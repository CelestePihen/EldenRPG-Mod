package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.maps.MapsSyncDataS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public final class MapsData {

    public static boolean addMapId(IPlayerDataSaver player, int mapId) {
        List<Integer> mapsId = getMapsId(player);
        if (mapsId.contains(mapId)) return false;
        mapsId.add(mapId);

        player.eldenrpg$getPersistentData().putIntArray("mapsId", mapsId);
        syncMap((PlayerEntity)  player, mapId);
        return true;
    }

    public static List<Integer> getMapsId(IPlayerDataSaver player) {
        List<Integer> temp = new ArrayList<>();
        for (int i : player.eldenrpg$getPersistentData().getIntArray("mapsId")) {
            temp.add(i);
        }
        return temp;
    }

    public static void syncMap(PlayerEntity player, int mapId) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new MapsSyncDataS2CPacket(mapId));
        }
    }

}