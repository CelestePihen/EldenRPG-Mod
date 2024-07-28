package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.networking.packets.flasks.FlasksSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.graces.GracesSyncDataS2CPacket;
import fr.cel.eldenrpg.networking.packets.maps.MapsSyncDataS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import fr.cel.eldenrpg.util.data.GracesData;
import fr.cel.eldenrpg.util.data.MapsData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class EntityLoadEvent implements ServerEntityEvents.Load {

    public static void init() {
        ServerEntityEvents.ENTITY_LOAD.register(new EntityLoadEvent());
    }

    @Override
    public void onLoad(Entity entity, ServerWorld world) {
        if (entity instanceof ServerPlayerEntity player) {
            IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;

            if (playerDataSaver.eldenrpg$getPersistentData().getBoolean("firstTime")) {
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(10.0D);
                playerDataSaver.eldenrpg$getPersistentData().putBoolean("firstTime", false);

                AdvancementEntry rootAdvancement = player.server.getAdvancementLoader().get(Identifier.of(EldenRPG.MOD_ID, "root"));
                if (rootAdvancement != null) {
                    for (String criteria : player.getAdvancementTracker().getProgress(rootAdvancement).getUnobtainedCriteria()) {
                        player.getAdvancementTracker().grantCriterion(rootAdvancement, criteria);
                    }
                }
            }

            ServerPlayNetworking.send(player, new FlasksSyncDataS2CPacket(FlasksData.getFlasks(playerDataSaver)));

            for (Integer i : MapsData.getMapsId(playerDataSaver)) {
                ServerPlayNetworking.send(player, new MapsSyncDataS2CPacket(i));
            }

            for (long pos : GracesData.getGraces(playerDataSaver)) {
                ServerPlayNetworking.send(player, new GracesSyncDataS2CPacket(BlockPos.fromLong(pos)));
            }
        }
    }

}