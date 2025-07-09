package fr.cel.eldenrpg.entity.renderer;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.entity.custom.AbstractNPCEntity;
import fr.cel.eldenrpg.entity.model.ModModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;

public class BlacksmithRenderer extends MobEntityRenderer<AbstractNPCEntity, PlayerEntityRenderState, PlayerEntityModel> {

    private static final Identifier BLACKSMITH_SKIN = Identifier.of(EldenRPG.MOD_ID, "textures/entity/npc/blacksmith.png");

    public BlacksmithRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel(context.getPart(ModModelLayers.ELDEN_NPC), false), 0.5F);
    }

    @Override
    public PlayerEntityRenderState createRenderState() {
        return new PlayerEntityRenderState();
    }

    @Override
    public Identifier getTexture(PlayerEntityRenderState state) {
        return BLACKSMITH_SKIN;
    }

}