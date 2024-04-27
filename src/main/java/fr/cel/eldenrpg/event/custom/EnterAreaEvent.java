package fr.cel.eldenrpg.event.custom;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class EnterAreaEvent extends Event {

    private final ServerPlayer player;
    private final Area area;

    private final Quest quest;

    public EnterAreaEvent(ServerPlayer player, Area area, Quest quest) {
        this.player = player;
        this.area = area;
        this.quest = quest;
    }

    public Area getArea() {
        return area;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public Quest getQuest() {
        return quest;
    }

}