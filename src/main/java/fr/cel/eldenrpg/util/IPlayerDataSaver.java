package fr.cel.eldenrpg.util;

import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public interface IPlayerDataSaver {
    NbtCompound eldenrpg$getPersistentData();

    List<Quest> eldenrpg$getQuests();
    List<Quest> eldenrpg$getKillQuests();
    List<Quest> eldenrpg$getItemQuests();
    List<Quest> eldenrpg$getZoneQuests();

    long eldenRPG_Mod$getLastRollTime();
    void eldenRPG_Mod$setLastRollTime(long time);

    long eldenRPG_Mod$getLastFlaskDrunk();
    void eldenRPG_Mod$setLastFlaskDrunk(long time);

    void eldenRPG_Mod$setInvulnerableTicks(int ticks);
    void eldenRPG_Mod$decrementInvulnerableTicks();
}