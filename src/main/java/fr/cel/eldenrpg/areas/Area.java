package fr.cel.eldenrpg.areas;

import fr.cel.eldenrpg.event.custom.EnterAreaEvent;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public abstract class Area {

    private final String string;
    private final AABB aabb;
    protected final Quest quest;

    private final Map<ServerPlayer, Boolean> playerInside = new HashMap<>();

    public Area(String string, double x1, double y1, double z1, double x2, double y2, double z2) {
        this(string, x1, y1, z1, x2, y2, z2, null);
    }

    public Area(String string, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        this.string = string;
        this.aabb = new AABB(x1, y1, z1, x2, y2, z2);
        this.quest = quest;
    }

    public void detectPlayerInArea(ServerPlayer player) {
        boolean isPlayerIn = aabb.contains(player.getX(), player.getY(), player.getZ());
        playerInside.putIfAbsent(player, false);

        // si dans la zone
        if (isPlayerIn && !playerInside.get(player)) {
            interact(player, string);
            playerInside.put(player, true);
            MinecraftForge.EVENT_BUS.post(new EnterAreaEvent(player, this, quest));
        }

        // si plus dans la zone
        else if (!isPlayerIn && playerInside.get(player)) {
            playerInside.put(player, false);
        }

    }

    protected abstract void interact(ServerPlayer player, String string);

    public String getString() {
        return string;
    }

}