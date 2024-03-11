package fr.cel.eldenrpg.capabilities.firecamp;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class PlayerCampfire {

    private List<BlockPos> campfires = new ArrayList<>();

    public List<BlockPos> getCampfires() {
        return campfires;
    }

    public void addCampfire(BlockPos pos) {
        if (campfires.contains(pos)) return;
        campfires.add(pos);
    }

    public void copyFrom(PlayerCampfire source) {
        this.campfires = source.campfires;
    }

    public void saveNBTData(CompoundTag nbt) {
        ListTag campfireList = new ListTag();
        for (BlockPos pos : campfires) {
            CompoundTag campfireTag = new CompoundTag();
            campfireTag.putLong("pos", pos.asLong());
            campfireList.add(campfireTag);
        }
        nbt.put("campfires", campfireList);
    }

    public void loadNBTData(CompoundTag nbt) {
        List<BlockPos> temp_campfire = new ArrayList<>();
        ListTag campfireList = nbt.getList("campfires", Tag.TAG_COMPOUND);

        for (int i = 0; i < campfireList.size(); i++) {
            CompoundTag campTags = campfireList.getCompound(i);
            BlockPos pos = BlockPos.of(campTags.getLong("pos"));
            temp_campfire.add(pos);
        }

        this.campfires = temp_campfire;
    }

}