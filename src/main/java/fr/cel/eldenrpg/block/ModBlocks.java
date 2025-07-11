package fr.cel.eldenrpg.block;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.block.custom.GhostBlock;
import fr.cel.eldenrpg.block.custom.GraceBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class ModBlocks {

    // Ghost Blocks
    public static final Block STONE_GHOST_BLOCK = registerBlock("stone_ghost_block",
            properties -> new GhostBlock("stone", Blocks.STONE));

    public static final Block GRAVEL_GHOST_BLOCK = registerBlock("gravel_ghost_block",
            properties -> new GhostBlock("gravel", Blocks.GRAVEL));

    public static final Block STONE_BRICKS_GHOST_BLOCK = registerBlock("stone_bricks_ghost_block",
            properties -> new GhostBlock("stone_bricks", Blocks.STONE_BRICKS));

    public static final Block SNOW_GHOST_BLOCK = registerBlock("snow_ghost_block",
            properties -> new GhostBlock("snow", Blocks.SNOW_BLOCK));

    public static final Block SANDSTONE_GHOST_BLOCK = registerBlock("sandstone_ghost_block",
            properties -> new GhostBlock("sandstone", Blocks.SANDSTONE));

    // Other
    public static final Block GRACE_BLOCK = registerBlock("grace_block", properties -> new GraceBlock());

    /**
     * Eenregistre un Bloc
     * @param name Le nom du bloc
     * @param function La classe du bloc avec possiblement ces propriétés
     * @return Retourne le Bloc à enregistrer
     */
    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(EldenRPG.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(EldenRPG.MOD_ID, name), toRegister);
    }

    /**
     * Enregistre un Bloc sans Item
     * @param name Le nom du bloc
     * @param function La classe du bloc
     * @return Retourne le Bloc à enregistrer
     */
    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function) {
        return Registry.register(Registries.BLOCK, Identifier.of(EldenRPG.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(EldenRPG.MOD_ID, name)))));
    }

    /**
     * Permet d'enregistrer le Bloc sous forme d'Item
     * @param name Le nom du bloc
     * @param block La classe du bloc
     */
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(EldenRPG.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EldenRPG.MOD_ID, name)))));
    }

    /**
     * Permet d'enregistrer tous les blocs de la classe
     */
    public static void registerModBlocks() {
        EldenRPG.LOGGER.info("Register Blocks for " + EldenRPG.MOD_ID);
    }

}
