package fr.cel.eldenrpg.networking;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.networking.packet.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.MapTeleportationC2SPacket;
import fr.cel.eldenrpg.networking.packet.SetSpawnC2SPacket;
import fr.cel.eldenrpg.networking.packet.slots.OpenSlotsC2SPacket;
import fr.cel.eldenrpg.networking.packet.slots.SlotsSyncS2CPacket;
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

        net.messageBuilder(SetSpawnC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetSpawnC2SPacket::new)
                .encoder(SetSpawnC2SPacket::toBytes)
                .consumerMainThread(SetSpawnC2SPacket::handle)
                .add();

        net.messageBuilder(FlasksDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FlasksDataSyncS2CPacket::new)
                .encoder(FlasksDataSyncS2CPacket::toBytes)
                .consumerMainThread(FlasksDataSyncS2CPacket::handle)
                .add();

        net.messageBuilder(OpenSlotsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenSlotsC2SPacket::new)
                .encoder(OpenSlotsC2SPacket::toBytes)
                .consumerMainThread(OpenSlotsC2SPacket::handle)
                .add();

        net.messageBuilder(SlotsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SlotsSyncS2CPacket::new)
                .encoder(SlotsSyncS2CPacket::toBytes)
                .consumerMainThread(SlotsSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
