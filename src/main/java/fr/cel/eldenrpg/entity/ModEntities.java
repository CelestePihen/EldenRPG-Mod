package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.entity.custom.AbstractNPCEntity;
import fr.cel.eldenrpg.entity.custom.CatacombCarcassEntity;
import fr.cel.eldenrpg.entity.npcs.BlacksmithEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class ModEntities {

    /** Registry Keys */
    private static final RegistryKey<EntityType<?>> BLACKSMITH_NPC_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(EldenRPG.MOD_ID, "blacksmith_npc"));

    private static final RegistryKey<EntityType<?>> CATACOMB_CARCASS_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(EldenRPG.MOD_ID, "catacomb_carcass"));


    /** Entity Types  */
    public static final EntityType<BlacksmithEntity> BLACKSMITH_NPC = registerEntity("blacksmith_npc",
            Builder.create(BlacksmithEntity::new, SpawnGroup.AMBIENT).dimensions(0.6F, 1.8F).disableSummon().makeFireImmune()
                    .maxTrackingRange(32).trackingTickInterval(2), BLACKSMITH_NPC_KEY);

    public static final EntityType<CatacombCarcassEntity> CATACOMB_CARCASS = registerEntity("catacomb_carcass",
            Builder.create(CatacombCarcassEntity::new, SpawnGroup.CREATURE).dimensions(0.6F, 1.99F)
                    .eyeHeight(1.74F).maxTrackingRange(8), CATACOMB_CARCASS_KEY);

    private static <T extends Entity> EntityType<T> registerEntity(String name, Builder<T> type, RegistryKey<EntityType<?>> registryKey) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(EldenRPG.MOD_ID, name), type.build(registryKey));
    }

    public static void registerModEntities() {
        EldenRPG.LOGGER.info("Register Entities for " + EldenRPG.MOD_ID);
        registerAttributes();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.BLACKSMITH_NPC, AbstractNPCEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CATACOMB_CARCASS, CatacombCarcassEntity.createCatacombCarcassAttributes());
        EldenRPG.LOGGER.info("Register Entity Attributes for " + EldenRPG.MOD_ID);
    }

}