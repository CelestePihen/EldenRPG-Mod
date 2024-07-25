package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.flasks.FlasksSyncDataS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlasksData {

    public static void addFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int flasks = nbt.getInt("flasks");
        int maxFlasks = nbt.getInt("maxFlasks");

        flasks = Math.min(flasks + amount, maxFlasks);

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (PlayerEntity) player);
    }

    public static void removeFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int flasks = nbt.getInt("flasks");

        flasks = Math.max(flasks - amount, 0);

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
    }

    public static int getFlasks(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("flasks");
    }

    public static void addLevelFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int flasks = nbt.getInt("levelFlasks");
        int maxFlasks = nbt.getInt("maxLevelFlasks");

        flasks = Math.min(flasks + amount, maxFlasks);

        nbt.putInt("levelFlasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
    }

    public static int getLevelFlasks(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("levelFlasks");
    }


    public static void addMaxFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int maxFlasks = nbt.getInt("maxFlasks");

        maxFlasks = Math.min(maxFlasks + amount, 14);

        nbt.putInt("maxFlasks", maxFlasks);
    }

    public static void addGoldenSeed(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int goldenSeed = nbt.getInt("goldenSeed");
        int maxGoldenSeed = nbt.getInt("maxGoldenSeed");

        goldenSeed = Math.min(goldenSeed + amount, maxGoldenSeed);

        nbt.putInt("goldenSeed", goldenSeed);
    }

    public static void removeGoldenSeed(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int goldenSeed = nbt.getInt("goldenSeed");

        goldenSeed = Math.max(goldenSeed - amount, 0);

        nbt.putInt("goldenSeed", goldenSeed);
    }

    public static void addTearOfLife(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int goldenSeed = nbt.getInt("tearOfLife");
        int maxGoldenSeed = nbt.getInt("maxTearOfLife");

        goldenSeed = Math.min(goldenSeed + amount, maxGoldenSeed);

        nbt.putInt("goldenSeed", goldenSeed);
    }

    public static void removeTearOfLife(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int goldenSeed = nbt.getInt("tearOfLife");

        goldenSeed = Math.max(goldenSeed - amount, 0);

        nbt.putInt("goldenSeed", goldenSeed);
    }

    public static void syncFlasks(int flasks, PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new FlasksSyncDataS2CPacket(flasks));
        }
    }

}