package fr.cel.eldenrpg.areas;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.Map;

public abstract class Area {

    private final String string;

    private final AABB aabb;

    private final Map<ServerPlayer, Boolean> playerInside = new HashMap<>();

    public Area(String string, double x1, double y1, double z1, double x2, double y2, double z2) {
        this.string = string;
        this.aabb = new AABB(x1, y1, z1, x2, y2, z2);
    }

    public void detectPlayerInArea(ServerPlayer player) {
        boolean isPlayerIn = aabb.contains(player.getX(), player.getY(), player.getZ());
        playerInside.putIfAbsent(player, false);

        // si dans la zone
        if (isPlayerIn && !playerInside.get(player)) {
            interact(player, string);
            playerInside.put(player, true);
        }

        // si plus dans la zone
        else if (!isPlayerIn && playerInside.get(player)) {
            playerInside.put(player, false);
        }

    }

    protected abstract void interact(ServerPlayer player, String string);

}