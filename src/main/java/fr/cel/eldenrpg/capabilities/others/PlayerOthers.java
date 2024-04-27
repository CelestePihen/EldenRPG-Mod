package fr.cel.eldenrpg.capabilities.others;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class PlayerOthers {

    private boolean isFirstTime;

    private double maxHealth;

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean firstTime) {
        isFirstTime = firstTime;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void copyFrom(PlayerOthers source) {
        this.isFirstTime = source.isFirstTime;
        this.maxHealth = source.maxHealth;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("isFirstTime", isFirstTime);
        nbt.putDouble("maxHealth", maxHealth);
    }

    public void loadNBTData(CompoundTag nbt) {
        isFirstTime = nbt.getBoolean("isFirstTime");
        maxHealth = nbt.getDouble("maxHealth");
    }

}
