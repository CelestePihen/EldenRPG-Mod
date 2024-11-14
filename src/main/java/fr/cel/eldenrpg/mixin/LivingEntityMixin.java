package fr.cel.eldenrpg.mixin;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IPlayerDataSaver {

    @Unique private NbtCompound persistentData;
    @Unique private List<Quest> quests;

    @Unique private long lastRollTime = 0;
    @Unique private long lastFlaskDrunk = 0;
    @Unique private int invulnerableTicks;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(EntityType<?> type, World world, CallbackInfo ci) {
        this.quests = new ArrayList<>();
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void injectWriteMethod(NbtCompound nbt, CallbackInfo ci) {
        if (this.persistentData != null) {
            nbt.put("eldenrpg.data", this.persistentData);
            persistentData.put("quests", Quests.writeNbt(quests));
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void injectReadMethod(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("eldenrpg.data", NbtElement.COMPOUND_TYPE)) {
            this.persistentData = nbt.getCompound("eldenrpg.data");
            this.quests = Quests.loadNbt(this.persistentData);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (invulnerableTicks > 0) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void sprint(boolean sprinting, CallbackInfo ci) {
        if (invulnerableTicks > 0) {
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        eldenRPG_Mod$decrementInvulnerableTicks();
    }

    @Override
    public NbtCompound eldenrpg$getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();

            // Fioles et nombre maximum de fiolesd
            this.persistentData.putInt("flasks", 4);
            this.persistentData.putInt("maxFlasks", 4);

            // Niveau des Fioles
            this.persistentData.putInt("levelFlasks", 1);

            // Nombre de graines dorées et de larmes de vie
            this.persistentData.putInt("goldenSeed", 0);
            this.persistentData.putInt("sacredTear", 0);

            // Identifiants des GD et LdV déjà prises
            this.persistentData.putIntArray("tearId", new int[]{});
            this.persistentData.putIntArray("gsId", new int[]{});

            // Première fois sur le serveur
            this.persistentData.putBoolean("firstTime", true);

            // Identifiants des maps déjà prises
            this.persistentData.putIntArray("mapsId", new int[]{});

            // Quêtes
            this.persistentData.put("quests", new NbtList());
            this.quests.add(Quests.BEGINNING);

            // Emplacements des Grâces déjà prises
            this.persistentData.putLongArray("graces", new long[]{});
        }

        return persistentData;
    }

    @Override
    public List<Quest> eldenrpg$getQuests() {
        return this.quests;
    }

    @Override
    public List<Quest> eldenrpg$getKillQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof KillTask) quests.add(quest);
        }
        return quests;
    }

    @Override
    public List<Quest> eldenrpg$getItemQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof ItemTask) quests.add(quest);
        }
        return quests;
    }

    @Override
    public List<Quest> eldenrpg$getZoneQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof ZoneTask) quests.add(quest);
        }
        return quests;
    }

    @Override
    public long eldenRPG_Mod$getLastRollTime() {
        return lastRollTime;
    }

    @Override
    public void eldenRPG_Mod$setLastRollTime(long time) {
        this.lastRollTime = time;
    }

    @Override
    public long eldenRPG_Mod$getLastFlaskDrunk() {
        return lastFlaskDrunk;
    }

    @Override
    public void eldenRPG_Mod$setLastFlaskDrunk(long time) {
        this.lastFlaskDrunk = time;
    }

    @Override
    public void eldenRPG_Mod$setInvulnerableTicks(int ticks) {
        invulnerableTicks = ticks;
    }

    @Override
    public void eldenRPG_Mod$decrementInvulnerableTicks() {
        if (invulnerableTicks > 0) {
            invulnerableTicks--;
        }
    }

}
