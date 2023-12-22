package fr.cel.eldenrpg.capabilities.slots;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBackpackProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerBackpack> PLAYER_BACKPACK = CapabilityManager.get(new CapabilityToken<PlayerBackpack>() { });

    private PlayerBackpack slots = null;
    private final LazyOptional<PlayerBackpack> optional = LazyOptional.of(this::createPlayerBackpack);

    private PlayerBackpack createPlayerBackpack() {
        if (this.slots == null) {
            this.slots = new PlayerBackpack();
        }

        return this.slots;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_BACKPACK) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerBackpack().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerBackpack().loadNBTData(nbt);
    }

}
