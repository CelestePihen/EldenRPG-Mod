package fr.cel.eldenrpg.networking.packets.flasks.sync;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SacredTearSyncS2CPacket(int tearOfLife) implements CustomPayload {

    public static final Id<SacredTearSyncS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "syncsacredtear"));
    public static final PacketCodec<RegistryByteBuf, SacredTearSyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SacredTearSyncS2CPacket::tearOfLife, SacredTearSyncS2CPacket::new);

    public static void handle(SacredTearSyncS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).getPersistentData().putInt("sacredTear", payload.tearOfLife()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}