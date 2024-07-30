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
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<BlacksmithEntity> BLACKSMITH_NPC = registerEntity("blacksmith_npc",
            Builder.create(BlacksmithEntity::new, SpawnGroup.CREATURE).dimensions(0.6F, 1.8F)
                    .disableSummon().makeFireImmune().maxTrackingRange(32).trackingTickInterval(2));

    public static final EntityType<CatacombCarcassEntity> CATACOMB_CARCASS = registerEntity("catacomb_carcass",
            Builder.create(CatacombCarcassEntity::new, SpawnGroup.CREATURE).dimensions(0.6F, 1.99F).eyeHeight(1.74F).maxTrackingRange(8));

    private static <T extends Entity> EntityType<T> registerEntity(String name, Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(EldenRPG.MOD_ID, name), type.build());
    }

    public static void registerModEntities() {
        EldenRPG.LOGGER.info("Enregistrement des Entites pour " + EldenRPG.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.BLACKSMITH_NPC, AbstractNPCEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CATACOMB_CARCASS, CatacombCarcassEntity.createCatacombCarcassAttributes());
    }

}