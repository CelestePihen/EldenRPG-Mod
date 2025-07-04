package fr.cel.eldenrpg.client.model;

import fr.cel.eldenrpg.entity.custom.CatacombCarcassEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class CatacombCarcassModel extends BipedEntityModel<CatacombCarcassEntity> {

    public CatacombCarcassModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        addLimbs(modelPartData);
        return TexturedModelData.of(modelData, 64, 32);
    }

    protected static void addLimbs(ModelPartData data) {
        data.addChild(
                EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F)
        );

        data.addChild(
                EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F)
        );

        data.addChild(
                EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F),
                ModelTransform.pivot(-2.0F, 12.0F, 0.0F)
        );

        data.addChild(
                EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F),
                ModelTransform.pivot(2.0F, 12.0F, 0.0F)
        );
    }

    public void animateModel(CatacombCarcassEntity mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.ITEM;
        this.leftArmPose = ArmPose.EMPTY;
        if (mobEntity.isAttacking()) {
            this.rightArmPose = ArmPose.BOW_AND_ARROW;
        }

        super.animateModel(mobEntity, f, g, h);
    }

    public void setAngles(CatacombCarcassEntity mobEntity, float f, float g, float h, float i, float j) {
        super.setAngles(mobEntity, f, g, h, i, j);
        if (mobEntity.isAttacking()) {
            float k = MathHelper.sin(this.handSwingProgress * (float) Math.PI);
            float l = MathHelper.sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * (float) Math.PI);
            this.rightArm.roll = 0.0F;
            this.leftArm.roll = 0.0F;
            this.rightArm.yaw = -(0.1F - k * 0.6F);
            this.leftArm.yaw = 0.1F - k * 0.6F;
            this.rightArm.pitch = (float) (-Math.PI / 2);
            this.leftArm.pitch = (float) (-Math.PI / 2);
            this.rightArm.pitch -= k * 1.2F - l * 0.4F;
            this.leftArm.pitch -= k * 1.2F - l * 0.4F;
            CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
        }
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0F : -1.0F;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }

}
