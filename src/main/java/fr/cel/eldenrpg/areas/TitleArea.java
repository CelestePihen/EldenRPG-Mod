package fr.cel.eldenrpg.areas;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;

public class TitleArea extends Area {

    public TitleArea(String areaName, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(areaName, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayer player, String areaName) {
        player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("eldenrpg.area." + areaName)));
    }

}