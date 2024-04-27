package fr.cel.eldenrpg.capabilities.quests;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.Quest.QuestState;
import fr.cel.eldenrpg.quest.Quests;
import fr.cel.eldenrpg.quest.task.Task;
import fr.cel.eldenrpg.quest.task.Tasks;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class PlayerQuests {

    private List<Quest> quests = new ArrayList<>();

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void removeQuest(String id) {
        quests.stream().filter(quest -> quest.getId().equals(id)).findFirst().ifPresent(quest -> quests.remove(quest));
    }

    // À utiliser pour la commande
    public void setQuestState(String id, QuestState newState) {
        quests.stream().filter(quest -> quest.getId().equals(id)).findFirst().ifPresent(quest -> quest.setQuestState(newState));
    }

    // TODO fonction pour changer la progression (progress) du joueur
    public void setQuestProgress(String id, int progress) {
        quests.stream().filter(quest -> quest.getId().equals(id)).findFirst().ifPresent(quest -> quest.getTask().setProgress(progress));
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public Quest getQuest(String id) {
        for (Quest quest : quests) {
            if (quest.getId().equalsIgnoreCase(id)) {
                return quest;
            }
        }
        return null;
    }

    public List<Quest> getKillQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof KillTask) {
                quests.add(quest);
            }
        }
        return quests;
    }

    public List<Quest> getItemQuests() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof ItemTask) {
                quests.add(quest);
            }
        }
        return quests;
    }

    public List<Quest> getZoneQuest() {
        List<Quest> quests = new ArrayList<>();
        for (Quest quest : this.quests) {
            if (quest.getTask() instanceof ZoneTask) {
                quests.add(quest);
            }
        }
        return quests;
    }

    public void copyFrom(PlayerQuests source) {
        this.quests = source.quests;
    }

    public void saveNBTData(CompoundTag nbt) {
        ListTag questsList = new ListTag();

        for (Quest quest : quests) {
            CompoundTag questTag = new CompoundTag();

            questTag.putString("id", quest.getId());
            questTag.putString("questState", quest.getQuestState().toString());
            questTag.put("task", quest.getTask().saveNBTData());

            questsList.add(questTag);
        }

        nbt.put("quests", questsList);
    }

    public void loadNBTData(CompoundTag nbt) {
        List<Quest> temp_quests = new ArrayList<>();
        ListTag questsList = nbt.getList("quests", Tag.TAG_COMPOUND);

        for (int i = 0; i < questsList.size(); i++) {
            CompoundTag questsTags = questsList.getCompound(i);

            String id = questsTags.getString("id");
            QuestState state = QuestState.valueOf(questsTags.getString("questState"));
            Task task = Tasks.loadNBTDataTask(questsTags);

            Quest quest = Quests.getQuest(id);
            if (quest != null) {
                quest.setQuestState(state);
                quest.setTask(task);
                temp_quests.add(quest);
            }
        }

        this.quests = temp_quests;
    }

}