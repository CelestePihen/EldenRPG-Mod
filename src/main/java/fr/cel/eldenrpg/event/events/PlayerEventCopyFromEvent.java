package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerEventCopyFromEvent implements ServerPlayerEvents.CopyFrom {

    public static void init() {
        ServerPlayerEvents.COPY_FROM.register(new PlayerEventCopyFromEvent());
    }

    // Quand un joueur respawn
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        IPlayerDataSaver original = (IPlayerDataSaver) oldPlayer;
        IPlayerDataSaver player = (IPlayerDataSaver) newPlayer;

        NbtCompound originalNbt = original.getPersistentData();
        NbtCompound newNbt = player.getPersistentData();

        newNbt.putInt("flasks", originalNbt.getInt("maxFlasks").orElse(0));
        newNbt.putInt("maxFlasks", originalNbt.getInt("maxFlasks").orElse(0));

        newNbt.putInt("levelFlasks", originalNbt.getInt("levelFlasks").orElse(0));

        newNbt.putInt("goldenSeed", originalNbt.getInt("goldenSeed").orElse(0));
        newNbt.putInt("sacredTear", originalNbt.getInt("sacredTear").orElse(0));

        newNbt.putBoolean("firstTime", originalNbt.getBoolean("firstTime").orElse(false));

        newNbt.putIntArray("mapsId", originalNbt.getIntArray("mapsId").orElse(new int[0]));

        newNbt.put("quests", originalNbt.get("quests"));

        newNbt.putLongArray("graces", originalNbt.getLongArray("graces").orElse(new long[0]));
    }

}