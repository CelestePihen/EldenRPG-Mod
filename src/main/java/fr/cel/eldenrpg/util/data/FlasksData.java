package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.flasks.FlasksSyncDataS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlasksData {

    public static void addFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int flasks = nbt.getInt("flasks");
        int maxFlasks = nbt.getInt("maxFlasks");

        if (flasks + amount >= maxFlasks) {
            flasks = maxFlasks;
        } else {
            flasks += amount;
        }

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
    }

    public static void removeFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int flasks = nbt.getInt("flasks");

        if (flasks - amount < 0) {
            flasks = 0;
        } else {
            flasks -= amount;
        }

        nbt.putInt("flasks", flasks);
        syncFlasks(flasks, (ServerPlayerEntity) player);
    }

    public static int getFlasks(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("flasks");
    }

    public static void addMaxFlasks(IPlayerDataSaver player, int amount) {
        NbtCompound nbt = player.eldenrpg$getPersistentData();
        int maxFlasks = nbt.getInt("maxFlasks");

        if (maxFlasks + amount >= 15) {
            maxFlasks = 15;
        } else {
            maxFlasks += amount;
        }

        nbt.putInt("maxFlasks", maxFlasks);;
    }

    public static int getMaxFlasks(IPlayerDataSaver player) {
        return player.eldenrpg$getPersistentData().getInt("maxFlasks");
    }

    public static void syncFlasks(int flasks, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new FlasksSyncDataS2CPacket(flasks));
    }

}