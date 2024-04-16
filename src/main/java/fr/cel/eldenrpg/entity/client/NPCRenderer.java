package fr.cel.eldenrpg.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.cel.eldenrpg.entity.EldenNPC;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class NPCRenderer extends LivingEntityRenderer<EldenNPC, PlayerModel<EldenNPC>> {

    private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("textures/entity/player/wide/steve.png");

    private static final ResourceLocation CEL_SKIN = new ResourceLocation("eldenrpg", "textures/entity/npc/cel___.png");
    private static final ResourceLocation DEADSKY666_SKIN = new ResourceLocation("eldenrpg", "textures/entity/npc/deadsky666.png");

    public NPCRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlayerModel<>(pContext.bakeLayer(ModModelLayers.ELDENNPC_LAYER), false), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(EldenNPC pEntity) {
        String s = ChatFormatting.stripFormatting(pEntity.getName().getString());
        // TODO à refaire
        if ("Cel___".equals(s)) {
            return CEL_SKIN;
        }

        else if ("Deadsky666".equals(s)) {
            return DEADSKY666_SKIN;
        }

        else {
            return DEFAULT_SKIN;
        }
    }

    @Override
    protected void scale(EldenNPC npc, PoseStack poseStack, float pPartialTickTime) {
        if (npc.isBaby()) {
            poseStack.scale(0.8f, 0.8f, 0.8f);
        }
    }
}