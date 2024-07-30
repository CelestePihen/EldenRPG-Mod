package fr.cel.eldenrpg.networking.packets.graces.screen;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.client.util.ClientHooks;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;

public record OpenGraceScreenS2CPacket(Text graceName) implements CustomPayload {

    public static final Id<OpenGraceScreenS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "openscreengrace"));
    public static final PacketCodec<RegistryByteBuf, OpenGraceScreenS2CPacket> CODEC = PacketCodec.tuple(
            TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC, OpenGraceScreenS2CPacket::graceName, OpenGraceScreenS2CPacket::new);

    public static void handle(OpenGraceScreenS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ClientHooks.openGraceScreen(payload.graceName()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}