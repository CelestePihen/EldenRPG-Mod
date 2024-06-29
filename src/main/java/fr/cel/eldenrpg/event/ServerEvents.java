package fr.cel.eldenrpg.event;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.areas.Area;
import fr.cel.eldenrpg.areas.Areas;
import fr.cel.eldenrpg.areas.type.POIArea;
import fr.cel.eldenrpg.capabilities.bonfires.BonfireList;
import fr.cel.eldenrpg.capabilities.bonfires.PlayerBonfireProvider;
import fr.cel.eldenrpg.capabilities.others.PlayerOthersProvider;
import fr.cel.eldenrpg.capabilities.flasks.PlayerFlasksProvider;
import fr.cel.eldenrpg.capabilities.map.PlayerMapsProvider;
import fr.cel.eldenrpg.capabilities.quests.PlayerQuestsProvider;
import fr.cel.eldenrpg.capabilities.slots.PlayerBackpackProvider;
import fr.cel.eldenrpg.client.data.ClientBonfiresData;
import fr.cel.eldenrpg.client.data.ClientMapsData;
import fr.cel.eldenrpg.command.NPCCommand;
import fr.cel.eldenrpg.command.QuestCommand;
import fr.cel.eldenrpg.event.custom.EnterAreaEvent;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.backpack.BackpackSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.bonfires.BonfireDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.maps.MapsDataSyncS2CPacket;
import fr.cel.eldenrpg.quest.Quest;
import fr.cel.eldenrpg.quest.task.type.ItemTask;
import fr.cel.eldenrpg.quest.task.type.KillTask;
import fr.cel.eldenrpg.quest.task.type.ZoneTask;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = EldenRPGMod.MOD_ID)
public class ServerEvents {

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER) return;
        ServerPlayer player = (ServerPlayer) event.player;

        if (player.getFoodData().needsFood()) {
            player.getFoodData().setFoodLevel(20);
        }

        if (event.phase == TickEvent.Phase.END) {
            player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> {
                for (Quest quest : playerQuests.getItemQuests()) {
                    ((ItemTask)quest.getTask()).checkItems(player, quest);
                }
            });
        }

        for (Area area : Areas.getAreas().values()) {
            area.detectPlayerInArea(player);
        }

    }

    @SubscribeEvent
    public static void onEnterArea(EnterAreaEvent event) {
        ServerPlayer player = event.getPlayer();
        Area area = event.getArea();
        Quest areaQuest = event.getQuest();

        if (!(area instanceof POIArea)) return;
        if (areaQuest == null) return;

        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> {
            for (Quest quest : playerQuests.getZoneQuest()) {
                if (quest.getId().equalsIgnoreCase(areaQuest.getId())) {
                    ((ZoneTask)quest.getTask()).interact(player, areaQuest);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Mob entity)) return;

        player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).ifPresent(playerQuests -> {
            for (Quest quest : playerQuests.getKillQuests()) {
                ((KillTask)quest.getTask()).mobKilled(player, entity, quest);
            }
        });
    }

    @SubscribeEvent
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event) {
        if (event.getEntity() instanceof EnderMan) {
            // TODO pour Timéo
            event.setDroppedExperience(0);
        }
    }

    @SubscribeEvent
    public static void onRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        EldenRPGMod.LOGGER.info("RESPAWN");

    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player player)) return;

        if (!player.getCapability(PlayerOthersProvider.PLAYER_OTHERS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "others"), new PlayerOthersProvider());
        }

        if (!player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "flasks"), new PlayerFlasksProvider());
        }

        if (!player.getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "backpack"), new PlayerBackpackProvider());
        }

        if (!player.getCapability(PlayerBonfireProvider.PLAYER_BONFIRE).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "campfires"), new PlayerBonfireProvider());
        }

        if (!player.getCapability(PlayerMapsProvider.PLAYER_MAPS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "maps"), new PlayerMapsProvider());
        }

        if (!player.getCapability(PlayerQuestsProvider.PLAYER_QUESTS).isPresent()) {
            event.addCapability(new ResourceLocation(EldenRPGMod.MOD_ID, "quests"), new PlayerQuestsProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(PlayerOthersProvider.PLAYER_OTHERS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerOthersProvider.PLAYER_OTHERS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                    event.getEntity().getAttribute(Attributes.MAX_HEALTH).setBaseValue(newStore.getMaxHealth());
                });
            });

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

            event.getOriginal().getCapability(PlayerBonfireProvider.PLAYER_BONFIRE).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerBonfireProvider.PLAYER_BONFIRE).ifPresent(newStore -> {
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
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks ->
                ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), player)
        );

        player.getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).ifPresent(playerSlots ->
            ModMessages.sendToPlayer(new BackpackSyncS2CPacket(playerSlots.getStacks().serializeNBT()), player)
        );

        player.getCapability(PlayerBonfireProvider.PLAYER_BONFIRE).ifPresent(playerCampfire -> {
            ClientBonfiresData.getBonfires().clear();
            for (BlockPos blockPos : playerCampfire.getBonfires()) {
                ModMessages.sendToPlayer(new BonfireDataSyncS2CPacket(blockPos, BonfireList.getBonfireName(blockPos)), player);
            }
        });

        player.getCapability(PlayerMapsProvider.PLAYER_MAPS).ifPresent(playerMaps -> {
            ClientMapsData.getPlayerMaps().clear();
            for (Integer i : playerMaps.getMapsId()) {
                ModMessages.sendToPlayer(new MapsDataSyncS2CPacket(i), player);
            }
        });

        player.getCapability(PlayerOthersProvider.PLAYER_OTHERS).ifPresent(playerFirstTime -> {
            if (playerFirstTime.isFirstTime()) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D);
                playerFirstTime.setFirstTime(false);

                Advancement rootAdvancement = ServerLifecycleHooks.getCurrentServer().getAdvancements().getAdvancement(new ResourceLocation("eldenrpg", "root"));
                if (rootAdvancement != null) {
                    AdvancementProgress progress = player.getAdvancements().getOrStartProgress(rootAdvancement);
                    for (String criteria : progress.getRemainingCriteria()) {
                        player.getAdvancements().award(rootAdvancement, criteria);
                    }
                }

            }
        });
    }

    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        NPCCommand.register(event.getDispatcher());
        QuestCommand.register(event.getDispatcher());
    }

}