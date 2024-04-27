package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ItemTask extends Task {

    private final Item item;
    private final int amount;

    public ItemTask(String id, String langName, Item item, int amount) {
        super(id, langName);
        this.item = item;
        this.amount = amount;
    }

    public void checkItems(Player player, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        if (player.getInventory().countItem(item) == amount) {
            player.sendSystemMessage(Component.literal("Tu as fini la quête ").append(quest.getTranslatedName()).append(" !"));
            quest.setQuestState(Quest.QuestState.FINISHED);
        }
    }

}