package fr.cel.eldenrpg.quest;

import java.util.HashMap;
import java.util.Map;

public class Quests {

    private static final Map<String, Quest> quests = new HashMap<>();

    public static Quest getQuest(String id) {
        return quests.get(id);
    }

    static {
        quests.put("beginning", new Quest("beginning", "eldenrpg.quest.beginning"));
        quests.put("blacksmith", new Quest("blacksmith", "eldenrpg.quest.blacksmith"));
    }

}