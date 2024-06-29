package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {

    public static final EntityModelLayer ELDEN_NPC = new EntityModelLayer(Identifier.of(EldenRPG.MOD_ID, "eldennpc_layer"), "main");

}
