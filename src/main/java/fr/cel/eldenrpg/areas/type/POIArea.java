package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class POIArea extends Area<String> {

    public POIArea(String areaName, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        super(areaName, x1, y1, z1, x2, y2, z2, quest);
    }

    @Override
    protected void interact(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new TitleS2CPacket(Text.translatable("eldenrpg.area." + getObject())));
    }

}