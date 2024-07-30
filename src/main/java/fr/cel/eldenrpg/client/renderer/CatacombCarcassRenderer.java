package fr.cel.eldenrpg.client.renderer;

import fr.cel.eldenrpg.client.model.CatacombCarcassModel;
import fr.cel.eldenrpg.entity.custom.CatacombCarcassEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class CatacombCarcassRenderer extends BipedEntityRenderer<CatacombCarcassEntity, CatacombCarcassModel> {

    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/skeleton/skeleton.png");

    public CatacombCarcassRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.SKELETON, EntityModelLayers.SKELETON_INNER_ARMOR, EntityModelLayers.SKELETON_OUTER_ARMOR);
    }

    public CatacombCarcassRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legArmorLayer, EntityModelLayer bodyArmorLayer) {
        this(ctx, legArmorLayer, bodyArmorLayer, new CatacombCarcassModel(ctx.getPart(layer)));
    }

    public CatacombCarcassRenderer(EntityRendererFactory.Context context, EntityModelLayer entityModelLayer,
                                   EntityModelLayer entityModelLayer2, CatacombCarcassModel skeletonEntityModel) {
        super(context, skeletonEntityModel, 0.5F);
        this.addFeature(new ArmorFeatureRenderer<>(
                this, new CatacombCarcassModel(context.getPart(entityModelLayer)),
                new CatacombCarcassModel(context.getPart(entityModelLayer2)),
                context.getModelManager())
        );
    }

    @Override
    public Identifier getTexture(CatacombCarcassEntity entity) {
        return TEXTURE;
    }

}