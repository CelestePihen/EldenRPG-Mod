package fr.cel.eldenrpg.networking.packets.graces.screen;

import fr.cel.eldenrpg.EldenRPG;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record OpenChestC2SPacket() implements CustomPayload {

    private static final OpenChestC2SPacket INSTANCE = new OpenChestC2SPacket();

    public static final Id<OpenChestC2SPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "openchest"));
    public static final PacketCodec<RegistryByteBuf, OpenChestC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(OpenChestC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        EnderChestInventory enderChestInventory = player.getEnderChestInventory();

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) ->
                        GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory), Text.translatable("container.chest"))
        );
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
