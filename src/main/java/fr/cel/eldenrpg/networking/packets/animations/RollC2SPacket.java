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
        if (player.isInFluid() || player.isSpectator() || !player.isOnGround() || player.isClimbing()) return;

        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        long currentTime = System.currentTimeMillis();

        // 0.4 seconde de délai
        if (currentTime - playerData.eldenRPG_Mod$getLastRollTime() < 400) return;

        playerData.eldenRPG_Mod$setLastRollTime(currentTime);
        player.setSprinting(false);

        // TODO animation
        PlayerAnimAPI.playPlayerAnim(player.getServerWorld(), player, Identifier.of(EldenRPG.MOD_ID, "roll"));

        playerData.eldenRPG_Mod$setInvulnerableTicks(16);

        // le joueur ira là où il regarde
        Vec3d rollVector = player.getRotationVec(1.0F).multiply(0.8);
        player.addVelocity(rollVector.x, 0, rollVector.z);
        player.velocityModified = true;

        player.setSprinting(false);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}