package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.entity.EldenNPC;
import fr.cel.eldenrpg.entity.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.NPC.get(), EldenNPC.createAttributes().build());
    }

}
