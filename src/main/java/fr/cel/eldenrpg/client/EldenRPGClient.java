package fr.cel.eldenrpg.client;

import fr.cel.eldenrpg.client.model.CatacombCarcassModel;
import fr.cel.eldenrpg.client.model.EldenNPCModel;
import fr.cel.eldenrpg.client.model.ModModelLayers;
import fr.cel.eldenrpg.client.overlay.FlasksHudOverlay;
import fr.cel.eldenrpg.client.renderer.BlacksmithRenderer;
import fr.cel.eldenrpg.client.renderer.CatacombCarcassRenderer;
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

        registerEntitiesModel();
        registerEntitiesRenderer();
    }

    private void registerEntitiesModel() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ELDEN_NPC, EldenNPCModel::createModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CATACOMB_CARCASS, CatacombCarcassModel::getTexturedModelData);
    }

    private void registerEntitiesRenderer() {
        EntityRendererRegistry.register(ModEntities.BLACKSMITH_NPC, BlacksmithRenderer::new);
        EntityRendererRegistry.register(ModEntities.CATACOMB_CARCASS, CatacombCarcassRenderer::new);
    }

}