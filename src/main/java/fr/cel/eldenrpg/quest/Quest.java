package fr.cel.eldenrpg.quest;

import net.minecraft.network.chat.Component;

public class Quest {

    private final String id;
    private final String langName;
    private final Component translatedName;
    private QuestState questState;

    public Quest(String id, String langName) {
        this(id, langName, QuestState.ACTIVE);
    }

    public Quest(String id, String langName, QuestState questState) {
        this.id = id;
        this.langName = langName;
        this.translatedName = Component.translatable(langName);
        this.questState = questState;
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

    public enum QuestState {
        ACTIVE(),
        FINISHED(),
        COMPLETED();
    }

}