package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SacredTearArea extends Area<Integer> {

    public SacredTearArea(int tofId, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(tofId, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayerEntity player) {
        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        if (FlasksData.getTearId(playerData).length == 0) {
            AdvancementEntry rootAdvancement = player.getServer().getAdvancementLoader().get(Identifier.of(EldenRPG.MOD_ID, "hintst"));
            if (rootAdvancement == null) return;

            PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
            for (String criteria : advancementTracker.getProgress(rootAdvancement).getUnobtainedCriteria()) {
                advancementTracker.grantCriterion(rootAdvancement, criteria);
            }

            player.sendMessage(Text.translatable("advancement.eldenrpg.sacredtear.description"));
        }

        if (FlasksData.addTearId(playerData, getObject())) {
            FlasksData.addSacredTear(playerData);
            player.sendMessage(Text.translatable("eldenrpg.area.sacredtear"), true);
        }
    }

}