package fr.cel.eldenrpg.item;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.item.custom.KeyItem;
import fr.cel.eldenrpg.item.custom.TalismanItem;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {

    public static final Item SPEED_TALISMAN = registerItem("speed_talisman", new TalismanItem(StatusEffects.SPEED));

    public static final Item KEY = registerItem("key", new KeyItem());

    /**
     * Permet d'enregistrer un nouvel Item
     * @param name Le nom de l'Item
     * @param item La classe de l'Item
     * @return Retourne l'Item qui vient d'être enregistré
     */
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(EldenRPG.MOD_ID, name), item);
    }

    /**
     * Permet d'enregistrer tous les items de la classe
     */
    public static void registerModItems() {
        EldenRPG.LOGGER.info("Register Items for " + EldenRPG.MOD_ID);
    }

}