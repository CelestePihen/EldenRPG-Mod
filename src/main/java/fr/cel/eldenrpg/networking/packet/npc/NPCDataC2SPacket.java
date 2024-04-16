package fr.cel.eldenrpg.networking.packet.npc;

import fr.cel.eldenrpg.entity.EldenNPC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NPCDataC2SPacket {

    private final int id;
    private final Component customName;
    private final boolean isBaby;

    public NPCDataC2SPacket(int id, Component customName, boolean isBaby) {
        this.id = id;
        this.customName = customName;
        this.isBaby = isBaby;
    }

    public NPCDataC2SPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.customName = buf.readComponent();
        this.isBaby = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeComponent(this.customName);
        buf.writeBoolean(this.isBaby);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();

        ctx.enqueueWork(() -> {
            ServerLevel level = ctx.getSender().serverLevel();

            Entity entity = level.getEntity(id);
            if (entity instanceof EldenNPC npc) {
                npc.setCustomName(customName);
                npc.setBaby(isBaby);
            }
        });

    }

}
