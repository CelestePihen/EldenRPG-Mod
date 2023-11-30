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

public class PlayerSlotsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerSlots> PLAYER_SLOTS = CapabilityManager.get(new CapabilityToken<PlayerSlots>() { });

    private PlayerSlots slots = null;
    private final LazyOptional<PlayerSlots> optional = LazyOptional.of(this::createPlayerSlots);

    private PlayerSlots createPlayerSlots() {
        if (this.slots == null) {
            this.slots = new PlayerSlots();
        }

        return this.slots;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SLOTS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSlots().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSlots().loadNBTData(nbt);
    }

}
