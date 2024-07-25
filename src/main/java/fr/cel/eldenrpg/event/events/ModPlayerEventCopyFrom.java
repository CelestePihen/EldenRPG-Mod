package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModPlayerEventCopyFrom implements ServerPlayerEvents.CopyFrom {

    public static void init() {
        ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());
    }

    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IPlayerDataSaver original = (IPlayerDataSaver) oldPlayer;
        IPlayerDataSaver player = (IPlayerDataSaver) newPlayer;

        NbtCompound originalNbt = original.eldenrpg$getPersistentData();
        NbtCompound newNbt = player.eldenrpg$getPersistentData();

        newNbt.putInt("flasks", originalNbt.getInt("maxFlasks"));
        newNbt.putInt("maxFlasks", originalNbt.getInt("maxFlasks"));

        newNbt.putBoolean("firstTime", originalNbt.getBoolean("firstTime"));

        newNbt.putIntArray("mapsId", originalNbt.getIntArray("mapsId"));

        newNbt.put("quests", originalNbt.get("quests"));

        newNbt.putLongArray("graces", originalNbt.getLongArray("graces"));
    }

}