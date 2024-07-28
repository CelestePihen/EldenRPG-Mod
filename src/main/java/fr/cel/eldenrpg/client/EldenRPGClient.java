package fr.cel.eldenrpg.client;

import fr.cel.eldenrpg.client.model.EldenNPCModel;
import fr.cel.eldenrpg.client.model.ModModelLayers;
import fr.cel.eldenrpg.client.overlay.FlasksHudOverlay;
import fr.cel.eldenrpg.client.renderer.BlacksmithRenderer;
import fr.cel.eldenrpg.client.renderer.EldenNPCRenderer;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.event.events.KeyInputEvent;
import fr.cel.eldenrpg.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public final class EldenRPGClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
        KeyInputEvent.register();

        HudRenderCallback.EVENT.register(new FlasksHudOverlay());

        EntityRendererRegistry.register(ModEntities.ELDEN_NPC, EldenNPCRenderer::new);
        EntityRendererRegistry.register(ModEntities.BLACKSMITH_NPC, BlacksmithRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ELDEN_NPC, EldenNPCModel::createModelData);
    }

}