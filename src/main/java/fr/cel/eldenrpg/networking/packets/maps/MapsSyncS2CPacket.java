package fr.cel.eldenrpg.networking.packets.maps;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.MapsData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MapsSyncS2CPacket(int mapId) implements CustomPayload {

    public static final Id<MapsSyncS2CPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "syncmaps"));
    public static final PacketCodec<RegistryByteBuf, MapsSyncS2CPacket> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, MapsSyncS2CPacket::mapId, MapsSyncS2CPacket::new);

    public static void handle(MapsSyncS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> MapsData.addMapId((IPlayerDataSaver) context.player(), payload.mapId()));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}