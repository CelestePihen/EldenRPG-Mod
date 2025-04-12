package fr.cel.eldenrpg.networking.packets.roll;

//import com.zigythebird.playeranimatorapi.API.PlayerAnimAPI;
import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record RollC2SPacket(float forward, float sideways) implements CustomPayload {

    public static final Id<RollC2SPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "roll"));

    public static final PacketCodec<RegistryByteBuf, RollC2SPacket> CODEC = PacketCodec.of((packet, buf) -> {
        buf.writeFloat(packet.forward);
        buf.writeFloat(packet.sideways);
    }, buf -> new RollC2SPacket(buf.readFloat(), buf.readFloat()));

    public static void handle(RollC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        if (player.isInFluid() || player.isSpectator() || !player.isOnGround() || player.isClimbing()) return;

        IPlayerDataSaver playerData = (IPlayerDataSaver) player;
        long currentTime = System.currentTimeMillis();

        // 0.4 seconde de délai
        if (currentTime - playerData.eldenrpg$getLastRollTime() < 400) return;

        playerData.eldenrpg$setLastRollTime(currentTime);
        playerData.eldenrpg$setRolling(true);
        player.setSprinting(false);
        playerData.eldenrpg$setInvulnerableTicks(16);

        // TODO animation
//        PlayerAnimAPI.playPlayerAnim(player.getServerWorld(), player, Identifier.of(EldenRPG.MOD_ID, "roll"));

        Vec3d look = player.getRotationVec(1.0F);
        Vec3d side = look.crossProduct(new Vec3d(0, 1, 0));

        Vec3d direction = look.multiply(payload.forward).add(side.multiply(-payload.sideways));

        // le joueur ne bouge pas, on fait une roulade arrière
        if (direction.lengthSquared() == 0) direction = look.multiply(-1);

        Vec3d rollVector = direction.normalize().multiply(0.8);
        player.addVelocity(rollVector.x, 0, rollVector.z);
        player.velocityModified = true;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}