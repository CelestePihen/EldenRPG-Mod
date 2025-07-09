package fr.cel.eldenrpg.entity.custom;

import fr.cel.eldenrpg.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CatacombCarcassEntity extends HostileEntity implements RangedAttackMob {

    private final Set<UUID> playersWhoHit = new HashSet<>();

    public final BowAttackGoal<CatacombCarcassEntity> bowAttackGoal = new BowAttackGoal<>(this, 1.0, 20, 15.0F);

    public CatacombCarcassEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setHealth(50.0F);
        this.setCustomNameVisible(true);
        this.setCustomName(Text.translatable("entity.eldenrpg.catacomb_carcass"));

        updateAttackType();
    }

    public static DefaultAttributeContainer.Builder createCatacombCarcassAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.MAX_HEALTH, 50.0);
    }

    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            this.goalSelector.remove(this.bowAttackGoal);
            this.bowAttackGoal.setAttackInterval(40);
            this.goalSelector.add(4, this.bowAttackGoal);
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity player) {
            playersWhoHit.add(player.getUuid());
        }
        return super.damage(world, source, amount);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (getWorld().isClient) return;
        for (UUID playerUUID : playersWhoHit) {
            PlayerEntity player = getServer().getPlayerManager().getPlayer(playerUUID);
            if (player != null) player.giveItemStack(new ItemStack(ModItems.KEY));
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);

        updateAttackType();

        this.setCanPickUpLoot(false);

        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setEquipmentDropChance(EquipmentSlot.OFFHAND, 0.0F);

        return entityData;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModItems.KEY));
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier, shotFrom);
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
        ItemStack itemStack2 = this.getProjectileType(itemStack);
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack2, pullProgress, itemStack);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * 0.2F, f, 1.6F, (float)(14 - this.getWorld().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(persistentProjectileEntity);
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        updateAttackType();
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        if (!this.getWorld().isClient) {
            updateAttackType();
        }
    }
}