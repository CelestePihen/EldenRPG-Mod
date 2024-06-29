package fr.cel.eldenrpg.quest.task.type;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.Task;
import net.minecraft.entity.mob.MobEntity;

public class KillTask extends Task {

    private final Class<? extends MobEntity> entity;
    private final int numberOfMobsToKill;

    public KillTask(String id, String langName, Class<? extends MobEntity> entity, int numberOfMobsToKill) {
        super(id, langName);
        this.entity = entity;
        this.numberOfMobsToKill = numberOfMobsToKill;
    }

    public Class<? extends MobEntity> getEntityClass() {
        return entity;
    }

    public int getNumberOfMobsToKill() {
        return numberOfMobsToKill;
    }

    public void mobKilled(MobEntity entity, Quest quest) {
        if (quest.getQuestState() != Quest.QuestState.ACTIVE) return;

        if (getEntityClass() == entity.getClass()) {
            if (getProgress() < getNumberOfMobsToKill()) {
                setProgress(getProgress() + 1);
                if (getProgress() == getNumberOfMobsToKill()) {
                    quest.setQuestState(Quest.QuestState.FINISHED);
                }
            }
        }

    }

}