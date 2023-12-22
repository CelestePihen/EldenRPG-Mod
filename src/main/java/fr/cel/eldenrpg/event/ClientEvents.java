package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.client.gui.SlotsScreen;
import fr.cel.eldenrpg.client.overlay.FlasksHudOverlay;
import fr.cel.eldenrpg.client.gui.MapScreen;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.client.ModModelLayers;
import fr.cel.eldenrpg.entity.client.NPCRenderer;
import fr.cel.eldenrpg.menu.ModMenus;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packet.firecamp.SetSpawnC2SPacket;
import fr.cel.eldenrpg.networking.packet.backpack.OpenBackpackC2SPacket;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.OPEN_MAP.consumeClick()) {
                Minecraft.getInstance().setScreen(new MapScreen());
            }

            else if (KeyBinding.DRINK_FLASK.consumeClick()) {
                ModMessages.sendToServer(new DrinkFlaskC2SPacket());
            }

            else if (KeyBinding.BACKPACK.consumeClick()) {
                ModMessages.sendToServer(new OpenBackpackC2SPacket());
            }
        }

        @SubscribeEvent
        public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
            Player player = event.getEntity();
            BlockPos pos = event.getPos();

            // Campfire
            if (event.getLevel().getBlockState(pos).getBlock() == Blocks.SOUL_CAMPFIRE && event.getHand() == InteractionHand.MAIN_HAND) {
                ModMessages.sendToServer(new SetSpawnC2SPacket(pos));
                event.getLevel().playSeededSound(player, pos.getX(), pos.getY(), pos.getZ(), ModSounds.LOST_GRACE_DISCOVERED.get(), SoundSource.AMBIENT,
                        0.5f, 1f, 0);
            }
        }

//        @SubscribeEvent
//        public static void onOpenGui(ScreenEvent event) {
//
//            if (event.getScreen() == null) return;
//
//            if (event.getScreen().getClass() == TitleScreen.class) {
//
//            }
//
//        }

    }

    @Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OPEN_MAP);
            event.register(KeyBinding.DRINK_FLASK);
            event.register(KeyBinding.BACKPACK);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenus.BACKPACK_MENU.get(), SlotsScreen::new);
            EntityRenderers.register(ModEntities.NPC.get(), NPCRenderer::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("flasks", FlasksHudOverlay.HUD_FLASKS);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.ELDENNPC_LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
        }

    }

}
