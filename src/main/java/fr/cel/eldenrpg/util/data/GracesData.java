package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.networking.packets.bonfires.GracesSyncDataS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GracesData {

    public static void addGrace(IPlayerDataSaver player, BlockPos pos) {
        List<Long> blockPosLong = getGraces(player);
        if (blockPosLong.contains(pos.asLong())) return;
        blockPosLong.add(pos.asLong());

        player.eldenrpg$getPersistentData().putLongArray("graces", blockPosLong);
        syncGraces((ServerPlayerEntity) player, pos);
    }

    public static List<Long> getGraces(IPlayerDataSaver player) {
        List<Long> temp = new ArrayList<>();
        for (long l : player.eldenrpg$getPersistentData().getLongArray("graces")) {
            temp.add(l);
        }
        return temp;
    }

    private static final Map<BlockPos, Text> GRACES = new HashMap<>();

    public static Map<BlockPos, Text> getGraces() {
        return GRACES;
    }

    public static Text getGraceName(BlockPos pos) {
        return GRACES.get(pos);
    }

    static {
        // TODO mettre TOUS les feux de camps quand map fini... trop hâte... + mettre traduction des lieux
        // TODO ou alors faire un système où on peut placer un feu de camp avec nom + mettre dans un fichier JSON
        // TODO translatable
        GRACES.put(new BlockPos(146, -19, -92), Text.translatable("Campfire 1"));
        GRACES.put(new BlockPos(150, 0, -87), Text.translatable("Campfire 2"));
    }

    public static void syncGraces(ServerPlayerEntity player, BlockPos pos) {
        ServerPlayNetworking.send(player, new GracesSyncDataS2CPacket(pos));
    }

}