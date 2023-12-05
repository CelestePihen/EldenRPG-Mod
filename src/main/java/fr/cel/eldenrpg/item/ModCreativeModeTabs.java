package fr.cel.eldenrpg.item;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EldenRPGMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ELDENRPG = CREATIVE_MODE_TABS.register(
            "eldenrpg",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.NETHERITE_SWORD))
                    .title(Component.translatable("itemGroup.eldenrpg"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.STONE_GHOST_BLOCK.get());
                        pOutput.accept(ModBlocks.GRAVEL_GHOST_BLOCK.get());
                        pOutput.accept(ModBlocks.STONE_BRICKS_GHOST_BLOCK.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
