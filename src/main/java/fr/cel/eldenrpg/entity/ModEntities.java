package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import fr.cel.eldenrpg.entity.custom.npcs.BlacksmithNPC;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<EldenNPC> ELDEN_NPC = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(EldenRPG.MOD_ID, "elden_npc"), EntityType.Builder.create(EldenNPC::new, SpawnGroup.CREATURE)
                    .dimensions(0.6F, 1.8F).disableSummon().makeFireImmune().maxTrackingRange(32).trackingTickInterval(2).build());

    public static final EntityType<BlacksmithNPC> BLACKSMITH_NPC = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(EldenRPG.MOD_ID, "blacksmith_npc"), EntityType.Builder.create(BlacksmithNPC::new, SpawnGroup.CREATURE)
                    .dimensions(0.6F, 1.8F).disableSummon().makeFireImmune().maxTrackingRange(32).trackingTickInterval(2).build());

    public static void registerModEntities() {
        EldenRPG.LOGGER.info("Enregistrement des Entites pour " + EldenRPG.MOD_ID);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.ELDEN_NPC, EldenNPC.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BLACKSMITH_NPC, EldenNPC.createAttributes());
    }

}