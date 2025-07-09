package fr.cel.eldenrpg.datagen;

import fr.cel.eldenrpg.block.ModBlocks;
import fr.cel.eldenrpg.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Items;

public final class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STONE_GHOST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GRAVEL_GHOST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STONE_BRICKS_GHOST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SNOW_GHOST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SANDSTONE_GHOST_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SPEED_TALISMAN, Models.GENERATED);
        itemModelGenerator.registerWithTextureSource(ModItems.KEY, Items.TRIAL_KEY, Models.GENERATED);
    }

}