package fr.cel.eldenrpg.entity.renderer;

import fr.cel.eldenrpg.entity.custom.CatacombCarcassEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;
import net.minecraft.util.Identifier;

public class CatacombCarcassRenderer extends BipedEntityRenderer<CatacombCarcassEntity, SkeletonEntityRenderState, SkeletonEntityModel<SkeletonEntityRenderState>> {

    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/skeleton/skeleton.png");

    public CatacombCarcassRenderer(EntityRendererFactory.Context context) {
        super(context, new SkeletonEntityModel<>(context.getEntityModels().getModelPart(EntityModelLayers.SKELETON)), 0.5f);
    }

    @Override
    public SkeletonEntityRenderState createRenderState() {
        return new SkeletonEntityRenderState();
    }

    @Override
    public Identifier getTexture(SkeletonEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(CatacombCarcassEntity mobEntity, SkeletonEntityRenderState skeletonEntityRenderState, float f) {
        super.updateRenderState(mobEntity, skeletonEntityRenderState, f);
        skeletonEntityRenderState.attacking = mobEntity.isAttacking();
    }
}