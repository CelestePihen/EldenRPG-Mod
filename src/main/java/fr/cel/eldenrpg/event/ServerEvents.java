package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.capabilities.flasks.PlayerFlasksProvider;
import fr.cel.eldenrpg.capabilities.slots.PlayerSlotsProvider;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.slots.SlotsSyncS2CPacket;
import fr.cel.eldenrpg.util.Area.Areas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID)
public class ServerEvents {

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
    public static void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player player)) return;

        if (!player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "properties"), new PlayerFlasksProvider());
        }

        if (!player.getCapability(PlayerSlotsProvider.PLAYER_SLOTS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "additionnalslots"), new PlayerSlotsProvider());
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
            event.getOriginal().getCapability(PlayerSlotsProvider.PLAYER_SLOTS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerSlotsProvider.PLAYER_SLOTS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks -> {
                ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), player);
            });
            player.getCapability(PlayerSlotsProvider.PLAYER_SLOTS).ifPresent(playerSlots -> {
                ModMessages.sendToPlayer(new SlotsSyncS2CPacket(playerSlots.getStacks().serializeNBT()), player);
            });
        }
    }

}
