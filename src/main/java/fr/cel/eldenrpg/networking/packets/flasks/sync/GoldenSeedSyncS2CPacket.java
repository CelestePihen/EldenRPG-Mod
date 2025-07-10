package fr.cel.eldenrpg.networking.packets.flasks.sync;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GoldenSeedSyncS2CPacket(int goldenSeed) implements CustomPayload {

    public static final Id<GoldenSeedSyncS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "syncgoldenseed"));
    public static final PacketCodec<RegistryByteBuf, GoldenSeedSyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, GoldenSeedSyncS2CPacket::goldenSeed, GoldenSeedSyncS2CPacket::new);

    public static void handle(GoldenSeedSyncS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).getPersistentData().putInt("goldenSeed", payload.goldenSeed()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}