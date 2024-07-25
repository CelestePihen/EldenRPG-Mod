package fr.cel.eldenrpg.networking.packets.graces;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.GracesData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record GracesSyncDataS2CPacket(BlockPos pos) implements CustomPayload {

    public static final Id<GracesSyncDataS2CPacket> ID = new Id<>(ModMessages.GRACES_SYNC_ID);
    public static final PacketCodec<RegistryByteBuf, GracesSyncDataS2CPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, GracesSyncDataS2CPacket::pos, GracesSyncDataS2CPacket::new);

    public static void handle(GracesSyncDataS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> GracesData.addGrace((IPlayerDataSaver) context.player(), payload.pos()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}