package fr.cel.eldenrpg.item;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ELDEN_RPG_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(EldenRPG.MOD_ID, "eldenrpg"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.eldenrpg"))
                    .icon(() -> new ItemStack(ModItems.SPEED_TALISMAN)).entries((displayContext, entries) -> {
                        entries.add(ModItems.TEST_ITEM);
                        entries.add(ModItems.SPEED_TALISMAN);

                        entries.add(ModBlocks.STONE_GHOST_BLOCK);
                        entries.add(ModBlocks.GRAVEL_GHOST_BLOCK);
                        entries.add(ModBlocks.STONE_BRICKS_GHOST_BLOCK);
                        entries.add(ModBlocks.SNOW_GHOST_BLOCK);
                        entries.add(ModBlocks.GRACE_BLOCK);
                    }).build());

    public static void registerItemGroups() {
        EldenRPG.LOGGER.info("Enregistrement de l'ItemGroup pour " + EldenRPG.MOD_ID);
    }

}
