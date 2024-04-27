package fr.cel.eldenrpg.item;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.item.custom.TalismanItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EldenRPGMod.MOD_ID);

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPEED_TALISMAN = ITEMS.register("speed_talisman", () -> new TalismanItem(MobEffects.MOVEMENT_SPEED));

    //  Rarity.create("name", ChatFormatting.color) -> permet de créer une nouvelle Rarité (système du jeu)

    /**
     * Permet d'enregistrer tous les items de la classe
     * @param eventBus Interface qui permet d'enregistrer les items
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}