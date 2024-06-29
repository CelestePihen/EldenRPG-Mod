package fr.cel.eldenrpg.client;

import fr.cel.eldenrpg.client.gui.overlay.FlasksHudOverlay;
import fr.cel.eldenrpg.client.render.EldenNPCRenderer;
import fr.cel.eldenrpg.client.render.model.EldenNPCModel;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.ModModelLayers;
import fr.cel.eldenrpg.event.events.KeyInputHandler;
import fr.cel.eldenrpg.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EldenRPGClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
        KeyInputHandler.register();

        HudRenderCallback.EVENT.register(new FlasksHudOverlay());

        EntityRendererRegistry.register(ModEntities.ELDEN_NPC, EldenNPCRenderer::new);
        EntityRendererRegistry.register(ModEntities.BLACKSMITH_NPC, EldenNPCRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ELDEN_NPC, EldenNPCModel::createModelData);
    }

}