package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPlayerEventCopyFrom implements ServerPlayerEvents.CopyFrom {

    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IPlayerDataSaver original = ((IPlayerDataSaver) oldPlayer);
        IPlayerDataSaver player = ((IPlayerDataSaver) newPlayer);

        player.eldenrpg$getPersistentData().putInt("flasks", original.eldenrpg$getPersistentData().getInt("maxFlasks"));
        player.eldenrpg$getPersistentData().putInt("maxFlasks", original.eldenrpg$getPersistentData().getInt("maxFlasks"));

        player.eldenrpg$getPersistentData().putBoolean("firstTime", original.eldenrpg$getPersistentData().getBoolean("firstTime"));

        player.eldenrpg$getPersistentData().putIntArray("mapsId", original.eldenrpg$getPersistentData().getIntArray("mapsId"));

        player.eldenrpg$getPersistentData().put("quests", original.eldenrpg$getPersistentData().get("quests"));

        player.eldenrpg$getPersistentData().putLongArray("graces", original.eldenrpg$getPersistentData().getLongArray("graces"));
    }

}