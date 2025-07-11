package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.networking.packets.flasks.sync.*;
import fr.cel.eldenrpg.networking.packets.graces.AddGraceS2CPacket;
import fr.cel.eldenrpg.networking.packets.maps.MapsSyncS2CPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import fr.cel.eldenrpg.util.data.GracesData;
import fr.cel.eldenrpg.util.data.MapsData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class EntityLoadEvent implements ServerEntityEvents.Load {

    public static void init() {
        ServerEntityEvents.ENTITY_LOAD.register(new EntityLoadEvent());
    }

    @Override
    public void onLoad(Entity entity, ServerWorld world) {
        if (entity instanceof ServerPlayerEntity player) {
            IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;

            Optional<Boolean> firstTime = playerDataSaver.getPersistentData().getBoolean("firstTime");
            if (firstTime.isPresent() && firstTime.get()) {
                player.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(10.0D);
                playerDataSaver.getPersistentData().putBoolean("firstTime", false);

                AdvancementEntry rootAdvancement = player.getServer().getAdvancementLoader().get(Identifier.of(EldenRPG.MOD_ID, "root"));
                if (rootAdvancement != null) {
                    PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
                    for (String criteria : advancementTracker.getProgress(rootAdvancement).getUnobtainedCriteria()) {
                        advancementTracker.grantCriterion(rootAdvancement, criteria);
                    }
                }
            }

            ServerPlayNetworking.send(player, new FlasksSyncS2CPacket(FlasksData.getFlasks(playerDataSaver)));
            ServerPlayNetworking.send(player, new MaxFlasksSyncS2CPacket(FlasksData.getMaxFlasks(playerDataSaver)));
            ServerPlayNetworking.send(player, new GoldenSeedSyncS2CPacket(FlasksData.getGoldenSeeds(playerDataSaver)));
            ServerPlayNetworking.send(player, new LevelFlasksSyncS2CPacket(FlasksData.getLevelFlasks(playerDataSaver)));
            ServerPlayNetworking.send(player, new SacredTearSyncS2CPacket(FlasksData.getSacredTears(playerDataSaver)));

            for (Integer i : MapsData.getMapsId(playerDataSaver)) {
                ServerPlayNetworking.send(player, new MapsSyncS2CPacket(i));
            }

            for (long pos : GracesData.getPlayerGraces(playerDataSaver)) {
                ServerPlayNetworking.send(player, new AddGraceS2CPacket(BlockPos.fromLong(pos)));
            }
        }
    }

}