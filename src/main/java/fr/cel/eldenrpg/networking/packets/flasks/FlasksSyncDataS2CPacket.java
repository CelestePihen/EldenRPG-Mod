package fr.cel.eldenrpg.networking.packets.flasks;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record FlasksSyncDataS2CPacket(int flasks) implements CustomPayload {

    public static final CustomPayload.Id<FlasksSyncDataS2CPacket> ID = new CustomPayload.Id<>(ModMessages.SYNC_FLASK_ID);
    public static final PacketCodec<RegistryByteBuf, FlasksSyncDataS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FlasksSyncDataS2CPacket::flasks, FlasksSyncDataS2CPacket::new);

    public static void handle(FlasksSyncDataS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).eldenrpg$getPersistentData().putInt("flasks", payload.flasks()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}