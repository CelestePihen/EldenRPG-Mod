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
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ServerEvents {

    public static void registerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());

        ServerEntityEvents.ENTITY_LOAD.register(new ModEntityLoad());

        ServerLivingEntityEvents.AFTER_DEATH.register(new ModAfterDeath());

        ServerTickEvents.END_SERVER_TICK.register(new ModEndServerTick());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            NPCCommand.register(dispatcher);
            QuestCommand.register(dispatcher);
            GraceCommand.register(dispatcher);
        });

        EnterAreaEvent.EVENT.register((player, area, areaQuest) -> {
            if (!(area instanceof POIArea)) return;
            if (areaQuest == null) return;

            for (Quest quest : ((IPlayerDataSaver) player).eldenrpg$getZoneQuests()) {
                if (quest.getId().equalsIgnoreCase(areaQuest.getId())) {
                    ((ZoneTask)quest.getTask()).interact(player, areaQuest);
                }
            }
        });

    }

}