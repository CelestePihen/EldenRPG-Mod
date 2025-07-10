package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.util.IPlayerDataSaver;

import java.util.List;

public final class QuestsData {

    public static void addQuest(IPlayerDataSaver player, Quest quest) {
        if (getQuest(player, quest.getId()) != null) return;
        player.getQuests().add(quest);
    }

    public static List<Quest> getQuests(IPlayerDataSaver player) {
        return player.getQuests();
    }

    public static List<Quest> getKillQuests(IPlayerDataSaver player) {
        return player.getKillQuests();
    }

    public static List<Quest> getItemQuests(IPlayerDataSaver player) {
        return player.getItemQuests();
    }

    public static List<Quest> getZoneQuests(IPlayerDataSaver player) {
        return player.getZoneQuests();
    }

    public static Quest getQuest(IPlayerDataSaver player, String id) {
        for (Quest quest : player.getQuests()) {
            if (quest.getId().equalsIgnoreCase(id)) return quest;
        }
        return null;
    }

}