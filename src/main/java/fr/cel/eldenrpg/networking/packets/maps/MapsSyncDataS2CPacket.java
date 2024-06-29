package fr.cel.eldenrpg.networking.packets.maps;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.ArrayList;

public record MapsSyncDataS2CPacket(int mapId) implements CustomPayload {

    public static final Id<MapsSyncDataS2CPacket> ID = new Id<>(ModMessages.SYNC_MAP_ID);
    public static final PacketCodec<RegistryByteBuf, MapsSyncDataS2CPacket> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, MapsSyncDataS2CPacket::mapId, MapsSyncDataS2CPacket::new);

    public static void handle(MapsSyncDataS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> ((IPlayerDataSaver) context.player()).
                eldenrpg$getPersistentData().putIntArray("mapsId", new ArrayList<>(payload.mapId())));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}