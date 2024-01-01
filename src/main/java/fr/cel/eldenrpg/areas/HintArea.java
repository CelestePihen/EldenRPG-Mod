package fr.cel.eldenrpg.areas;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class HintArea extends Area {

    private final ResourceLocation resourceLocation;

    public HintArea(String advancementName, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(advancementName, x1, y1, z1, x2, y2, z2);
        resourceLocation = new ResourceLocation("eldenrpg", advancementName);
    }

    @Override
    protected void interact(ServerPlayer player, String advancementName) {
        Advancement advancement = player.getServer().getAdvancements().getAdvancement(resourceLocation);
        if (advancement == null) return;

        AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(advancement);
        for (String s : advancementProgress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, s);
        }
    }

}