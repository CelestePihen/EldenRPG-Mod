package fr.cel.eldenrpg.networking.packet.flasks;

import fr.cel.eldenrpg.client.data.ClientFlaskData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FlasksDataSyncS2CPacket {

    private final int flasks;

    public FlasksDataSyncS2CPacket(int flasks) {
        this.flasks = flasks;
    }

    public FlasksDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.flasks = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(flasks);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientFlaskData.set(flasks));
    }

}
