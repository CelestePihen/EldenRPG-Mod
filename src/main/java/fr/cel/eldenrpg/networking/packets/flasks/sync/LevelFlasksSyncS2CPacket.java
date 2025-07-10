package fr.cel.eldenrpg.networking.packets.flasks.sync;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record LevelFlasksSyncS2CPacket(int level) implements CustomPayload {

    public static final Id<LevelFlasksSyncS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "synclevelflasks"));
    public static final PacketCodec<RegistryByteBuf, LevelFlasksSyncS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, LevelFlasksSyncS2CPacket::level, LevelFlasksSyncS2CPacket::new);

    public static void handle(LevelFlasksSyncS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).getPersistentData().putInt("levelFlasks", payload.level()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}