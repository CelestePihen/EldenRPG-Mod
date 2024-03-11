package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.client.utils.ClientHooks;
import fr.cel.eldenrpg.entity.goal.EldenNPCMoveGoal;
import fr.cel.eldenrpg.entity.goal.TestGoal;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EldenNPC extends AgeableMob {

    public boolean canMove;
    protected Quest quest;

    private final BlockPos[] path = new BlockPos[] {
            new BlockPos(204, 65, -63),
            new BlockPos(193, 67, -58),
            new BlockPos(184, 68, -50),
            new BlockPos(203, 68, -40),
            new BlockPos(219, 70, -40),
            new BlockPos(229, 70, -46),
            new BlockPos(226, 68, -56),
            new BlockPos(213, 65, -67),
    };

    // TODO c'est de la merde
    private final EldenNPCMoveGoal eldenNPCMoveGoal = new EldenNPCMoveGoal(this, path, 0.3D);

    private final TestGoal testGoal = new TestGoal(this, 0.5D, Arrays.stream(path).toList());

    public EldenNPC(EntityType<? extends AgeableMob> entityType, Level world) {
        super(entityType, world);

        this.setInvulnerable(true);
        this.setCustomNameVisible(true);
        this.setCustomName(this.getName());
        this.setPersistenceRequired();
        this.setCanMove(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 48.0D).add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
    protected void registerGoals() {
        if (canMove) {
            enableMovement();
            EldenRPGMod.LOGGER.error("canMove");
        } else {
            EldenRPGMod.LOGGER.error("can Pas move");
        }
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 48.0f));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("CanMove", canMove);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        canMove = pCompound.getBoolean("CanMove");
    }

    public void enableMovement() {
        this.goalSelector.addGoal(0, testGoal);
    }

    public void disableMovement() {
        this.goalSelector.removeGoal(testGoal);
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;

        if (this.canMove) {
            enableMovement();
        } else {
            disableMovement();
        }
    }

    protected InteractionResult playerInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        if (!level().isClientSide()) return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown() && player.canUseGameMasterBlocks()) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openNPCScreen(this));
            return InteractionResult.SUCCESS;
        }

        playerInteract(player, hand);

        return super.mobInteract(player, hand);
    }

    @Override
    protected void doPush(Entity entity) {

    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return this.isRemoved() || this.isInvulnerable() && !damageSource.is(DamageTypes.GENERIC_KILL);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

}