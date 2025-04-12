package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.graces.AddGraceS2CPacket;
import fr.cel.eldenrpg.networking.packets.graces.RemoveGraceS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GracesData {

    public static void addGrace(IPlayerDataSaver player, BlockPos pos) {
        List<Long> blockPosLong = getPlayerGraces(player);
        if (blockPosLong.contains(pos.asLong())) return;
        blockPosLong.add(pos.asLong());

        player.eldenrpg$getPersistentData().putLongArray("graces", blockPosLong);
        syncAddGrace((PlayerEntity) player, pos);
    }

    public static void addGraces(IPlayerDataSaver player) {
        for (BlockPos gracePos : GracesData.getGraces().keySet()) {
            GracesData.addGrace(player, gracePos);
        }
    }

    public static void removeGrace(IPlayerDataSaver player, BlockPos pos) {
        List<Long> blockPosLong = getPlayerGraces(player);
        if (!blockPosLong.contains(pos.asLong())) return;
        blockPosLong.remove(pos.asLong());

        player.eldenrpg$getPersistentData().putLongArray("graces", blockPosLong);
        syncRemoveGrace((PlayerEntity) player, pos);
    }

    public static void removeGraces(IPlayerDataSaver player) {
        for (BlockPos gracePos : GracesData.getGraces().keySet()) {
            GracesData.removeGrace(player, gracePos);
        }
    }

    public static List<Long> getPlayerGraces(IPlayerDataSaver player) {
        List<Long> graces = new ArrayList<>();
        for (long l : player.eldenrpg$getPersistentData().getLongArray("graces")) graces.add(l);
        return graces;
    }

    private static void syncAddGrace(PlayerEntity player, BlockPos pos) {
        if (player instanceof ServerPlayerEntity serverPlayer) ServerPlayNetworking.send(serverPlayer, new AddGraceS2CPacket(pos));
    }

    private static void syncRemoveGrace(PlayerEntity player, BlockPos pos) {
        if (player instanceof ServerPlayerEntity serverPlayer) ServerPlayNetworking.send(serverPlayer, new RemoveGraceS2CPacket(pos));
    }

    /* --------------- UTILS --------------- */
    private static final Map<BlockPos, Text> GRACES = new HashMap<>();

    public static Map<BlockPos, Text> getGraces() {
        return GRACES;
    }

    public static Text getGraceName(BlockPos pos) {
        return GRACES.get(pos);
    }

    static {
        // TODO bon... & translatable

        /* 1ère zone - Plaine */
        GRACES.put(new BlockPos(146, -19, -92), Text.translatable("Tuto 1"));    // le laisser à la fin du tuto ?
        GRACES.put(new BlockPos(150, 0, -87), Text.translatable("Tuto 2"));      // le laisser à la fin du tuto ?
        GRACES.put(new BlockPos(163, 67, -97), Text.translatable("Cimetière"));
        GRACES.put(new BlockPos(203, 73, -179), Text.translatable("Donjon Squelettes"));
        GRACES.put(new BlockPos(142, 61, -151), Text.translatable("Donjon proche du Chalet"));
        GRACES.put(new BlockPos(173, 73, -235), Text.translatable("Tour de guet"));
        GRACES.put(new BlockPos(177, 64, -226), Text.translatable("Donjon Tour de guet"));
        GRACES.put(new BlockPos(161, 63, -342), Text.translatable("Donjon proche Bordure"));
        GRACES.put(new BlockPos(344, 35, -90), Text.translatable("Sous-terrain Lave sous le Pont"));
        GRACES.put(new BlockPos(317, 62, -120), Text.translatable("Donjon proche du pont"));
        GRACES.put(new BlockPos(379, 70, -131), Text.translatable("Donjon Squelettes du pont"));
        GRACES.put(new BlockPos(364, 111, -113), Text.translatable("Pont"));

        /* Désert */
        GRACES.put(new BlockPos(-22, 83, -61), Text.translatable("Château"));

        /* Neige */
        GRACES.put(new BlockPos(341, 69, 134), Text.translatable("Dans la neige"));
        GRACES.put(new BlockPos(373, 58, 36), Text.translatable("Petit Donjon neige"));
        GRACES.put(new BlockPos(377, 55, 66), Text.translatable("Gros Donjon neige"));
        GRACES.put(new BlockPos(145, 73, 86), Text.translatable("Donjon aléatoire neige"));
    }

}