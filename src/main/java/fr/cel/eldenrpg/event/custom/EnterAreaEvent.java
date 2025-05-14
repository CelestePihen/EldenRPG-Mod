package fr.cel.eldenrpg.event.custom;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.quest.Quest;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface EnterAreaEvent {

    Event<EnterAreaEvent> EVENT = EventFactory.createArrayBacked(EnterAreaEvent.class,
            (listeners) -> (player, area, quest) -> {
                for (EnterAreaEvent listener : listeners) listener.onEnterArea(player, area, quest);
            });

    void onEnterArea(ServerPlayerEntity player, Area<?> area, Quest quest);

}