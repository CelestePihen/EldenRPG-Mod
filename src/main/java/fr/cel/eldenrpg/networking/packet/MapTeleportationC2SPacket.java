package fr.cel.eldenrpg.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collections;
import java.util.function.Supplier;

public class MapTeleportationC2SPacket {

    private final double x;
    private final double y;
    private final double z;

    public MapTeleportationC2SPacket(double pX, double pY, double pZ) {
        this.x = pX;
        this.y = pY;
        this.z = pZ;
    }

    public MapTeleportationC2SPacket(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        ServerPlayer player = context.getSender();
        ServerLevel level = player.serverLevel();

        context.enqueueWork(() -> teleportEntity(player, level, this.x, this.y, this.z));
    }

    private void teleportEntity(Entity entity, ServerLevel targetWorld, double x, double y, double z) {
        // multijoueur
        if (entity instanceof ServerPlayer) {
            ChunkPos chunkPos = new ChunkPos(BlockPos.containing(x, y, z));
            targetWorld.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, entity.getId());

            if (targetWorld == entity.level()) {
                ((ServerPlayer) entity).connection.teleport(x, y, z, entity.getYRot(), entity.getXRot(), Collections.emptySet());
            } else {
                ((ServerPlayer) entity).teleportTo(targetWorld, x, y, z, entity.getYRot(), entity.getXRot());
            }

            entity.setYHeadRot(entity.getYRot());
        }
        // peut etre que c'était pour les mobs mais ça me paraitrait bizarre car il y a le unRide() pour le joueur je suppose ?
        // je sais pas quand ça s'utilise du coup au pire je verrai si ça casse tout ou pas
//        else {
//            float pitch = Mth.clamp(entity.getXRot(), -90.0F, 90.0F);
//            if (targetWorld == entity.level()) {
//                entity.moveTo(x, y, z, entity.getYRot(), pitch);
//                entity.setYHeadRot(entity.getYRot());
//            } else {
//                entity.unRide();
//                Entity oldEntity = entity;
//                entity = entity.getType().create(targetWorld);
//                if (entity == null) {
//                    return;
//                }
//
//                entity.restoreFrom(oldEntity);
//                entity.moveTo(x, y, z, entity.getYRot(), pitch);
//                entity.setYHeadRot(entity.getYRot());
//                oldEntity.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
//                targetWorld.addDuringTeleport(entity);
//            }
//        }

        if (!(entity instanceof LivingEntity) || !((LivingEntity) entity).isFallFlying()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1));
            entity.setOnGround(true);
        }

    }

}
