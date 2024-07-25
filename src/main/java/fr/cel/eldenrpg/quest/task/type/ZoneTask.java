package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;

public class ZoneTask extends Task {

    public ZoneTask(String id) {
        super(id);
    }

    public void interact(Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        quest.setQuestState(Quest.QuestState.COMPLETED);
    }

}