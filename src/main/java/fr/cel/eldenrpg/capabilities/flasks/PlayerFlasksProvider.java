package fr.cel.eldenrpg.capabilities.flasks;

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

public class PlayerFlasksProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerFlasks> PLAYER_FLASKS = CapabilityManager.get(new CapabilityToken<PlayerFlasks>() { });

    private PlayerFlasks flasks = null;
    private final LazyOptional<PlayerFlasks> optional = LazyOptional.of(this::createPlayerFlasks);

    private PlayerFlasks createPlayerFlasks() {
        if (this.flasks == null) {
            this.flasks = new PlayerFlasks();
            this.flasks.addMaxFlasksPlayer(4);
            this.flasks.addFlasks(4);

        }

        return this.flasks;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_FLASKS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerFlasks().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFlasks().loadNBTData(nbt);
    }

}