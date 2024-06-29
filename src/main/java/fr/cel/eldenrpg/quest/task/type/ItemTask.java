package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

public class ItemTask extends Task {

    private final Item item;
    private final int amount;

    public ItemTask(String id, String langName, Item item, int amount) {
        super(id, langName);
        this.item = item;
        this.amount = amount;
    }

    public void checkItems(PlayerEntity player, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        if (player.getInventory().count(item) != amount) return;

        player.sendMessage(Text.literal("DEBUG - Tu as fini la quête " + quest.getId() + " !"));
        quest.setQuestState(Quest.QuestState.FINISHED);
    }

}