package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class HintArea extends Area {

    private final ResourceLocation resourceLocation;

    public HintArea(String advancementName, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(advancementName, x1, y1, z1, x2, y2, z2);
        resourceLocation = new ResourceLocation("eldenrpg", advancementName);
    }

    @Override
    protected void interact(ServerPlayer player, String advancementName) {
        Advancement rootAdvancement = ServerLifecycleHooks.getCurrentServer().getAdvancements().getAdvancement(resourceLocation);
        if (rootAdvancement == null) return;
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(rootAdvancement);
        for (String criteria : progress.getRemainingCriteria()) {
            player.getAdvancements().award(rootAdvancement, criteria);
        }
    }

}