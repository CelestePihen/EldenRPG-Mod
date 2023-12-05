package fr.cel.eldenrpg.client.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class ClientFirecampsData {

    private static final Map<BlockPos, Component> firecamps = new HashMap<>();

    public static void add(BlockPos blockPos, Component name) {
        if (firecamps.containsKey(blockPos)) return;
        ClientFirecampsData.firecamps.put(blockPos, name);
    }

    public static Map<BlockPos, Component> getFirecamps() {
        return firecamps;
    }

}
