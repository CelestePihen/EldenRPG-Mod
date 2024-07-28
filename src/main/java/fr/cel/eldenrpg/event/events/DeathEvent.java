package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.QuestsData;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;

public class DeathEvent implements ServerLivingEntityEvents.AfterDeath {

    public static void init() {
        ServerLivingEntityEvents.AFTER_DEATH.register(new DeathEvent());
    }

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity instanceof PlayerEntity player) {
            player.playSoundToPlayer(ModSounds.DEATH, SoundCategory.BLOCKS, 0.5F, 1.0F);
            return;
        }

        if (!(damageSource.getSource() instanceof PlayerEntity player)) return;
        if (!(entity instanceof MobEntity entityKilled)) return;

        for (Quest quest : QuestsData.getKillQuests((IPlayerDataSaver) player)) {
            ((KillTask) quest.getTask()).mobKilled(entityKilled, quest);
        }
    }

}