package fr.cel.eldenrpg.mixin;

import com.mojang.authlib.GameProfile;
import fr.cel.eldenrpg.networking.packets.flasks.EndDrinkFlaskS2CPacket;
import fr.cel.eldenrpg.networking.packets.roll.EndRollS2CPacket;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerDataSaver {

    @Shadow public abstract GameProfile getGameProfile();

    @Unique private NbtCompound persistentData;
    @Unique private List<Quest> quests = new ArrayList<>();

    @Unique private boolean rolling = false;
    @Unique private long lastRollTime = 0;
    @Unique private int invulnerableTicks = 0;

    @Unique private boolean takingFlask = false;
    @Unique private int flaskDrunkTicks = 0;
    @Unique private long lastFlaskDrunk = 0;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /* Méthodes du jeu */

    @Inject(method = "writeCustomData", at = @At("HEAD"))
    private void injectWriteMethod(WriteView view, CallbackInfo ci) {
        view.put("eldenrpg", NbtCompound.CODEC, this.persistentData);
        persistentData.put("quests", Quests.writeNbt(quests));
    }

    @Inject(method = "readCustomData", at = @At("HEAD"))
    private void injectReadMethod(ReadView view, CallbackInfo ci) {
        Optional<NbtCompound> eldenrpg = view.read("eldenrpg", NbtCompound.CODEC);
        this.persistentData = eldenrpg.orElseGet(NbtCompound::new);
        this.quests = Quests.loadNbt(this.persistentData);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (invulnerableTicks > 0 && isCombatDamage(source)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        decrementInvulnerableTicks();
        decrementTakenPotion();
    }

    /* Méthodes customs */

    @Unique
    private boolean isCombatDamage(DamageSource source) {
        return source.isOf(DamageTypes.GENERIC) ||
                source.isOf(DamageTypes.PLAYER_ATTACK) ||
                source.isOf(DamageTypes.MOB_ATTACK) ||
                source.isOf(DamageTypes.EXPLOSION) ||
                source.isOf(DamageTypes.THROWN);
    }

    @Unique
    public void decrementInvulnerableTicks() {
        if (this.rolling) {
            if (this.invulnerableTicks > 0) {
                this.invulnerableTicks--;
            } else {
                this.rolling = false;
                if (!this.getWorld().isClient())
                    ServerPlayNetworking.send((ServerPlayerEntity) this.getWorld().getPlayerByUuid(this.getGameProfile().getId()), new EndRollS2CPacket());
            }
        }
    }

    @Unique
    public void decrementTakenPotion() {
        if (this.takingFlask) {
            if (this.flaskDrunkTicks > 0) {
                this.flaskDrunkTicks--;
            } else {
                this.takingFlask = false;
                if (!this.getWorld().isClient())
                    ServerPlayNetworking.send((ServerPlayerEntity) this.getWorld().getPlayerByUuid(this.getGameProfile().getId()), new EndDrinkFlaskS2CPacket());
            }
        }
    }

    @Override
    public NbtCompound getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();

            /* Ce que le joueur peut voir */

            // Fioles et nombre maximum de fioles
            this.persistentData.putInt("flasks", 4);
            this.persistentData.putInt("maxFlasks", 4);

            // Niveau des Fioles
            this.persistentData.putInt("levelFlasks", 1);

            // Nombre de graines dorées et de larmes de vie
            this.persistentData.putInt("goldenSeed", 0);
            this.persistentData.putInt("sacredTear", 0);

            // Identifiants des maps déjà prises
            this.persistentData.putIntArray("mapsId", new int[]{});

            // Première fois sur le serveur
            this.persistentData.putBoolean("firstTime", true);

            // Emplacements des Grâces déjà prises
            this.persistentData.putLongArray("graces", new long[]{});

            /* Ce que le joueur ne peut pas voir */

            // Identifiants des Graines dorées et Larmes de Vie déjà prises
            this.persistentData.putIntArray("gsId", new int[]{});
            this.persistentData.putIntArray("tearId", new int[]{});

            // Quêtes
            this.persistentData.put("quests", new NbtList());
            this.quests.add(Quests.BEGINNING);
        }

        return persistentData;
    }

    @Override
    public List<Quest> getQuests() {
        return this.quests;
    }

    @Override
    public List<Quest> getKillQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof KillTask) quests.add(quest);
        }
        return quests;
    }

    @Override
    public List<Quest> getItemQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof ItemTask) quests.add(quest);
        }
        return quests;
    }

    @Override
    public List<Quest> getZoneQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof ZoneTask) quests.add(quest);
        }
        return quests;
    }

    @Override
    public long getLastRollTime() {
        return this.lastRollTime;
    }

    @Override
    public void setLastRollTime(long time) {
        this.lastRollTime = time;
    }

    @Override
    public void setInvulnerableTicks(int ticks) {
        this.invulnerableTicks = ticks;
    }
    
    @Override
    public void setTakingFlask(boolean takingFlask) {
        this.takingFlask = takingFlask;
    }

    @Override
    public void setRolling(boolean rolling) {
        this.rolling = rolling;
    }

    @Override
    public void setFlaskDrunkTicks(int time) {
        this.flaskDrunkTicks = time;
    }

    @Override
    public long getLastFlaskDrunk() {
        return lastFlaskDrunk;
    }

    @Override
    public void setLastFlaskDrunk(long time) {
        this.lastFlaskDrunk = time;
    }
}