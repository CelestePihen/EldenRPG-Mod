package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.entity.npcs.BlacksmithNPC;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EldenRPGMod.MOD_ID);

    public static final RegistryObject<EntityType<EldenNPC>> ELDEN_NPC = ENTITY_TYPES.register("elden_npc", () -> EntityType.Builder.of(EldenNPC::new, MobCategory.MISC)
            .sized(0.6F, 1.8F).noSummon().fireImmune().clientTrackingRange(32).updateInterval(2).build("elden_npc"));

    public static final RegistryObject<EntityType<BlacksmithNPC>> BLACKSMITH = ENTITY_TYPES.register("blacksmith", () -> EntityType.Builder.of(BlacksmithNPC::new, MobCategory.MISC)
            .sized(0.6F, 1.8F).noSummon().fireImmune().clientTrackingRange(32).updateInterval(2).build("blacksmith"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}