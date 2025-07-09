package fr.cel.eldenrpg.client;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.client.overlay.FlasksHudOverlay;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.entity.model.EldenNPCModel;
import fr.cel.eldenrpg.entity.model.ModModelLayers;
import fr.cel.eldenrpg.entity.renderer.BlacksmithRenderer;
import fr.cel.eldenrpg.entity.renderer.CatacombCarcassRenderer;
import fr.cel.eldenrpg.event.events.KeyInputEvent;
import fr.cel.eldenrpg.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.Identifier;

public final class EldenRPGClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
        KeyInputEvent.register();

        HudElementRegistry.addFirst(Identifier.of(EldenRPG.MOD_ID, "flaskshud"), new FlasksHudOverlay());

        registerEntitiesModel();
        registerEntitiesRenderer();
    }

    private void registerEntitiesModel() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ELDEN_NPC, EldenNPCModel::createModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CATACOMB_CARCASS, SkeletonEntityModel::getTexturedModelData);
    }

    private void registerEntitiesRenderer() {
        EntityRendererRegistry.register(ModEntities.BLACKSMITH_NPC, BlacksmithRenderer::new);
        EntityRendererRegistry.register(ModEntities.CATACOMB_CARCASS, CatacombCarcassRenderer::new);
    }

}