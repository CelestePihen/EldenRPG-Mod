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

    long eldenrpg$getLastRollTime();
    void eldenrpg$setLastRollTime(long time);
    boolean eldenrpg$isRolling();
    void eldenrpg$setRolling(boolean rolling);

    long eldenrpg$getLastFlaskDrunk();
    void eldenrpg$setLastFlaskDrunk(long time);

    void eldenrpg$setInvulnerableTicks(int ticks);
    void eldenrpg$decrementInvulnerableTicks();

}