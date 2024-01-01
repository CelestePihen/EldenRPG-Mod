package fr.cel.eldenrpg.client.data;

import java.util.ArrayList;
import java.util.List;

public class ClientMapsData {

    private final static List<Integer> playerMaps = new ArrayList<>();

    public static void add(int mapId) {
        if (playerMaps.contains(mapId)) return;
        ClientMapsData.playerMaps.add(mapId);
    }

    public static List<Integer> getPlayerMaps() {
        return playerMaps;
    }

}
