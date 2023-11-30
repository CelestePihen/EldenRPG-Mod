package fr.cel.eldenrpg.networking.packet.slots;

import fr.cel.eldenrpg.capabilities.slots.PlayerSlotsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SlotsSyncS2CPacket {

    private final CompoundTag nbt;

    public SlotsSyncS2CPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public SlotsSyncS2CPacket(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        context.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            player.getCapability(PlayerSlotsProvider.PLAYER_SLOTS).ifPresent(playerSlots -> {
                playerSlots.loadNBTData(nbt);
            });
        });
    }

}
