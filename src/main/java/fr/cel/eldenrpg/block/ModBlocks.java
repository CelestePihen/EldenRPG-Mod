package fr.cel.eldenrpg.block;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.block.custom.GhostBlock;
import fr.cel.eldenrpg.block.custom.GraceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block STONE_GHOST_BLOCK = registerBlock("stone_ghost_block", new GhostBlock(Blocks.STONE));
    public static final Block GRAVEL_GHOST_BLOCK = registerBlock("gravel_ghost_block", new GhostBlock(Blocks.GRAVEL));
    public static final Block STONE_BRICKS_GHOST_BLOCK = registerBlock("stone_bricks_ghost_block",  new GhostBlock(Blocks.STONE_BRICKS));
    public static final Block SNOW_GHOST_BLOCK = registerBlock("snow_ghost_block",  new GhostBlock(Blocks.SNOW_BLOCK));
    public static final Block SANDSTONE_BLOCK = registerBlock("sandstone_block",  new GhostBlock(Blocks.SANDSTONE));

    public static final Block GRACE_BLOCK = registerBlock("grace_block", new GraceBlock());

    /**
     * Permet d'enregistrer le Bloc
     * @param name Le nom du bloc
     * @param block La classe du bloc
     * @return Retourne le Bloc qui vient d'être enregistré
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(EldenRPG.MOD_ID, name), block);
    }

    /**
     * Permet d'enregistrer le Bloc sous forme d'Item
     * @param name Le nom du bloc
     * @param block La classe du bloc
     * @return Retourne le Bloc sous forme d'item qui vient d'être enregistré
     */
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(EldenRPG.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    /**
     * Permet d'enregistrer tous les blocs de la classe
     */
    public static void registerModBlocks() {
        EldenRPG.LOGGER.info("Enregistrement des Blocs pour " + EldenRPG.MOD_ID);
    }

}
