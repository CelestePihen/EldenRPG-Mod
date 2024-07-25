package fr.cel.eldenrpg.item;

import fr.cel.eldenrpg.EldenRPG;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModItemGroups {

    public static final ItemGroup ELDEN_RPG_GROUP = register("eldenrpg",
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.eldenrpg"))
                    .icon(() -> new ItemStack(ModItems.SPEED_TALISMAN)).entries((displayContext, entries) -> {
                        Registries.ITEM.getIds()
                                .stream().filter(identifier -> identifier.getNamespace().equals(EldenRPG.MOD_ID))
                                .map(Registries.ITEM::getOrEmpty).map(Optional::orElseThrow).forEach(entries::add);
                    }).build());

    private static ItemGroup register(String name, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, Identifier.of(EldenRPG.MOD_ID, name), itemGroup);
    }

    public static void registerItemGroups() {
        EldenRPG.LOGGER.info("Enregistrement de l'ItemGroup pour " + EldenRPG.MOD_ID);
    }

}
