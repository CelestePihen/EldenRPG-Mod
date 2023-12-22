package fr.cel.eldenrpg.entity.client;

import fr.cel.eldenrpg.entity.EldenNPC;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NPCRenderer extends MobRenderer<EldenNPC, PlayerModel<EldenNPC>> {

    private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("textures/entity/player/wide/steve.png");

    private static final ResourceLocation CEL_SKIN = new ResourceLocation("eldenrpg", "textures/entity/npc/cel___.png");
    private static final ResourceLocation DEADSKY666_SKIN = new ResourceLocation("eldenrpg", "textures/entity/npc/deadsky666.png");

    public NPCRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlayerModel<>(pContext.bakeLayer(ModModelLayers.ELDENNPC_LAYER), false), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(EldenNPC pEntity) {
        String s = ChatFormatting.stripFormatting(pEntity.getName().getString());
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

}