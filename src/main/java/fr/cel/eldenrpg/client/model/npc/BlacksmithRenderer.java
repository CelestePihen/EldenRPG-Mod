package fr.cel.eldenrpg.client.model.npc;

import fr.cel.eldenrpg.client.model.ModModelLayers;
import fr.cel.eldenrpg.entity.npcs.BlacksmithNPC;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class BlacksmithRenderer extends LivingEntityRenderer<BlacksmithNPC, PlayerModel<BlacksmithNPC>> {

    private static final ResourceLocation BLACKSMITH_SKIN = new ResourceLocation("eldenrpg", "textures/entity/npc/blacksmith.png");

    public BlacksmithRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlayerModel<>(pContext.bakeLayer(ModModelLayers.BLACKSMITH_LAYER), false), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BlacksmithNPC pEntity) {
        return BLACKSMITH_SKIN;
    }

}