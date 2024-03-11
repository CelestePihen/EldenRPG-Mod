package fr.cel.eldenrpg.quest;

import net.minecraft.network.chat.Component;

public class Quest {

    private final String id;
    private final Component displayName;
    private QuestState questState;

    public Quest(String id, Component displayName) {
        this(id, displayName, QuestState.ACTIVE);
    }

    public Quest(String id, Component displayName, QuestState questState) {
        this.id = id;
        this.displayName = displayName;
        this.questState = questState;
    }

    public String getId() {
        return id;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public QuestState getQuestState() {
        return questState;
    }

    public void setQuestState(QuestState questState) {
        this.questState = questState;
    }

    public enum QuestState {
        ACTIVE(),
        FINISHED(),
        COMPLETED();
    }

}