package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class KillTask extends Task {

    private final Class<? extends Mob> entity;
    private final int numberOfMobsToKill;

    public KillTask(String id, String langName, Class<? extends Mob> entity, int numberOfMobsToKill) {
        super(id, langName);
        this.entity = entity;
        this.numberOfMobsToKill = numberOfMobsToKill;
    }

    public Class<? extends Mob> getEntityClass() {
        return entity;
    }

    public int getNumberOfMobsToKill() {
        return numberOfMobsToKill;
    }

    public void mobKilled(Player player, Mob entity, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;
        if (getEntityClass() == entity.getClass()) {
            if (getProgress() < getNumberOfMobsToKill()) {
                setProgress(getProgress() + 1);
                if (getProgress() == getNumberOfMobsToKill()) {
                    quest.setQuestState(Quest.QuestState.FINISHED);
                    player.sendSystemMessage(Component.literal("Tu as fini la quête ").append(quest.getTranslatedName()).append(" !"));
                } else {
                    Component component = Component.literal("Tu as tué " + getProgress() + " ")
                            .append(entity.getCustomName() != null ? entity.getCustomName() : entity.getName())
                            .append(". Plus que " + (getNumberOfMobsToKill() - getProgress()));
                    player.sendSystemMessage(component);
                }
            }

        }
    }

}