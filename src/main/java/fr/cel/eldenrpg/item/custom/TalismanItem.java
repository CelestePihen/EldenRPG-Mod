package fr.cel.eldenrpg.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

public class TalismanItem extends Item {

    private final RegistryEntry<StatusEffect> effect;

    public TalismanItem(RegistryEntry<StatusEffect> effect) {
        super(new Settings().maxCount(1).rarity(Rarity.EPIC));
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) return;
        if (!(entity instanceof PlayerEntity player)) return;
        if (!player.getInventory().contains(stack)) return;
        player.addStatusEffect(new StatusEffectInstance(effect, 20, 0, false, false, true));
    }

}