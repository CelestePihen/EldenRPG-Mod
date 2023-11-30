package fr.cel.eldenrpg.capabilities.slots;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.items.ItemStackHandler;

@AutoRegisterCapability
public class PlayerSlots {

    // https://github.com/Ketheroth/Slots/blob/master/src/main/java/com/ketheroth/common/capability/PlayerSlots.java

    private ItemStackHandler stacks = new ItemStackHandler(2);

    public ItemStackHandler getStacks() {
        return stacks;
    }

    public void copyFrom(PlayerSlots source) {
        this.stacks = source.stacks;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.put("slots", stacks.serializeNBT());
    }

    public void loadNBTData(CompoundTag nbt) {
        if (nbt.contains("slots")) {
            stacks.deserializeNBT(nbt.getCompound("slots"));
        }
    }

}