package fr.cel.eldenrpg.networking.packets.bonfires;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.GracesData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public record GracesSyncDataS2CPacket(BlockPos pos) implements CustomPayload {

    public static final Id<GracesSyncDataS2CPacket> ID = new Id<>(ModMessages.GRACES_SYNC_ID);
    public static final PacketCodec<RegistryByteBuf, GracesSyncDataS2CPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, GracesSyncDataS2CPacket::pos, GracesSyncDataS2CPacket::new);

    public static void handle(GracesSyncDataS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            IPlayerDataSaver player = (IPlayerDataSaver) context.player();

            List<Long> blockPosLong = GracesData.getGraces(player);
            if (blockPosLong.contains(payload.pos().asLong())) return;
            blockPosLong.add(payload.pos().asLong());

            player.eldenrpg$getPersistentData().putLongArray("graces", blockPosLong);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}