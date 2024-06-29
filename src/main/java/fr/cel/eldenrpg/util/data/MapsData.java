package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.maps.MapsSyncDataS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class MapsData {

    public static void addMapId(IPlayerDataSaver player, int mapId) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        List<Integer> mapsId = getMapsId(player);

        if (mapsId.contains(mapId)) return;

        nbt.putIntArray("mapsId", new ArrayList<>(mapId));
        syncMap((ServerPlayerEntity) player, mapId);
    }

    public static List<Integer> getMapsId(IPlayerDataSaver player) {
        List<Integer> temp = new ArrayList<>();
        for (int i : player.eldenrpg$getPersistentData().getIntArray("mapsId")) {
            temp.add(i);
        }
        return temp;
    }

    public static void syncMap(ServerPlayerEntity player, int mapId) {
        ServerPlayNetworking.send(player, new MapsSyncDataS2CPacket(mapId));
    }

}