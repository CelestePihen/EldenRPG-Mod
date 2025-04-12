package fr.cel.eldenrpg.networking.packets.roll;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IKeyboard;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EndRollS2CPacket() implements CustomPayload {

    public static final Id<EndRollS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "endroll"));

    public static final PacketCodec<RegistryByteBuf, EndRollS2CPacket> CODEC = PacketCodec.of((packet, buf) -> {}, buf -> new EndRollS2CPacket());

    public static void handle(EndRollS2CPacket payload, ClientPlayNetworking.Context context) {
        IKeyboard keyboard = (IKeyboard) context.player().input;
        keyboard.eldenrpg$setBlocked(false);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}