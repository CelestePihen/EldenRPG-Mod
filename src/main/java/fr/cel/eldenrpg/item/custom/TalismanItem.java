package fr.cel.eldenrpg.item.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TalismanItem extends Item {

    private final MobEffect mobEffect;

    public TalismanItem(MobEffect mobEffect) {
        super(new Properties().stacksTo(1));
        this.mobEffect = mobEffect;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide()) return;
        if (!(pEntity instanceof Player player)) return;
        if (!player.getInventory().contains(pStack)) return;
        player.addEffect(new MobEffectInstance(mobEffect, 20, 0, false, false, true));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

}