package fr.cel.eldenrpg.areas;

import fr.cel.eldenrpg.event.custom.EnterAreaEvent;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;

import java.util.HashMap;
import java.util.Map;

public abstract class Area<T> {

    private final Box aabb;
    private final T object;
    protected final Quest quest;

    private final Map<ServerPlayerEntity, Boolean> playerInside = new HashMap<>();

    public Area(T object, double x1, double y1, double z1, double x2, double y2, double z2) {
        this(object, x1, y1, z1, x2, y2, z2, null);
    }

    public Area(T object, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        this.object = object;
        this.aabb = new Box(x1, y1, z1, x2, y2, z2);
        this.quest = quest;
    }

    public void detectPlayerInArea(ServerPlayerEntity player) {
        playerInside.putIfAbsent(player, false);
        boolean isPlayerIn = aabb.contains(player.getX(), player.getY(), player.getZ());

        // si dans la zone
        if (isPlayerIn && !playerInside.get(player)) {
            interact(player);
            playerInside.put(player, true);
            EnterAreaEvent.EVENT.invoker().onEnterArea(player, this, quest);
        }

        // si plus dans la zone
        else if (!isPlayerIn && playerInside.get(player)) {
            playerInside.put(player, false);
        }

    }

    /**
     * Permet de faire une interaction dès qu'un joueur entre dans la zone
     * @param player Le joueur qui est entré
     */
    protected abstract void interact(ServerPlayerEntity player);

    protected T getObject() {
        return object;
    }

    protected Box getBox() {
        return aabb;
    }

}