package fr.cel.eldenrpg.networking.packet.maps;

import fr.cel.eldenrpg.capabilities.map.PlayerMapsProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PickMapC2SPacket {

    private final int id;

    public PickMapC2SPacket(int id) {
        this.id = id;
    }

    public PickMapC2SPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            player.getCapability(PlayerMapsProvider.PLAYER_MAPS).ifPresent(maps -> {
                maps.addMap(this.id);
            });
        });
    }

}
