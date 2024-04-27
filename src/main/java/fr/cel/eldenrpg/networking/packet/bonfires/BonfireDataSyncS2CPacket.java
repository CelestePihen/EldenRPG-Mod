package fr.cel.eldenrpg.networking.packet.bonfires;

import fr.cel.eldenrpg.client.data.ClientBonfiresData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BonfireDataSyncS2CPacket {

    private final BlockPos blockPos;
    private final Component name;

    public BonfireDataSyncS2CPacket(BlockPos blockPos, Component name) {
        this.blockPos = blockPos;
        this.name = name;
    }

    public BonfireDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.name = buf.readComponent();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeComponent(name);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientBonfiresData.add(blockPos, name));
    }

}
