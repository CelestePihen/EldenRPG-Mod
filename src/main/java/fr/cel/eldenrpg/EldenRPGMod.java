package fr.cel.eldenrpg;

import com.mojang.logging.LogUtils;
import fr.cel.eldenrpg.block.ModBlocks;
import fr.cel.eldenrpg.item.ModCreativeModeTabs;
import fr.cel.eldenrpg.item.ModItems;
import fr.cel.eldenrpg.menu.ModMenus;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.sound.ModSounds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(EldenRPGMod.MOD_ID)
public class EldenRPGMod {

    public static final String MOD_ID = "eldenrpg";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EldenRPGMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModSounds.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModMenus.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

}