package fr.cel.eldenrpg.client.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class ClientBonfiresData {

    private static final Map<BlockPos, Component> bonfires = new HashMap<>();

    public static void add(BlockPos blockPos, Component name) {
        if (bonfires.containsKey(blockPos)) return;
        ClientBonfiresData.bonfires.put(blockPos, name);
    }

    public static Map<BlockPos, Component> getBonfires() {
        return bonfires;
    }

}