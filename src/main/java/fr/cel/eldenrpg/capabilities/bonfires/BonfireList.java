package fr.cel.eldenrpg.capabilities.bonfires;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class BonfireList {

    private static final Map<BlockPos, Component> BONFIRES = new HashMap<>();

    public static Map<BlockPos, Component> getBonfires() {
        return BONFIRES;
    }

    public static Component getBonfireName(BlockPos blockPos) {
        return BONFIRES.get(blockPos);
    }
    
    static {
        // TODO mettre TOUS les feux de camps quand map fini... trop hâte... + mettre traduction des lieux
        // TODO ou alors faire un système où on peut placer un feu de camp avec nom + mettre dans un fichier JSON
        BONFIRES.put(new BlockPos(146, -19, -92), Component.translatable("Campfire 1"));
        BONFIRES.put(new BlockPos(150, 0, -87), Component.translatable("Campfire 2"));
    }

}