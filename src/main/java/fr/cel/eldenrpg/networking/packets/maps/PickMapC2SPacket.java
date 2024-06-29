package fr.cel.eldenrpg.networking.packets.maps;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.MapsData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record PickMapC2SPacket(int mapId) implements CustomPayload {

    public static final Id<PickMapC2SPacket> ID = new Id<>(ModMessages.PICK_MAP_ID);
    public static final PacketCodec<RegistryByteBuf, PickMapC2SPacket> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, PickMapC2SPacket::mapId, PickMapC2SPacket::new);

    public static void handle(PickMapC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        IPlayerDataSaver entityDataSaver = (IPlayerDataSaver) player;

        MapsData.addMapId(entityDataSaver, payload.mapId());
        MapsData.syncMap(player, payload.mapId());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
