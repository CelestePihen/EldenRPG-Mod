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

public class GoldenSeedArea extends Area<Integer> {

    public GoldenSeedArea(Integer gsId, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(gsId, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayerEntity player) {
        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        if (FlasksData.getGSId(playerData).isEmpty()) {
            AdvancementEntry rootAdvancement = player.server.getAdvancementLoader().get(Identifier.of(EldenRPG.MOD_ID, "hintgs"));
            if (rootAdvancement == null) return;

            PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
            for (String criteria : advancementTracker.getProgress(rootAdvancement).getUnobtainedCriteria()) {
                advancementTracker.grantCriterion(rootAdvancement, criteria);
            }

            player.sendMessage(Text.translatable("advancement.eldenrpg.goldenseed.description"));
        }

        if (FlasksData.addGSId(playerData, getObject())) {
            FlasksData.addGoldenSeed(playerData);
            player.sendMessage(Text.translatable("eldenrpg.area.goldenseed"), true);
        }
    }

}