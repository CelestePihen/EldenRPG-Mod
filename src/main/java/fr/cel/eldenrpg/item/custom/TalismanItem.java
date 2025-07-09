package fr.cel.eldenrpg.item.custom;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class TalismanItem extends Item {

    private final RegistryEntry<StatusEffect> effect;

    public TalismanItem(String name, RegistryEntry<StatusEffect> effect) {
        super(new Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EldenRPG.MOD_ID, name)))
                .maxCount(1).rarity(Rarity.EPIC));
        this.effect = effect;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (world.isClient()) return;
        if (!(entity instanceof PlayerEntity player)) return;
        if (!player.getInventory().contains(stack)) return;
        player.addStatusEffect(new StatusEffectInstance(effect, 20, 0, false, false, true));
    }

}