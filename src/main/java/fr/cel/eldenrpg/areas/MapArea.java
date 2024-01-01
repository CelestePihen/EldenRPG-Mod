package fr.cel.eldenrpg.areas;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.maps.PickMapC2SPacket;
import net.minecraft.server.level.ServerPlayer;

public class MapArea extends Area {

    public MapArea(String mapId, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(mapId, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayer player, String mapId) {
        ModMessages.sendToServer(new PickMapC2SPacket(Integer.parseInt(mapId)));
    }

}
