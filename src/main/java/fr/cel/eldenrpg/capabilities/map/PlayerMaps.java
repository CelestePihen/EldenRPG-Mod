package fr.cel.eldenrpg.capabilities.map;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class PlayerMaps {

    private List<Integer> mapsId = new ArrayList<>();

    public void addMap(int mapId) {
        if (mapsId.contains(mapId)) return;
        mapsId.add(mapId);
    }

    public List<Integer> getMapsId() {
        return mapsId;
    }

    public void copyFrom(PlayerMaps source) {
        this.mapsId = source.mapsId;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putIntArray("mapsId", mapsId);
    }

    public void loadNBTData(CompoundTag nbt) {
        List<Integer> temp = new ArrayList<>();

        for (int mapId : nbt.getIntArray("mapsId")) {
            temp.add(mapId);
        }

        this.mapsId = temp;
    }

}