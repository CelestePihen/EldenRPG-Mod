package fr.cel.eldenrpg.networking.packets.flasks;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IKeyboard;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class EndDrinkFlaskS2CPacket implements CustomPayload {

    public static final Id<EndDrinkFlaskS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "enddrinkflask"));

    public static final PacketCodec<RegistryByteBuf, EndDrinkFlaskS2CPacket> CODEC = PacketCodec.of((packet, buf) -> {}, buf -> new EndDrinkFlaskS2CPacket());

    public static void handle(EndDrinkFlaskS2CPacket payload, ClientPlayNetworking.Context context) {
        IKeyboard keyboard = (IKeyboard) context.player().input;
        keyboard.setBlockJump(false);
        keyboard.setBlockSneak(false);
        keyboard.setBlockSprint(false);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
