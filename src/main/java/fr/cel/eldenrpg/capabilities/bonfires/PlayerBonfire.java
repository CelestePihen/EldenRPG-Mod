package fr.cel.eldenrpg.capabilities.bonfires;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class PlayerBonfire {

    private List<BlockPos> bonfires = new ArrayList<>();

    public List<BlockPos> getBonfires() {
        return bonfires;
    }

    public void addBonfire(BlockPos pos) {
        bonfires.add(pos);
    }

    public void copyFrom(PlayerBonfire source) {
        this.bonfires = source.bonfires;
    }

    public void saveNBTData(CompoundTag nbt) {
        ListTag bonfireList = new ListTag();
        for (BlockPos pos : bonfires) {
            CompoundTag campfireTag = new CompoundTag();
            campfireTag.putLong("pos", pos.asLong());
            bonfireList.add(campfireTag);
        }
        nbt.put("bonfires", bonfireList);
    }

    public void loadNBTData(CompoundTag nbt) {
        List<BlockPos> temp_bonfire = new ArrayList<>();
        ListTag bonfireList = nbt.getList("bonfires", Tag.TAG_COMPOUND);

        for (int i = 0; i < bonfireList.size(); i++) {
            CompoundTag campTags = bonfireList.getCompound(i);
            BlockPos pos = BlockPos.of(campTags.getLong("pos"));
            temp_bonfire.add(pos);
        }

        this.bonfires = temp_bonfire;
    }

}