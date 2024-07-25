package fr.cel.eldenrpg.networking.packets.graces;

import fr.cel.eldenrpg.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public record MapTeleportationC2SPacket(BlockPos pos) implements CustomPayload {

    public static final Id<MapTeleportationC2SPacket> ID = new Id<>(ModMessages.MAP_TELEPORTATION_ID);
    public static final PacketCodec<RegistryByteBuf, MapTeleportationC2SPacket> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, MapTeleportationC2SPacket::pos, MapTeleportationC2SPacket::new);

    public static void handle(MapTeleportationC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        BlockPos pos = payload.pos();

        teleportEntity(player, player.getServerWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }

    private static void teleportEntity(ServerPlayerEntity player, ServerWorld world, double x, double y, double z) {
        ChunkPos chunkPos = new ChunkPos(BlockPos.ofFloored(x, y ,z));
        world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, player.getId());

        if (world == player.getWorld()) {
            player.networkHandler.requestTeleport(x, y, z, player.getYaw(), player.getPitch());
        } else {
            player.teleport(world, x, y, z, player.getYaw(), player.getPitch());
        }

        player.setHeadYaw(player.getYaw());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}