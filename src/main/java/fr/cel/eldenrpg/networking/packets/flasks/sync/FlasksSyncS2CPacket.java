package fr.cel.eldenrpg.networking.packets.flasks.sync;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FlasksSyncS2CPacket(int flasks) implements CustomPayload {

    public static final CustomPayload.Id<FlasksSyncS2CPacket> ID = new CustomPayload.Id<>(Identifier.of(EldenRPG.MOD_ID, "syncflasks"));
    public static final PacketCodec<RegistryByteBuf, FlasksSyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FlasksSyncS2CPacket::flasks, FlasksSyncS2CPacket::new);

    public static void handle(FlasksSyncS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).eldenrpg$getPersistentData().putInt("flasks", payload.flasks()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}