package fr.cel.eldenrpg.capabilities.firecamp;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class CampfireList {

    private static final Map<BlockPos, Component> CAMPFIRES = new HashMap<>();

    public static Map<BlockPos, Component> getCampfires() {
        return CAMPFIRES;
    }

    public static Component getCampfireName(BlockPos blockPos) {
        return CAMPFIRES.get(blockPos);
    }
    
    static {
        // TODO mettre TOUS les feux de camps quand map fini... trop hâte... + mettre traduction des lieux
        CAMPFIRES.put(new BlockPos(100, 94, 83), Component.translatable("Campfire 1"));
        CAMPFIRES.put(new BlockPos(105, 95, 89), Component.translatable("Campfire 2"));
        CAMPFIRES.put(new BlockPos(104, 95, 89), Component.translatable("Campfire 3"));
        CAMPFIRES.put(new BlockPos(103, 95, 89), Component.translatable("Campfire 4"));
        CAMPFIRES.put(new BlockPos(102, 95, 89), Component.translatable("Campfire 5"));
        CAMPFIRES.put(new BlockPos(101, 95, 89), Component.translatable("Campfire 6"));
        CAMPFIRES.put(new BlockPos(100, 95, 89), Component.translatable("Campfire 7"));
        CAMPFIRES.put(new BlockPos(99, 95, 89), Component.translatable("Campfire 8"));
        CAMPFIRES.put(new BlockPos(98, 95, 89), Component.translatable("Campfire 9"));
        CAMPFIRES.put(new BlockPos(97, 95, 89), Component.translatable("Campfire 10"));
    }

}
