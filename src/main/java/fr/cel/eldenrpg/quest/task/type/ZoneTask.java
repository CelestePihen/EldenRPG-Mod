package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ZoneTask extends Task {

    public ZoneTask(String id, String langName) {
        super(id, langName);
    }

    public void interact(ServerPlayerEntity player, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        player.sendMessage(Text.literal("Tu as fini la quête " + quest.getId() + " !"));
        quest.setQuestState(Quest.QuestState.COMPLETED);
    }

}