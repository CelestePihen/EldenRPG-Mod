package fr.cel.eldenrpg.client.model;

import fr.cel.eldenrpg.EldenRPGMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {

    public static final ModelLayerLocation ELDENNPC_LAYER = new ModelLayerLocation(
            new ResourceLocation(EldenRPGMod.MOD_ID, "eldennpc_layer"), "main");

    public static final ModelLayerLocation BLACKSMITH_LAYER = new ModelLayerLocation(
            new ResourceLocation(EldenRPGMod.MOD_ID, "blacksmith_layer"), "main");

}