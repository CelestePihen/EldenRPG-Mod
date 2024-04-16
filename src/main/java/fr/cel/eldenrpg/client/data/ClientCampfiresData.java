package fr.cel.eldenrpg.client.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class ClientCampfiresData {

    private static final Map<BlockPos, Component> campfires = new HashMap<>();

    public static void add(BlockPos blockPos, Component name) {
        if (campfires.containsKey(blockPos)) return;
        ClientCampfiresData.campfires.put(blockPos, name);
    }

    public static Map<BlockPos, Component> getCampfires() {
        return campfires;
    }

}