package fr.cel.eldenrpg.block;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.block.custom.BonfireBlock;
import fr.cel.eldenrpg.block.custom.GhostBlock;
import fr.cel.eldenrpg.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EldenRPGMod.MOD_ID);

    public static final RegistryObject<Block> STONE_GHOST_BLOCK = registerBlock("stone_ghost_block", () -> new GhostBlock(Blocks.STONE));
    public static final RegistryObject<Block> GRAVEL_GHOST_BLOCK = registerBlock("gravel_ghost_block", () -> new GhostBlock(Blocks.GRAVEL));
    public static final RegistryObject<Block> STONE_BRICKS_GHOST_BLOCK = registerBlock("stone_bricks_ghost_block",  () -> new GhostBlock(Blocks.STONE_BRICKS));

    public static final RegistryObject<Block> BONFIRE_BLOCK = registerBlock("bonfire_block", BonfireBlock::new);

    /**
     * Permet de créer un bloc
     * @param name Le nom du bloc
     * @param block La classe du bloc (qui extends de la classe Block)
     * @return Retourne la classe du bloc
     */
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    /**
     * Permet de créer l'item du bloc
     * @param name Le nom du bloc
     * @param block Le bloc
     * @return Retourne l'item du bloc
     */
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * Permet d'enregistrer tous les blocs de la classe
     * @param eventBus Interface qui permet d'enregistrer les blocs
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
