package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEventCopyFromEvent implements ServerPlayerEvents.CopyFrom {

    public static void init() {
        ServerPlayerEvents.COPY_FROM.register(new PlayerEventCopyFromEvent());
    }

    // FIXME ça sert à quelque chose ?
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IPlayerDataSaver original = (IPlayerDataSaver) oldPlayer;
        IPlayerDataSaver player = (IPlayerDataSaver) newPlayer;

        NbtCompound originalNbt = original.eldenrpg$getPersistentData();
        NbtCompound newNbt = player.eldenrpg$getPersistentData();

        newNbt.putInt("flasks", originalNbt.getInt("maxFlasks"));
        newNbt.putInt("maxFlasks", originalNbt.getInt("maxFlasks"));

        newNbt.putInt("levelFlasks", originalNbt.getInt("levelFlasks"));

        newNbt.putInt("goldenSeed", originalNbt.getInt("goldenSeed"));
        newNbt.putInt("sacredTear", originalNbt.getInt("sacredTear"));

        newNbt.putBoolean("firstTime", originalNbt.getBoolean("firstTime"));

        newNbt.putIntArray("mapsId", originalNbt.getIntArray("mapsId"));

        newNbt.put("quests", originalNbt.get("quests"));

        newNbt.putLongArray("graces", originalNbt.getLongArray("graces"));
    }

}