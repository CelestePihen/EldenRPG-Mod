package fr.cel.eldenrpg.capabilities.flasks;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerFlasks {

    private int flasks;
    private int maxFlasksPlayer;

    public int getFlasks() {
        return flasks;
    }

    public void addMaxFlasksPlayer(int add) {
        this.maxFlasksPlayer = Math.min(maxFlasksPlayer + add, 14);
    }

    public void addFlasks(int add) {
        this.flasks = Math.min(flasks + add, maxFlasksPlayer);
    }

    public void subFlasks(int sub) {
        this.flasks = Math.max(flasks - sub, 0);
    }

    public void copyFrom(PlayerFlasks source) {
        this.flasks = source.flasks;
        this.maxFlasksPlayer = source.maxFlasksPlayer;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("flasks", flasks);
        nbt.putInt("maxFlasksPlayer", maxFlasksPlayer);
    }

    public void loadNBTData(CompoundTag nbt) {
        flasks = nbt.getInt("flasks");
        maxFlasksPlayer = nbt.getInt("maxFlasksPlayer");
    }

}
