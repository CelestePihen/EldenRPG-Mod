package fr.cel.eldenrpg.client.renderer;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.client.model.ModModelLayers;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class BlacksmithRenderer extends MobEntityRenderer<EldenNPC, PlayerEntityModel<EldenNPC>> {

    private static final Identifier BLACKSMITH_SKIN = Identifier.of(EldenRPG.MOD_ID, "textures/entity/npc/blacksmith.png");

    public BlacksmithRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel<>(context.getPart(ModModelLayers.ELDEN_NPC), false), 0.5F);
    }

    @Override
    public Identifier getTexture(EldenNPC entity) {
        return BLACKSMITH_SKIN;
    }
}