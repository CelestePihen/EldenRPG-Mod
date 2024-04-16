package fr.cel.eldenrpg.capabilities.quests;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class PlayerQuests {

    private List<Quest> quests = new ArrayList<>();

    public Quest getQuest(String id) {
        return quests.stream().filter(quest -> quest.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean hasQuest(Quest quest) {
        return quests.contains(quest);
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void removeQuest(String id) {
        quests.stream().filter(quest -> quest.getId().equals(id)).findFirst().ifPresent(quest -> quests.remove(quest));
    }

    public void setQuestState(String id, QuestState newState) {
        quests.stream().filter(quest -> quest.getId().equals(id)).findFirst().ifPresent(quest -> quest.setQuestState(newState));
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void copyFrom(PlayerQuests source) {
        this.quests = source.quests;
    }

    public void saveNBTData(CompoundTag nbt) {
        ListTag questsList = new ListTag();

        for (Quest quest : quests) {
            CompoundTag tag = new CompoundTag();

            tag.putString("id", quest.getId());
            tag.putString("displayName", quest.getLangName());
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
            String langName = questsTags.getString("displayName");
            QuestState state = QuestState.valueOf(questsTags.getString("state"));

            temp_quests.add(new Quest(id, langName, state));
        }

        this.quests = temp_quests;
    }

}