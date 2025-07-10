package fr.cel.eldenrpg.util;

import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public interface IPlayerDataSaver {

    NbtCompound getPersistentData();

    List<Quest> getQuests();
    List<Quest> getKillQuests();
    List<Quest> getItemQuests();
    List<Quest> getZoneQuests();

    long getLastRollTime();
    void setLastRollTime(long time);
    void setRolling(boolean rolling);
    void setInvulnerableTicks(int ticks);

    void setFlaskDrunkTicks(int ticks);
    void setTakingFlask(boolean takingFlask);
    long getLastFlaskDrunk();
    void setLastFlaskDrunk(long time);
}