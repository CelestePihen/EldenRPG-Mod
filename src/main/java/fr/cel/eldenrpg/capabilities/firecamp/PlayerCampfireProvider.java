package fr.cel.eldenrpg.capabilities.firecamp;

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

public class PlayerCampfireProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerCampfire> PLAYER_CAMPFIRE = CapabilityManager.get(new CapabilityToken<PlayerCampfire>() { });

    private PlayerCampfire campfire = null;
    private final LazyOptional<PlayerCampfire> optional = LazyOptional.of(this::createPlayerCampfire);

    private PlayerCampfire createPlayerCampfire() {
        if (this.campfire == null) {
            this.campfire = new PlayerCampfire();
        }

        return this.campfire;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_CAMPFIRE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCampfire().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCampfire().loadNBTData(nbt);
    }
}
