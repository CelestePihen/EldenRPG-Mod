package fr.cel.eldenrpg.networking.packets.animations;

import com.zigythebird.playeranimatorapi.API.PlayerAnimAPI;
import fr.cel.eldenrpg.EldenRPG;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record WaveC2SPacket() implements CustomPayload {

    private static final WaveC2SPacket INSTANCE = new WaveC2SPacket();

    public static final Id<WaveC2SPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "wave"));
    public static final PacketCodec<RegistryByteBuf, WaveC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(WaveC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        if (player.isSwimming() || player.isSpectator()) return;

        PlayerAnimAPI.playPlayerAnim(player.getServerWorld(), player, Identifier.of(EldenRPG.MOD_ID, "wave"));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}