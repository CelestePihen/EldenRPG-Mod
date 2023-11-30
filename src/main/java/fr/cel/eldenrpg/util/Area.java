package fr.cel.eldenrpg.util;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.Map;

public class Area {

    private final String message;
    private final boolean isHint;

    private final AABB aabb;
    private final Map<ServerPlayer, Boolean> playerInside = new HashMap<>();

    public Area(String message, double x1, double y1, double z1, double x2, double y2, double z2, boolean isHint) {
        this.message = message;
        this.aabb = new AABB(x1, y1, z1, x2, y2, z2);
        this.isHint = isHint;
    }

    public void detectPlayerInArea(ServerPlayer player) {
        boolean isPlayerIn = aabb.contains(player.getX(), player.getY(), player.getZ());
        playerInside.putIfAbsent(player, false);

        if (isPlayerIn && !playerInside.get(player)) {
            if (isHint) {
                perform(player, player.getServer().getAdvancements().getAdvancement(new ResourceLocation("eldenrpg", message)));
            } else {
                player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable(message)));
            }
            playerInside.put(player, true);
        }

        else if (!isPlayerIn && playerInside.get(player)) {
            playerInside.put(player, false);
        }

    }

    private void perform(ServerPlayer player, Advancement advancement) {
        AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(advancement);
        for (String s : advancementProgress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, s);
        }
    }

    public enum Areas {

        GRAVEYARD(new Area("eldenrpg.area.graveyard", 165, 75, -61, 136, 69, -90, false)),
        HINT_CAMPFIRE(new Area("hintfirecamp", 147, -15, -89, 144, -20, -93, true)),
        HINT_FAKE_BLOCK(new Area("hintfakeblock", 148, 2, -75, 152, -1, -77, true)),
        ;

        private final Area area;

        Areas(Area area) {
            this.area = area;
        }

        public Area getArea() {
            return area;
        }
    }

}