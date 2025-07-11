package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.areas.Area;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HintArea extends Area<String> {

    private final Identifier identifier;

    public HintArea(String advancementName, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(advancementName, x1, y1, z1, x2, y2, z2);
        this.identifier = Identifier.of(EldenRPG.MOD_ID, advancementName);
    }

    @Override
    protected void interact(ServerPlayerEntity player) {
        AdvancementEntry advancement = player.getServer().getAdvancementLoader().get(identifier);
        if (advancement == null) return;

        PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
        // si le joueur a déjà eu l'indice
        if (advancementTracker.getProgress(advancement).isDone()) return;

        for (String criteria : advancementTracker.getProgress(advancement).getUnobtainedCriteria()) {
            advancementTracker.grantCriterion(advancement, criteria);
        }

        String nameWithoutHint = getObject().substring(4);
        player.sendMessage(Text.translatable("advancement.eldenrpg." + nameWithoutHint + ".description"));
    }

}