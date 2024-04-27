package fr.cel.eldenrpg.capabilities.others;

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

public class PlayerOthersProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerOthers> PLAYER_OTHERS = CapabilityManager.get(new CapabilityToken<PlayerOthers>() { });

    private PlayerOthers others = null;
    private final LazyOptional<PlayerOthers> optional = LazyOptional.of(this::createPlayerOthers);

    private PlayerOthers createPlayerOthers() {
        if (this.others == null) {
            this.others = new PlayerOthers();
            this.others.setFirstTime(true);
            this.others.setMaxHealth(10.0D);
        }

        return this.others;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_OTHERS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerOthers().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerOthers().loadNBTData(nbt);
    }

}