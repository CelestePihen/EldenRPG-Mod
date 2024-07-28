package fr.cel.eldenrpg.client.renderer;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.client.model.ModModelLayers;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class EldenNPCRenderer extends MobEntityRenderer<EldenNPC, PlayerEntityModel<EldenNPC>> {

    private static final Identifier DEFAULT_SKIN = Identifier.ofVanilla("textures/entity/player/wide/steve.png");

    private static final Identifier CEL_SKIN = Identifier.of(EldenRPG.MOD_ID, "textures/entity/npc/cel.png");
    private static final Identifier DEADSKY_SKIN = Identifier.of(EldenRPG.MOD_ID, "textures/entity/npc/deadsky.png");

    public EldenNPCRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel<>(context.getPart(ModModelLayers.ELDEN_NPC), false), 0.5F);
    }

    @Override
    public Identifier getTexture(EldenNPC entity) {
        return switch (Formatting.strip(entity.getName().getString())) {
            case "Cel" -> CEL_SKIN;
            case "Deadsky" -> DEADSKY_SKIN;
            case null, default -> DEFAULT_SKIN;
        };
    }

}