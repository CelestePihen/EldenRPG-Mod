package fr.cel.eldenrpg.client.render;

import fr.cel.eldenrpg.entity.ModModelLayers;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class EldenNPCRenderer extends MobEntityRenderer<EldenNPC, PlayerEntityModel<EldenNPC>> {

    private static final Identifier DEFAULT_SKIN = Identifier.ofVanilla("textures/entity/player/wide/steve.png");

    private static final Identifier CEL_SKIN = Identifier.of("eldenrpg", "textures/entity/npc/cel.png");
    private static final Identifier DEADSKY_SKIN = Identifier.of("eldenrpg", "textures/entity/npc/deadsky.png");

    private static final Identifier BLACKSMITH_SKIN = Identifier.of("eldenrpg", "textures/entity/npc/blacksmith.png");

    public EldenNPCRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel<>(context.getPart(ModModelLayers.ELDEN_NPC), false), 0.5F);
    }

    @Override
    public Identifier getTexture(EldenNPC entity) {
        String s = Formatting.strip(entity.getName().getString());
        return switch (s) {
            case "Cel" -> CEL_SKIN;
            case "Deadsky" -> DEADSKY_SKIN;
            case "Blacksmith", "Forgeron" -> BLACKSMITH_SKIN;
            case null, default -> DEFAULT_SKIN;
        };
    }

    @Override
    public void render(EldenNPC livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.8F, 0.8F, 0.8F);
        } else {
            matrixStack.scale(1F, 1F, 1F);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}