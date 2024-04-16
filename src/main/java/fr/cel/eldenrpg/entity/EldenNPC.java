package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.client.utils.ClientHooks;
import fr.cel.eldenrpg.quest.Quest;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

public class EldenNPC extends AgeableMob {

    protected Quest quest;

    public EldenNPC(EntityType<? extends AgeableMob> entityType, Level world) {
        super(entityType, world);

        this.setInvulnerable(true);
        this.setCustomNameVisible(true);
        this.setCustomName(this.getName());
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 48.0D);
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