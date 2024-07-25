package fr.cel.eldenrpg.quest;

import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.task.Task;
import fr.cel.eldenrpg.quest.task.Tasks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quests {

    private static final Map<String, Quest> quests = new HashMap<>();

    public static final Quest BEGINNING = register("beginning", Tasks.LEAVE_TUTORIAL_CAVE);
    public static final Quest BLACKSMITH = register("blacksmith", Tasks.KILL_5_SLIMES);

    public static Quest getQuest(String id) {
        return quests.get(id);
    }

    private static Quest register(String id, Task task) {
        Quest quest = new Quest(id,  task);
        quests.put(id, quest);
        return quest;
    }

    public static NbtList writeNbt(List<Quest> quests) {
        NbtList questsList = new NbtList();

        for (Quest quest : quests) {
            NbtCompound questTag = new NbtCompound();

            questTag.putString("id", quest.getId());
            questTag.putString("questState", quest.getQuestState().toString());
            questTag.put("task", quest.getTask().writeNbt());

            questsList.add(questTag);
        }

        return questsList;
    }

    public static List<Quest> loadNbt(NbtCompound persistentData) {
        List<Quest> quests = new ArrayList<>();

        NbtList list = persistentData.getList("quests", NbtCompound.COMPOUND_TYPE);

        for (int i = 0; i < list.size(); i++) {
            NbtCompound questTag = list.getCompound(i);

            Quest quest = Quests.getQuest(questTag.getString("id"));

            if (quest != null) {
                quest.setQuestState(QuestState.valueOf(questTag.getString("questState")));
                quest.setTask(Tasks.loadNbt(questTag));
                quests.add(quest);
            }
        }

        return quests;
    }

}