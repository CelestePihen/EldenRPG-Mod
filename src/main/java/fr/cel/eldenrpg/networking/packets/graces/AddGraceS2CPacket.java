package fr.cel.eldenrpg.networking.packets.graces;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.GracesData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record AddGraceS2CPacket(BlockPos pos) implements CustomPayload {

    public static final Id<AddGraceS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "addgrace"));

    public static final PacketCodec<RegistryByteBuf, AddGraceS2CPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, AddGraceS2CPacket::pos, AddGraceS2CPacket::new);

    public static void handle(AddGraceS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> GracesData.addGrace((IPlayerDataSaver) context.player(), payload.pos()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}