package fr.cel.eldenrpg.capabilities.quests;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class PlayerQuests {

    private final Player player;

    private List<Quest> quests = new ArrayList<>();

    public PlayerQuests(Player player) {
        this.player = player;
    }

    public Quest getQuest(String id) {
        for (Quest quest : quests) {
            if (quest.getId().equals(id)) return quest;
        }
        return null;
    }

    public boolean hasQuest(Quest quest) {
        return quests.contains(quest);
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
        EldenRPGMod.LOGGER.info("Le joueur " + player.getName().getString() + " a obtenu la quête " + quest.getDisplayName().getString() + "(id:" + quest.getId() + ").");
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
        EldenRPGMod.LOGGER.info("Le joueur " + player.getName().getString() + " s'est fait enlever la quête " + quest.getDisplayName().getString() + "(id:" + quest.getId() + ").");
    }

    public void setQuestState(String id, QuestState newState) {
        for (Quest quest : quests) {
            if (quest.getId().equals(id)) {
                quest.setQuestState(newState);
                return;
            }
        }
    }

    public void copyFrom(PlayerQuests source) {
        this.quests = source.quests;
    }

    public void saveNBTData(CompoundTag nbt) {
        ListTag questsList = new ListTag();
        for (Quest quest : quests) {
            CompoundTag tag = new CompoundTag();

            tag.putString("id", quest.getId());
            tag.putString("displayName", quest.getDisplayName().getString());
            tag.putString("state", quest.getQuestState().toString());

            questsList.add(tag);
        }
        nbt.put("quests", questsList);
    }

    public void loadNBTData(CompoundTag nbt) {
        List<Quest> temp_quests = new ArrayList<>();
        ListTag questsList = nbt.getList("quests", Tag.TAG_COMPOUND);

        for (int i = 0; i < questsList.size(); i++) {
            CompoundTag questsTags = questsList.getCompound(i);

            String id = questsTags.getString("id");
            Component displayName = Component.translatable(questsTags.getString("displayName"));
            QuestState state = QuestState.valueOf(questsTags.getString("state"));

            temp_quests.add(new Quest(id, displayName, state));
        }

        this.quests = temp_quests;
    }

}