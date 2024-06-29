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

    // POI Area
    public static final POIArea GRAVEYARD = registerPOI("graveyard", 165, 75, -61, 136, 69, -90, Quests.BEGINNING);

    // Hint Area
    public static final HintArea BONFIRE = registerHint("hintbonfire", 148, -21, -87, 144, -15, -93);
    public static final HintArea FAKE_BLOCK = registerHint("hintfakeblock", 148, 2, -75, 152, -1, -77);

    // Map Area
    public static final MapArea ZONE_1 = registerMap("0", 194, 71, -81, 192, 68, -83);
    public static final MapArea ZONE_2 = registerMap("1", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_3 = registerMap("2", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_4 = registerMap("3", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_5 = registerMap("4", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_6 = registerMap("5", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_7 = registerMap("6", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_8 = registerMap("7", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_9 = registerMap("8", 0, 0, 0, 0, 0, 0);
    public static final MapArea ZONE_10 = registerMap("9", 0, 0, 0, 0, 0, 0);

    public static Map<String, Area> getAreas() {
        return areas;
    }

    private static POIArea registerPOI(String id, double x1, double y1, double z1, double x2, double y2, double z2) {
        return registerPOI(id, x1, y1, z1, x2, y2, z2, null);
    }

    private static POIArea registerPOI(String id, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        POIArea poiArea = new POIArea(id, x1, y1, z1, x2, y2, z2, quest);
        areas.put(id, poiArea);
        return poiArea;
    }

    private static HintArea registerHint(String id, double x1, double y1, double z1, double x2, double y2, double z2) {
        HintArea hintArea = new HintArea(id, x1, y1, z1, x2, y2, z2);
        areas.put(id, hintArea);
        return hintArea;
    }

    private static MapArea registerMap(String id, double x1, double y1, double z1, double x2, double y2, double z2) {
        MapArea mapArea = new MapArea(id, x1, y1, z1, x2, y2, z2);
        areas.put(id, mapArea);
        return mapArea;
    }

}