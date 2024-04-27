package fr.cel.eldenrpg.quest;

import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.network.chat.Component;

public class Quest {

    private final String id;
    private final String langName;
    private final Component translatedName;
    private QuestState questState;
    private Task task;

    public Quest(String id, String langName, Task task) {
        this(id, langName, QuestState.ACTIVE, task);
    }

    public Quest(String id, String langName, QuestState questState, Task task) {
        this.id = id;
        this.langName = langName;
        this.translatedName = Component.translatable(langName);
        this.questState = questState;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public String getLangName() {
        return langName;
    }

    public Component getTranslatedName() {
        return translatedName;
    }

    public QuestState getQuestState() {
        return questState;
    }

    public void setQuestState(QuestState questState) {
        this.questState = questState;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public enum QuestState {
        ACTIVE(),
        FINISHED(),
        COMPLETED();
    }

}