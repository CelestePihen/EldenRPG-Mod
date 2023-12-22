package fr.cel.eldenrpg.networking.packet.backpack;

import fr.cel.eldenrpg.capabilities.slots.PlayerBackpackProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BackpackSyncS2CPacket {

    private final CompoundTag nbt;

    public BackpackSyncS2CPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public BackpackSyncS2CPacket(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();

        ctx.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) { return; }

            player.getCapability(PlayerBackpackProvider.PLAYER_BACKPACK).ifPresent(playerSlots -> {
                playerSlots.loadNBTData(nbt);
            });
        });
    }

}
