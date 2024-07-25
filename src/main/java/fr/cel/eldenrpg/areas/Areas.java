package fr.cel.eldenrpg.areas;

import fr.cel.eldenrpg.areas.type.HintArea;
import fr.cel.eldenrpg.areas.type.MapArea;
import fr.cel.eldenrpg.areas.type.POIArea;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;

import java.util.HashMap;
import java.util.Map;

public class Areas {

    private static final Map<String, Area> areas = new HashMap<>();

    public static Map<String, Area> getAreas() {
        return areas;
    }

    private static void registerPOI(String arenaName, double x1, double y1, double z1, double x2, double y2, double z2) {
        registerPOI(arenaName, x1, y1, z1, x2, y2, z2, null);
    }

    private static void registerPOI(String arenaName, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        POIArea poiArea = new POIArea(arenaName, x1, y1, z1, x2, y2, z2, quest);
        areas.put(arenaName, poiArea);
    }

    private static void registerHint(String advancementName, double x1, double y1, double z1, double x2, double y2, double z2) {
        HintArea hintArea = new HintArea(advancementName, x1, y1, z1, x2, y2, z2);
        areas.put(advancementName, hintArea);
    }

    private static void registerMap(String mapId, double x1, double y1, double z1, double x2, double y2, double z2) {
        MapArea mapArea = new MapArea(mapId, x1, y1, z1, x2, y2, z2);
        areas.put(mapId, mapArea);
    }

    static {
        // POI Area
        registerPOI("graveyard", 165, 75, -61, 136, 69, -90, Quests.BEGINNING);

        // Hint Area
        registerHint("hintgrace", 148, -21, -87, 144, -15, -93);
        registerHint("hintfakeblock", 148, 2, -75, 152, -1, -77);

        // Map Area
        registerMap("1", 192, 73, -80, 194, 67, -84);
        registerMap("2", 0, 0, 0, 0, 0, 0);
        registerMap("3", 0, 0, 0, 0, 0, 0);
        registerMap("4", 0, 0, 0, 0, 0, 0);
        registerMap("5", 0, 0, 0, 0, 0, 0);
        registerMap("6", 0, 0, 0, 0, 0, 0);
        registerMap("7", 0, 0, 0, 0, 0, 0);
        registerMap("8", 0, 0, 0, 0, 0, 0);
        registerMap("9", 0, 0, 0, 0, 0, 0);
    }

}