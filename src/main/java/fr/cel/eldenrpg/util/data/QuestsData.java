package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.util.IPlayerDataSaver;

import java.util.List;

public class QuestsData {

    public static void addQuest(IPlayerDataSaver player, Quest quest) {
        if (getQuest(player, quest.getId()) != null) return;
        player.eldenrpg$getQuests().add(quest);
    }

    public static void removeQuest(IPlayerDataSaver player, String questId) {
        if (getQuest(player, questId) == null) return;

        for (Quest quest : getQuests(player)) {
            if (quest.getId().equalsIgnoreCase(questId)) {
                player.eldenrpg$getQuests().remove(quest);
            }
        }
    }

    public static List<Quest> getQuests(IPlayerDataSaver player) {
        return player.eldenrpg$getQuests();
    }

    public static List<Quest> getKillQuests(IPlayerDataSaver player) {
        return player.eldenrpg$getKillQuests();
    }

    public static List<Quest> getItemQuests(IPlayerDataSaver player) {
        return player.eldenrpg$getItemQuests();
    }

    public static List<Quest> getZoneQuests(IPlayerDataSaver player) {
        return player.eldenrpg$getZoneQuests();
    }

    public static Quest getQuest(IPlayerDataSaver player, String id) {
        for (Quest quest : player.eldenrpg$getQuests()) {
            if (quest.getId().equalsIgnoreCase(id)) return quest;
        }
        return null;
    }

}