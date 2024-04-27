package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ZoneTask extends Task {

    public ZoneTask(String id, String langName) {
        super(id, langName);
    }

    public void interact(ServerPlayer player, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        player.sendSystemMessage(Component.literal("Tu as fini la quête ").append(quest.getTranslatedName()).append(" !"));
        quest.setQuestState(Quest.QuestState.FINISHED);
    }

}