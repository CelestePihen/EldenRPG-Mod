package fr.cel.eldenrpg.networking.packets.npc;

import fr.cel.eldenrpg.entity.custom.EldenNPC;
import fr.cel.eldenrpg.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;

public record NPCDataC2SPacket(int entityId, String customName, boolean isBaby) implements CustomPayload {

    public static final Id<NPCDataC2SPacket> ID = new Id<>(ModMessages.NPC_DATA_ID);
    public static final PacketCodec<RegistryByteBuf, NPCDataC2SPacket> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, NPCDataC2SPacket::entityId, PacketCodecs.STRING, NPCDataC2SPacket::customName, PacketCodecs.BOOL, NPCDataC2SPacket::isBaby, NPCDataC2SPacket::new);

    public static void handle(NPCDataC2SPacket payload, ServerPlayNetworking.Context context) {
        if (context.player().getWorld().getEntityById(payload.entityId()) instanceof EldenNPC npc) {
            npc.setCustomName(Text.literal(payload.customName()));
            npc.setBaby(payload.isBaby());
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
