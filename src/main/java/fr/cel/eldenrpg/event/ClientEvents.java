package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.client.gui.MapScreen;
import fr.cel.eldenrpg.client.gui.SlotsScreen;
import fr.cel.eldenrpg.client.overlay.FlasksHudOverlay;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.client.ModModelLayers;
import fr.cel.eldenrpg.entity.client.NPCRenderer;
import fr.cel.eldenrpg.menu.ModMenus;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.backpack.OpenBackpackC2SPacket;
import fr.cel.eldenrpg.networking.packet.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.util.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (ModKeyBindings.OPEN_MAP.consumeClick()) {
                Minecraft.getInstance().setScreen(new MapScreen());
            }

            else if (ModKeyBindings.DRINK_FLASK.consumeClick()) {
                ModMessages.sendToServer(new DrinkFlaskC2SPacket());
            }

            else if (ModKeyBindings.BACKPACK.consumeClick()) {
                ModMessages.sendToServer(new OpenBackpackC2SPacket());
            }
        }

//        @SubscribeEvent
//        public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
//            BlockPos pos = event.getPos();
//
//            if (event.getLevel().getBlockState(pos).getBlock() == Blocks.SOUL_CAMPFIRE && event.getHand() == InteractionHand.MAIN_HAND) {
//                Minecraft.getInstance().getConnection().setTitleText(new ClientboundSetTitleTextPacket(Component.translatable("eldenrpg.title.setspawn")));
//                Minecraft.getInstance().getConnection().setTitlesAnimation(new ClientboundSetTitlesAnimationPacket(20, 50, 20));
//                ModMessages.sendToServer(new SetSpawnC2SPacket(pos));
//                event.getLevel().playSeededSound(event.getEntity(), pos.getX(), pos.getY(), pos.getZ(), ModSounds.LOST_GRACE_DISCOVERED.get(), SoundSource.AMBIENT,
//                        0.5f, 1f, 0);
//            }
//        }

        @SubscribeEvent
        public static void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event) {
            if (Minecraft.getInstance().player.getAbilities().invulnerable) return;

            if (event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type()) {
                renderCustomHealthBar(event.getGuiGraphics());
                event.setCanceled(true);
            }

            if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
                event.setCanceled(true);
            }
        }

        private static void renderCustomHealthBar(GuiGraphics pGuiGraphics) {
            Minecraft mc = Minecraft.getInstance();

            float health = mc.player.getHealth();
            float maxHealth = mc.player.getMaxHealth();

            int barWidth = 180;
            int barHeight = 10;
            int barX = (pGuiGraphics.guiWidth() - barWidth) / 2;
            int barY = (pGuiGraphics.guiHeight()- barHeight) - 37;

            int currentHealthWidth = (int) ((double) health / maxHealth * barWidth);

            pGuiGraphics.fill(barX, barY, barX + currentHealthWidth, barY + barHeight, 0xFF00FF00);

            String healthText = String.format("%.1f", health) + " / " + String.format("%.1f", maxHealth);
            int textX = barX + (barWidth - mc.font.width(healthText)) / 2;
            pGuiGraphics.drawString(mc.font, healthText, textX, barY + 1, Color.WHITE.getRGB(), true);
        }

    }

    @Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.OPEN_MAP);
            event.register(ModKeyBindings.DRINK_FLASK);
            event.register(ModKeyBindings.BACKPACK);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenus.BACKPACK_MENU.get(), SlotsScreen::new);
            EntityRenderers.register(ModEntities.NPC.get(), NPCRenderer::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("flasks", new FlasksHudOverlay());
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.ELDENNPC_LAYER, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64));
        }

    }

}
