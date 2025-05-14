package fr.cel.eldenrpg.areas;

import fr.cel.eldenrpg.areas.type.*;
import fr.cel.eldenrpg.entity.ModEntities;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quests;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public final class Areas {

    private static final List<Area<?>> areas = new ArrayList<>();

    static {
        // POI Area
        registerPOI("graveyard", 165, 75, -61, 136, 69, -90, Quests.BEGINNING);

        // Hint Area
        registerHint("hintgrace", 148, -21, -87, 144, -15, -93);
        registerHint("hintfakeblock", 148, 2, -75, 152, -1, -77);

        // Map Area
        registerMap(1, 196, 72, -80, 191, 67, -86);
//        registerMap(2, 0, 0, 0, 0, 0, 0);
//        registerMap(3, 0, 0, 0, 0, 0, 0);
//        registerMap(4, 0, 0, 0, 0, 0, 0);
//        registerMap(5, 0, 0, 0, 0, 0, 0);
//        registerMap(6, 0, 0, 0, 0, 0, 0);
//        registerMap(7, 0, 0, 0, 0, 0, 0);
//        registerMap(8, 0, 0, 0, 0, 0, 0);
//        registerMap(9, 0, 0, 0, 0, 0, 0);

        // Golden Seed
        registerGoldenSeed(1, 189, 69, -87, 193, 74, -90);

        // Sacred Tear
        registerSacredTear(1, 185, 66, -84, 183, 71, -86);

        // Boss Area
        registerBoss(ModEntities.CATACOMB_CARCASS, new BlockPos(146, 20, -82), 159, 19, -78, 144, 32, -89);
    }

    private static void registerPOI(String poiName, double x1, double y1, double z1, double x2, double y2, double z2, Quest quest) {
        areas.add(new POIArea(poiName, x1, y1, z1, x2, y2, z2, quest));
    }

    private static void registerHint(String advancementName, double x1, double y1, double z1, double x2, double y2, double z2) {
        areas.add(new HintArea(advancementName, x1, y1, z1, x2, y2, z2));
    }

    private static void registerMap(int mapId, double x1, double y1, double z1, double x2, double y2, double z2) {
        areas.add(new MapArea(mapId, x1, y1, z1, x2, y2, z2));
    }

    private static void registerGoldenSeed(int gsId, double x1, double y1, double z1, double x2, double y2, double z2) {
        areas.add(new GoldenSeedArea(gsId, x1, y1, z1, x2, y2, z2));
    }

    private static void registerSacredTear(int stId, double x1, double y1, double z1, double x2, double y2, double z2) {
        areas.add(new SacredTearArea(stId, x1, y1, z1, x2, y2, z2));
    }

    private static void registerBoss(EntityType<?> entityType, BlockPos spawn, double x1, double y1, double z1, double x2, double y2, double z2) {
        areas.add(new BossArea(entityType, spawn, x1, y1, z1, x2, y2, z2));
    }

    public static List<Area<?>> getAreas() {
        return areas;
    }

}