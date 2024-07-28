package fr.cel.eldenrpg.mixin;

import com.mojang.authlib.GameProfile;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerDataSaver {

    @Unique private List<Quest> quests;
    @Unique private NbtCompound persistentData;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
        this.quests = new ArrayList<>();
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void injectWriteMethod(NbtCompound nbt, CallbackInfo ci) {
        if (this.persistentData != null) {
            nbt.put("eldenrpg.data", this.persistentData);
            persistentData.put("quests", Quests.writeNbt(quests));
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void injectReadMethod(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("eldenrpg.data", NbtElement.COMPOUND_TYPE)) {
            this.persistentData = nbt.getCompound("eldenrpg.data");
            this.quests = Quests.loadNbt(this.persistentData);
        }
    }

    @Override
    public NbtCompound eldenrpg$getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();

            // Flasks
            this.persistentData.putInt("flasks", 3);
            this.persistentData.putInt("maxFlasks", 3);

            this.persistentData.putInt("levelFlasks", 1);

            this.persistentData.putInt("goldenSeed", 0);
            this.persistentData.putInt("tearOfLife", 0);

            // First Time
            this.persistentData.putBoolean("firstTime", true);

            // Maps Id
            this.persistentData.putIntArray("mapsId", new int[]{});

            // Quests
            this.persistentData.put("quests", new NbtList());
            this.quests.add(Quests.BEGINNING);

            // Graces acquired
            this.persistentData.putLongArray("graces", new long[]{});
        }

        return persistentData;
    }

    @Override
    public List<Quest> eldenrpg$getQuests() {
        return quests;
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

}