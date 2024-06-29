package fr.cel.eldenrpg.util.data;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.util.IPlayerDataSaver;

import java.util.List;

public class QuestsData {

    public static List<Quest> getQuests(IPlayerDataSaver player) {
        return player.eldenrpg$getQuests();
    }

    public static Quest getQuest(IPlayerDataSaver player, String id) {
        for (Quest quest : player.eldenrpg$getQuests()) {
            if (quest.getId().equalsIgnoreCase(id)) {
                return quest;
            }
        }
        return null;
    }

}
