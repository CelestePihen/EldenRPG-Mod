package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.client.utils.ClientHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class EldenNPC extends Mob {

    public EldenNPC(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.setCustomNameVisible(true);
        this.setCustomName(this.getName());
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 48.0D).add(Attributes.MOVEMENT_SPEED, 0.0D);
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!level().isClientSide()) return InteractionResult.SUCCESS;

        if (pPlayer.isShiftKeyDown() && pPlayer.canUseGameMasterBlocks()) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openNPCScreen(this));
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(pPlayer, hand);
    }

    @Override
    public void push(Entity entity) {

    }

    @Override
    protected void doPush(Entity entity) {

    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return this.isRemoved() || this.isInvulnerable() && !damageSource.is(DamageTypes.GENERIC_KILL);
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public void setLeashedTo(Entity pLeashHolder, boolean pBroadcastPacket) {

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 48.0f));
    }

}