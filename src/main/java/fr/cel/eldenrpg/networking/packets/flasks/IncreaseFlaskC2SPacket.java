package fr.cel.eldenrpg.networking.packets.flasks;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record IncreaseFlaskC2SPacket() implements CustomPayload {

    private static final IncreaseFlaskC2SPacket INSTANCE = new IncreaseFlaskC2SPacket();

    public static final Id<IncreaseFlaskC2SPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "increaseflask"));
    public static final PacketCodec<RegistryByteBuf, IncreaseFlaskC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(IncreaseFlaskC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        if (FlasksData.convertSeedToFlask(playerData)) {
            FlasksData.syncGoldenSeed(FlasksData.getGoldenSeeds(playerData), player);
            FlasksData.syncFlasks(FlasksData.getFlasks(playerData), player);
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
