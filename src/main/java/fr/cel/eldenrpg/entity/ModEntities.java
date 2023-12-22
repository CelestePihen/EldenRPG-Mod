package fr.cel.eldenrpg.entity;

import fr.cel.eldenrpg.EldenRPGMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EldenRPGMod.MOD_ID);

    public static final RegistryObject<EntityType<EldenNPC>> NPC = ENTITY_TYPES.register("npc", () -> EntityType.Builder.of(EldenNPC::new, MobCategory.MISC)
            .sized(0.6F, 1.8F).fireImmune().clientTrackingRange(32).updateInterval(2).build("npc"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
