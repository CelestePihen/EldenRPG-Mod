package fr.cel.eldenrpg.networking;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.networking.packet.bonfires.MapTeleportationC2SPacket;
import fr.cel.eldenrpg.networking.packet.backpack.BackpackSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.backpack.OpenBackpackC2SPacket;
import fr.cel.eldenrpg.networking.packet.bonfires.BonfireDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.maps.MapsDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.maps.PickMapC2SPacket;
import fr.cel.eldenrpg.networking.packet.npc.NPCDataC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(EldenRPGMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Client to Server (Serverbound)
        net.messageBuilder(MapTeleportationC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MapTeleportationC2SPacket::new)
                .encoder(MapTeleportationC2SPacket::toBytes)
                .consumerMainThread(MapTeleportationC2SPacket::handle)
                .add();

        net.messageBuilder(DrinkFlaskC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkFlaskC2SPacket::new)
                .encoder(DrinkFlaskC2SPacket::toBytes)
                .consumerMainThread(DrinkFlaskC2SPacket::handle)
                .add();

        net.messageBuilder(OpenBackpackC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenBackpackC2SPacket::new)
                .encoder(OpenBackpackC2SPacket::toBytes)
                .consumerMainThread(OpenBackpackC2SPacket::handle)
                .add();

        net.messageBuilder(NPCDataC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NPCDataC2SPacket::new)
                .encoder(NPCDataC2SPacket::toBytes)
                .consumerMainThread(NPCDataC2SPacket::handle)
                .add();

        net.messageBuilder(PickMapC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PickMapC2SPacket::new)
                .encoder(PickMapC2SPacket::toBytes)
                .consumerMainThread(PickMapC2SPacket::handle)
                .add();

        // Server to Client (Clientbound)
        net.messageBuilder(FlasksDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FlasksDataSyncS2CPacket::new)
                .encoder(FlasksDataSyncS2CPacket::toBytes)
                .consumerMainThread(FlasksDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(BackpackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BackpackSyncS2CPacket::new)
                .encoder(BackpackSyncS2CPacket::toBytes)
                .consumerMainThread(BackpackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(BonfireDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BonfireDataSyncS2CPacket::new)
                .encoder(BonfireDataSyncS2CPacket::toBytes)
                .consumerMainThread(BonfireDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(MapsDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MapsDataSyncS2CPacket::new)
                .encoder(MapsDataSyncS2CPacket::toBytes)
                .consumerMainThread(MapsDataSyncS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
