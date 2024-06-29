package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ModAfterDeath implements ServerLivingEntityEvents.AfterDeath {

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity instanceof PlayerEntity) return;
        if (!(damageSource.getSource() instanceof PlayerEntity player)) return;
        if (!(entity instanceof MobEntity entityKilled)) return;

        for (Quest quest : ((IPlayerDataSaver) player).eldenrpg$getKillQuests()) {
            ((KillTask)quest.getTask()).mobKilled(entityKilled, quest);
        }
    }

}