package fr.cel.eldenrpg.areas;

public enum Areas {

    // TODO mettre les zones
    GRAVEYARD(new TitleArea("graveyard", 165, 75, -61, 136, 69, -90)),

    HINT_CAMPFIRE(new HintArea("hintfirecamp", 147, -15, -89, 144, -20, -93)),
    HINT_FAKE_BLOCK(new HintArea("hintfakeblock", 148, 2, -75, 152, -1, -77)),

    MAP_ONE(new MapArea("0", 194, 71, -81, 192, 68, -83)),

    MAP_TWO(new MapArea("1", 0, 0, 0, 0, 0, 0)),
    MAP_THREE(new MapArea("2", 0, 0, 0, 0, 0, 0)),
    MAP_FOUR(new MapArea("3", 0, 0, 0, 0, 0, 0)),
    MAP_FIVE(new MapArea("4", 0, 0, 0, 0, 0, 0)),
    MAP_SIX(new MapArea("5", 0, 0, 0, 0, 0, 0)),
    MAP_SEVEN(new MapArea("6", 0, 0, 0, 0, 0, 0)),
    MAP_EIGHT(new MapArea("7", 0, 0, 0, 0, 0, 0)),
    MAP_NINE(new MapArea("8", 0, 0, 0, 0, 0, 0)),
    ;

    private final Area area;

    Areas(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

}