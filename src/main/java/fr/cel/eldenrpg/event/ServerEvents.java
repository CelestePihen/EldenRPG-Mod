package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.areas.type.POIArea;
import fr.cel.eldenrpg.event.custom.EnterAreaEvent;
import fr.cel.eldenrpg.event.events.*;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;

public class ServerEvents {

    public static void registerEvents() {
        PlayerEventCopyFromEvent.init();
        EntityLoadEvent.init();
        DeathEvent.init();
        EndServerTickEvent.init();
        UseBlockEvent.init();
        CommandsRegisterEvent.init();

        EnterAreaEvent.EVENT.register((player, area, quest) -> {
            if (!(area instanceof POIArea) || quest == null) return;

            for (Quest zoneQuest : QuestsData.getZoneQuests((IPlayerDataSaver) player)) {
                if (zoneQuest.getId().equalsIgnoreCase(quest.getId())) {
                    ((ZoneTask) zoneQuest.getTask()).interact(quest);
                }
            }
        });

    }

}