package fr.cel.eldenrpg.quest;

import fr.cel.eldenrpg.quest.task.Task;
import fr.cel.eldenrpg.quest.task.Tasks;

import java.util.HashMap;
import java.util.Map;

public class Quests {

    private static final Map<String, Quest> quests = new HashMap<>();

    public static final Quest BEGINNING = register("beginning", "eldenrpg.quest.beginning", Tasks.LEAVE_TUTORIAL_CAVE);

    public static final Quest BLACKSMITH = register("blacksmith", "eldenrpg.quest.blacksmith", Tasks.KILL_5_SLIMES);

    public static final Quest TEST_ITEM = register("test_item", "eldenrpg.quest.test_item", Tasks.OBTAIN_TEST_ITEM);

    public static Quest getQuest(String id) {
        return quests.get(id);
    }

    private static Quest register(String id, String langName, Task task) {
        Quest quest = new Quest(id, langName, task);
        quests.put(id, quest);
        return quest;
    }

}