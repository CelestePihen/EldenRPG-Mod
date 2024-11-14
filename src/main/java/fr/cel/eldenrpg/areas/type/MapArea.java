package fr.cel.eldenrpg.areas.type;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.MapsData;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MapArea extends Area<Integer> {

    public MapArea(int mapId, double x1, double y1, double z1, double x2, double y2, double z2) {
        super(mapId, x1, y1, z1, x2, y2, z2);
    }

    @Override
    protected void interact(ServerPlayerEntity player) {
        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        if (MapsData.getMapsId(playerData).isEmpty()) {
            AdvancementEntry rootAdvancement = player.server.getAdvancementLoader().get(Identifier.of(EldenRPG.MOD_ID, "hintmap"));
            if (rootAdvancement == null) return;

            PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
            for (String criteria : advancementTracker.getProgress(rootAdvancement).getUnobtainedCriteria()) {
                advancementTracker.grantCriterion(rootAdvancement, criteria);
            }
        }

        if (MapsData.addMapId(playerData, getObject())) {
            player.sendMessage(Text.translatable("eldenrpg.area.partofmap"), true);
            player.playSoundToPlayer(ModSounds.MAP_FOUND, SoundCategory.AMBIENT, 0.5F, 1.0F);
        }
    }
}