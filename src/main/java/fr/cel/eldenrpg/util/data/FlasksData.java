package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.flasks.sync.FlasksSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.GoldenSeedSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.LevelFlasksSyncS2CPacket;
import fr.cel.eldenrpg.networking.packets.flasks.sync.SacredTearSyncS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public final class FlasksData {

    /* ----------------------------- Flasks ----------------------------- */

    public static void addFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int flasks = nbt.getInt("flasks").get();
        int maxFlasks = nbt.getInt("maxFlasks").get();

        flasks = Math.min(flasks + amount, maxFlasks);

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
    }

    public static void removeFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int flasks = nbt.getInt("flasks").get();

        flasks = Math.max(flasks - amount, 0);

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
    }

    public static int getFlasks(IPlayerDataSaver player) {
        return player.getPersistentData().getInt("flasks").get();
    }

    /* ------------------------- MAX FLASKS ------------------------- */

    public static void addMaxFlasks(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int maxFlasks = nbt.getInt("maxFlasks").get();

        maxFlasks = Math.min(maxFlasks + 1, 14);

        nbt.putInt("maxFlasks", maxFlasks);
    }

    public static int getMaxFlasks(IPlayerDataSaver player) {
        return player.getPersistentData().getInt("maxFlasks").get();
    }

    /* ----------------------- LEVEL OF FLASKS ----------------------- */

    public static void addLevelFlasks(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int levelFlasks = nbt.getInt("levelFlasks").get();

        levelFlasks = Math.min(levelFlasks + 1, 12);

        nbt.putInt("levelFlasks", levelFlasks);
        syncLevelFlask(levelFlasks, (ServerPlayerEntity) player);
    }

    public static int getLevelFlasks(IPlayerDataSaver player) {
        return player.getPersistentData().getInt("levelFlasks").get();
    }

    /* ------------------------ GOLDEN SEED ------------------------ */

    public static void addGoldenSeed(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int goldenSeed = nbt.getInt("goldenSeed").get();

        // TODO voir le max
        goldenSeed = Math.min(goldenSeed + 1, 30);

        nbt.putInt("goldenSeed", goldenSeed);
        syncGoldenSeed(goldenSeed, (ServerPlayerEntity) player);
    }

    public static void removeGoldenSeed(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int goldenSeed = nbt.getInt("goldenSeed").get();

        goldenSeed = Math.max(goldenSeed - amount, 0);

        nbt.putInt("goldenSeed", goldenSeed);
        syncGoldenSeed(goldenSeed, (ServerPlayerEntity) player);
    }

    public static int getGoldenSeeds(IPlayerDataSaver player) {
        return player.getPersistentData().getInt("goldenSeed").get();
    }

    /* ------------------------ TEAR OF LIFE ------------------------ */

    public static void addSacredTear(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int sacredTear = nbt.getInt("sacredTear").get();

        // TODO voir le max
        sacredTear = Math.min(sacredTear + 1, 12);

        nbt.putInt("sacredTear", sacredTear);
        syncSacredTear(sacredTear, (ServerPlayerEntity) player);
    }

    public static void removeSacredTear(IPlayerDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        int sacredTear = nbt.getInt("sacredTear").get();

        sacredTear = Math.max(sacredTear - 1, 0);

        nbt.putInt("sacredTear", sacredTear);
        syncSacredTear(sacredTear, (ServerPlayerEntity) player);
    }

    public static int getSacredTears(IPlayerDataSaver player) {
        return player.getPersistentData().getInt("sacredTear").get();
    }

    /* --------------------------- AREAS --------------------------- */

    public static boolean addTearId(IPlayerDataSaver player, int mapId) {
        int[] tearIdArray = player.getPersistentData().getIntArray("tearId").get();

        for (int id : tearIdArray) {
            if (id == mapId) return false;
        }

        int[] newTearIdArray = new int[tearIdArray.length + 1];
        System.arraycopy(tearIdArray, 0, newTearIdArray, 0, tearIdArray.length);
        newTearIdArray[tearIdArray.length] = mapId;

        player.getPersistentData().putIntArray("tearId", newTearIdArray);
        return true;
    }

    public static void removeAllAreasST(IPlayerDataSaver player) {
        player.getPersistentData().putIntArray("tearId", new int[]{});
    }

    public static int[] getTearId(IPlayerDataSaver player) {
        return player.getPersistentData().getIntArray("tearId").get();
    }

    public static boolean addGSId(IPlayerDataSaver player, int mapId) {
        int[] gsIdArray = player.getPersistentData().getIntArray("gsId").get();

        for (int id : gsIdArray) {
            if (id == mapId) return false;
        }

        int[] newGsIdArray = new int[gsIdArray.length + 1];
        System.arraycopy(gsIdArray, 0, newGsIdArray, 0, gsIdArray.length);
        newGsIdArray[gsIdArray.length] = mapId;

        player.getPersistentData().putIntArray("gsId", newGsIdArray);
        return true;
    }

    public static void removeAllAreasGS(IPlayerDataSaver player) {
        player.getPersistentData().putIntArray("gsId", new int[]{});
    }

    public static int[] getGSId(IPlayerDataSaver player) {
        return player.getPersistentData().getIntArray("gsId").get();
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