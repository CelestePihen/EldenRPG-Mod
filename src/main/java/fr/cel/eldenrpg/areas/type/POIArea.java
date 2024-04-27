package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.capabilities.quests.PlayerQuestsProvider;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;

public class POIArea extends Area {

    public POIArea(String areaName, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        super(areaName, x1, y1, z1, x2, y2, z2, quest);
    }

    public POIArea(String areaName, double x1, double y1, double z1, double x2, double y2, double z2) {
        this(areaName, x1, y1, z1, x2, y2, z2, null);
    }

    @Override
    protected void interact(ServerPlayer player, String areaName) {
        player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("eldenrpg.area." + areaName)));
    }

}