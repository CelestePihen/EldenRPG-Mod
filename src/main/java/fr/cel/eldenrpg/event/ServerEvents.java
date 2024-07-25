package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.areas.type.POIArea;
import fr.cel.eldenrpg.command.GraceCommand;
import fr.cel.eldenrpg.command.NPCCommand;
import fr.cel.eldenrpg.command.QuestCommand;
import fr.cel.eldenrpg.event.custom.EnterAreaEvent;
import fr.cel.eldenrpg.event.events.ModAfterDeath;
import fr.cel.eldenrpg.event.events.ModEndServerTick;
import fr.cel.eldenrpg.event.events.ModEntityLoad;
import fr.cel.eldenrpg.event.events.ModPlayerEventCopyFrom;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ServerEvents {

    public static void registerEvents() {
        ModPlayerEventCopyFrom.init();
        ModEntityLoad.init();
        ModAfterDeath.init();
        ModEndServerTick.init();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            NPCCommand.register(dispatcher);
            QuestCommand.register(dispatcher);
            GraceCommand.register(dispatcher);
        });

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