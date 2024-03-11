package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.capabilities.firecamp.CampfireList;
import fr.cel.eldenrpg.capabilities.firecamp.PlayerCampfireProvider;
import fr.cel.eldenrpg.capabilities.flasks.PlayerFlasksProvider;
import fr.cel.eldenrpg.capabilities.map.PlayerMapsProvider;
import fr.cel.eldenrpg.capabilities.quests.PlayerQuestsProvider;
import fr.cel.eldenrpg.capabilities.slots.PlayerBackpackProvider;
import fr.cel.eldenrpg.client.data.ClientFirecampsData;
import fr.cel.eldenrpg.client.data.ClientMapsData;
import fr.cel.eldenrpg.command.NPCCommand;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.firecamp.FirecampsDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.backpack.BackpackSyncS2CPacket;
import fr.cel.eldenrpg.areas.Areas;
import fr.cel.eldenrpg.networking.packet.maps.MapsDataSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER && player != null) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            for (Areas area : Areas.values()) {
                area.getArea().detectPlayerInArea(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void onDeathEvent(PlayerEvent.PlayerRespawnEvent event) {

    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player player)) return;

        if (!player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "flasks"), new PlayerFlasksProvider());
        }

        if (!player.getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "backpack"), new PlayerBackpackProvider());
        }

        if (!player.getCapability(PlayerCampfireProvider.PLAYER_CAMPFIRE).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "campfires"), new PlayerCampfireProvider());
        }

        if (!player.getCapability(PlayerMapsProvider.PLAYER_MAPS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "maps"), new PlayerMapsProvider());
        }

        if (!player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "quests"), new PlayerQuestsProvider(player));
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(PlayerCampfireProvider.PLAYER_CAMPFIRE).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerCampfireProvider.PLAYER_CAMPFIRE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(PlayerMapsProvider.PLAYER_MAPS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerMapsProvider.PLAYER_MAPS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks ->
                    ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), player)
            );

            player.getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).ifPresent(playerSlots ->
                    ModMessages.sendToPlayer(new BackpackSyncS2CPacket(playerSlots.getStacks().serializeNBT()), player)
            );

            player.getCapability(PlayerCampfireProvider.PLAYER_CAMPFIRE).ifPresent(playerCampfire -> {
                ClientFirecampsData.getFirecamps().clear();
                for (BlockPos blockPos : playerCampfire.getCampfires()) {
                    ModMessages.sendToPlayer(new FirecampsDataSyncS2CPacket(blockPos, CampfireList.getCampfireName(blockPos)), player);
                }
            });

            player.getCapability(PlayerMapsProvider.PLAYER_MAPS).ifPresent(playerMaps -> {
                ClientMapsData.getPlayerMaps().clear();
                for (Integer i : playerMaps.getMapsId()) {
                    ModMessages.sendToPlayer(new MapsDataSyncS2CPacket(i), player);
                }
            });

        }
    }

    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        NPCCommand.register(event.getDispatcher());
    }

}
