package fr.cel.eldenrpg.networking.packet.maps;

import fr.cel.eldenrpg.client.data.ClientMapsData;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MapsDataSyncS2CPacket {

    private final int mapId;

    public MapsDataSyncS2CPacket(int mapId) {
        this.mapId = mapId;
    }

    public MapsDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.mapId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.mapId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientMapsData.add(mapId));
    }

}
