package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.flasks.sync.FlasksSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.GoldenSeedSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.LevelFlasksSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.SacredTearSyncS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public final class FlasksData {

    /* ----------------------------- Flasks ----------------------------- */

    public static void addFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int flasks = nbt.getInt("flasks");
        int maxFlasks = nbt.getInt("maxFlasks");

        flasks = Math.min(flasks + amount, maxFlasks);

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
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

    /* ------------------------- MAX FLASKS ------------------------- */

    public static void addMaxFlasks(IPlayerDataSaver player) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int maxFlasks = nbt.getInt("maxFlasks");

        maxFlasks = Math.min(maxFlasks + 1, 14);

        nbt.putInt("maxFlasks", maxFlasks);
    }

    public static int getMaxFlasks(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("maxFlasks");
    }

    /* ----------------------- LEVEL OF FLASKS ----------------------- */

    public static void addLevelFlasks(IPlayerDataSaver player) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int levelFlasks = nbt.getInt("levelFlasks");

        levelFlasks = Math.min(levelFlasks + 1, 12);

        nbt.putInt("levelFlasks", levelFlasks);
        syncLevelFlask(levelFlasks, (ServerPlayerEntity) player);
    }

    public static int getLevelFlasks(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("levelFlasks");
    }

    /* ------------------------ GOLDEN SEED ------------------------ */

    public static void addGoldenSeed(IPlayerDataSaver player) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int goldenSeed = nbt.getInt("goldenSeed");

        // TODO voir le max
        goldenSeed = Math.min(goldenSeed + 1, 30);

        nbt.putInt("goldenSeed", goldenSeed);
        syncGoldenSeed(goldenSeed, (ServerPlayerEntity) player);
    }

    public static void removeGoldenSeed(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int goldenSeed = nbt.getInt("goldenSeed");

        goldenSeed = Math.max(goldenSeed - amount, 0);

        nbt.putInt("goldenSeed", goldenSeed);
        syncGoldenSeed(goldenSeed, (ServerPlayerEntity) player);
    }

    public static int getGoldenSeeds(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("goldenSeed");
    }

    /* ------------------------ TEAR OF LIFE ------------------------ */

    public static void addSacredTear(IPlayerDataSaver player) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int sacredTear = nbt.getInt("sacredTear");

        // TODO voir le max
        sacredTear = Math.min(sacredTear + 1, 12);

        nbt.putInt("sacredTear", sacredTear);
        syncSacredTear(sacredTear, (ServerPlayerEntity) player);
    }

    public static void removeSacredTear(IPlayerDataSaver player) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int sacredTear = nbt.getInt("sacredTear");

        sacredTear = Math.max(sacredTear - 1, 0);

        nbt.putInt("sacredTear", sacredTear);
        syncSacredTear(sacredTear, (ServerPlayerEntity) player);
    }

    public static int getSacredTears(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("sacredTear");
    }

    /* --------------------------- AREAS --------------------------- */

    public static boolean addTearId(IPlayerDataSaver player, int mapId) {
        List<Integer> tearId = getTearId(player);

        if (tearId.contains(mapId)) return false;
        tearId.add(mapId);

        player.eldenrpg$getPersistentData().putIntArray("tearId", tearId);
        return true;
    }

    public static void removeAllAreasST(IPlayerDataSaver player) {
        player.eldenrpg$getPersistentData().putIntArray("tearId", new ArrayList<>());
    }

    public static List<Integer> getTearId(IPlayerDataSaver player) {
        List<Integer> tearId = new ArrayList<>();
        for (int i : player.eldenrpg$getPersistentData().getIntArray("tearId")) {
            tearId.add(i);
        }
        return tearId;
    }

    public static boolean addGSId(IPlayerDataSaver player, int mapId) {
        List<Integer> gsId = getGSId(player);

        if (gsId.contains(mapId)) return false;
        gsId.add(mapId);

        player.eldenrpg$getPersistentData().putIntArray("gsId", gsId);
        return true;
    }

    public static void removeAllAreasGS(IPlayerDataSaver player) {
        player.eldenrpg$getPersistentData().putIntArray("gsId", new ArrayList<>());
    }

    public static List<Integer> getGSId(IPlayerDataSaver player) {
        List<Integer> gsId = new ArrayList<>();
        for (int i : player.eldenrpg$getPersistentData().getIntArray("gsId")) {
            gsId.add(i);
        }
        return gsId;
    }

    /* ---------------------------- CONVERT ---------------------------- */

    public static boolean convertSeedToFlask(IPlayerDataSaver player) {
        int goldenSeeds = getGoldenSeeds(player);
        if (goldenSeeds == 0) return false;

        int maxFlasks = getMaxFlasks(player);
        int seedsRequired;
        if (maxFlasks < 6) {
            seedsRequired = 1;
        } else if (maxFlasks < 8) {
            seedsRequired = 2;
        } else if (maxFlasks < 10) {
            seedsRequired = 3;
        } else if (maxFlasks < 12) {
            seedsRequired = 4;
        } else {
            seedsRequired = 5;
        }

        if (goldenSeeds >= seedsRequired) {
            removeGoldenSeed(player, seedsRequired);
            addMaxFlasks(player);
            addFlasks(player, 1);

            syncFlasks(getFlasks(player), (ServerPlayerEntity) player);
            syncGoldenSeed(getGoldenSeeds(player), (ServerPlayerEntity) player);
            return true;
        }

        return false;
    }

    public static boolean convertTearToLevel(IPlayerDataSaver player) {
        if (getSacredTears(player) == 0) return false;

        removeSacredTear(player);
        addLevelFlasks(player);

        syncSacredTear(getSacredTears(player), (ServerPlayerEntity) player);
        syncLevelFlask(getLevelFlasks(player), (ServerPlayerEntity) player);
        return true;
    }

    /* ----------------------------- SYNC ----------------------------- */

    public static void syncFlasks(int flasks, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new FlasksSyncS2CPacket(flasks));
    }

    public static void syncGoldenSeed(int goldenSeed, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new GoldenSeedSyncS2CPacket(goldenSeed));
    }

    public static void syncSacredTear(int sacredTear, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new SacredTearSyncS2CPacket(sacredTear));
    }

    public static void syncLevelFlask(int levelFlasks, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new LevelFlasksSyncS2CPacket(levelFlasks));
    }

}