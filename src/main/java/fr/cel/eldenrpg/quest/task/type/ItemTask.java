package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;

public class ItemTask extends Task {

    private final Item item;
    private final int amount;

    public ItemTask(String id, Item item, int amount) {
        super(id);
        this.item = item;
        this.amount = amount;
    }

    public void checkItems(ServerPlayerEntity player, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        if (player.getInventory().count(item) != amount) return;
        quest.setQuestState(Quest.QuestState.FINISHED);
    }

}