package fr.cel.eldenrpg.datagen;

import fr.cel.eldenrpg.block.ModBlocks;
import fr.cel.eldenrpg.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Items;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GRACE_BLOCK);

        blockStateModelGenerator.registerParented(Blocks.STONE, ModBlocks.STONE_GHOST_BLOCK);
        blockStateModelGenerator.registerParented(Blocks.GRAVEL, ModBlocks.GRAVEL_GHOST_BLOCK);
        blockStateModelGenerator.registerParented(Blocks.STONE_BRICKS, ModBlocks.STONE_BRICKS_GHOST_BLOCK);
        blockStateModelGenerator.registerParented(Blocks.SNOW_BLOCK, ModBlocks.SNOW_GHOST_BLOCK);
        blockStateModelGenerator.registerParented(Blocks.SANDSTONE, ModBlocks.SANDSTONE_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SPEED_TALISMAN, Models.GENERATED);
        itemModelGenerator.register(ModItems.KEY, Items.TRIAL_KEY, Models.GENERATED);
    }

}