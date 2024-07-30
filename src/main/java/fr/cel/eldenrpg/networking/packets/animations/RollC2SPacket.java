package fr.cel.eldenrpg.networking.packets.animations;

import com.zigythebird.playeranimatorapi.API.PlayerAnimAPI;
import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public record RollC2SPacket() implements CustomPayload {

    private static final RollC2SPacket INSTANCE = new RollC2SPacket();

    public static final Id<RollC2SPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "roll"));
    public static final PacketCodec<RegistryByteBuf, RollC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(RollC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        if (player.isInFluid() || player.isSpectator() || !player.isOnGround() || player.isClimbing() || player.isOnGround()) return;

        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        long currentTime = System.currentTimeMillis();
        long lastRollTime = playerData.eldenRPG_Mod$getLastRollTime();
        int cooldownMillis = 400; // 2 secondes

        if (currentTime - lastRollTime < cooldownMillis) return;

        playerData.eldenRPG_Mod$setLastRollTime(currentTime);
        player.setSprinting(false);

        // TODO animation roulade
        PlayerAnimAPI.playPlayerAnim(player.getServerWorld(), player, Identifier.of(EldenRPG.MOD_ID, "roll"));

        Vec3d lookDirection = player.getRotationVec(1.0F);
        Vec3d rollVector = lookDirection.multiply(0.4);
        player.addVelocity(rollVector.x, 0, rollVector.z);
        player.velocityModified = true;

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 13, 255, false, false, true));
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}