package fr.cel.eldenrpg.client.model;

import fr.cel.eldenrpg.entity.custom.AbstractNPCEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class EldenNPCModel extends PlayerEntityModel<AbstractNPCEntity> {

    public EldenNPCModel(ModelPart root) {
        super(root, false);
    }

    public static TexturedModelData createModelData() {
        return TexturedModelData.of(PlayerEntityModel.getTexturedModelData(Dilation.NONE, false), 64, 64);
    }

}