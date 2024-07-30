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

public record AddChargeC2SPacket() implements CustomPayload {

    private static final AddChargeC2SPacket INSTANCE = new AddChargeC2SPacket();

    public static final CustomPayload.Id<AddChargeC2SPacket> ID = new CustomPayload.Id<>(Identifier.of(EldenRPG.MOD_ID, "addcharged"));
    public static final PacketCodec<RegistryByteBuf, AddChargeC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(AddChargeC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        if (FlasksData.convertTearToLevel(playerData)) {
            FlasksData.syncSacredTear(FlasksData.getSacredTears(playerData), player);
            FlasksData.syncLevelFlask(FlasksData.getLevelFlasks(playerData), player);
        }
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

}