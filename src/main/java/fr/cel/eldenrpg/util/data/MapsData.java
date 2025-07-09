package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.maps.MapsSyncS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public final class MapsData {

    public static boolean addMapId(IPlayerDataSaver player, int mapId) {
        int[] mapsId = player.eldenrpg$getPersistentData().getIntArray("mapsId").get();

        for (int id : mapsId) {
            if (id == mapId) return false;
        }

        int[] newMapsId = new int[mapsId.length + 1];
        System.arraycopy(mapsId, 0, newMapsId, 0, mapsId.length);
        newMapsId[mapsId.length] = mapId;

        player.eldenrpg$getPersistentData().putIntArray("mapsId", newMapsId);
        syncMap((PlayerEntity) player, mapId);
        return true;
    }

    public static int[] getMapsId(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getIntArray("mapsId").get();
    }

    public static void syncMap(PlayerEntity player, int mapId) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new MapsSyncS2CPacket(mapId));
        }
    }

}