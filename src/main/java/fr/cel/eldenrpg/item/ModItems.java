package fr.cel.eldenrpg.item;

import fr.cel.eldenrpg.EldenRPGMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EldenRPGMod.MOD_ID);

    /**
     * Permet d'enregistrer tous les items de la classe
     * @param eventBus Interface qui permet d'enregistrer les items
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
