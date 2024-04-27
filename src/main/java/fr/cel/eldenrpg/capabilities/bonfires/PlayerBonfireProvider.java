package fr.cel.eldenrpg.capabilities.bonfires;

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

public class PlayerBonfireProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerBonfire> PLAYER_BONFIRE = CapabilityManager.get(new CapabilityToken<PlayerBonfire>() { });

    private PlayerBonfire bonfire = null;
    private final LazyOptional<PlayerBonfire> optional = LazyOptional.of(this::createPlayerBonfire);

    private PlayerBonfire createPlayerBonfire() {
        if (this.bonfire == null) {
            this.bonfire = new PlayerBonfire();
        }

        return this.bonfire;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_BONFIRE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerBonfire().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerBonfire().loadNBTData(nbt);
    }
}
