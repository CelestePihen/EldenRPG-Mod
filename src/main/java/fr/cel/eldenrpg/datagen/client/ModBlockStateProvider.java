package fr.cel.eldenrpg.datagen.client;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EldenRPGMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.STONE_GHOST_BLOCK, Blocks.STONE);
        blockWithItem(ModBlocks.GRAVEL_GHOST_BLOCK, Blocks.GRAVEL);
        blockWithItem(ModBlocks.STONE_BRICKS_GHOST_BLOCK, Blocks.STONE_BRICKS);
        blockWithItem(ModBlocks.BONFIRE_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject, Block block) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(block));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}