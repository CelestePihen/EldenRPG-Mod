package fr.cel.eldenrpg.capabilities.map;

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

public class PlayerMapsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerMaps> PLAYER_MAPS = CapabilityManager.get(new CapabilityToken<PlayerMaps>() { });

    private PlayerMaps maps = null;
    private final LazyOptional<PlayerMaps> optional = LazyOptional.of(this::createPlayerMaps);

    private PlayerMaps createPlayerMaps() {
        if (this.maps == null) {
            this.maps = new PlayerMaps();
        }

        return this.maps;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_MAPS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerMaps().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerMaps().loadNBTData(nbt);
    }

}