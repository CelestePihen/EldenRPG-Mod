package fr.cel.eldenrpg.entity.custom;

import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EldenNPC extends MobEntity {

    protected Quest quest;

    public EldenNPC(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.setCustomNameVisible(true);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 32.0F));
        this.goalSelector.add(2, new LookAroundGoal(this));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

        if (!getWorld().isClient()) {
            playerInteract((ServerPlayerEntity) player, hand);
            return ActionResult.PASS;
        }

        return super.interactMob(player, hand);
    }

    protected void playerInteract(ServerPlayerEntity player, Hand hand) {}

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return this.isRemoved() || this.isInvulnerable() && !damageSource.isOf(DamageTypes.GENERIC_KILL);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

}