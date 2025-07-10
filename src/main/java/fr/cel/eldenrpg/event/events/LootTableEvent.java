package fr.cel.eldenrpg.event.events;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class LootTableEvent implements LootTableEvents.Replace {

    private static final Identifier SLIME_BALL_ID = Identifier.of("minecraft", "entities/slime");

    public static void init() {
        LootTableEvents.REPLACE.register(new LootTableEvent());
    }

    // FIXME solution temporaire pour que les slimes de la quête du Forgeron ne droppent pas de slime balls

    @Override
    public @Nullable LootTable replaceLootTable(RegistryKey<LootTable> key, LootTable original, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (SLIME_BALL_ID.equals(key.getValue()) && source == LootTableSource.VANILLA) {
            return LootTable.EMPTY;
        }

        return null;
    }
}