package fr.cel.eldenrpg.networking.packets.flasks.sync;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MaxFlasksSyncS2CPacket(int maxFlasks) implements CustomPayload {

    public static final Id<MaxFlasksSyncS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "syncmaxflasks"));
    public static final PacketCodec<RegistryByteBuf, MaxFlasksSyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, MaxFlasksSyncS2CPacket::maxFlasks, MaxFlasksSyncS2CPacket::new);

    public static void handle(MaxFlasksSyncS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).getPersistentData().putInt("maxFlasks", payload.maxFlasks()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}