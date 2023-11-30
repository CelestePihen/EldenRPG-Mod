package fr.cel.eldenrpg.datagen;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.block.ModBlocks;
import fr.cel.eldenrpg.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
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
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject, Block apparence) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(apparence));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}