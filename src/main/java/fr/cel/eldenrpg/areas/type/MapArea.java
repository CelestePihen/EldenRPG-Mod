package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.networking.packets.maps.PickMapC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

public class MapArea extends Area {

    public MapArea(String mapId, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(mapId, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayerEntity player, String mapId) {
        ClientPlayNetworking.send(new PickMapC2SPacket(Integer.parseInt(mapId)));
    }

}
