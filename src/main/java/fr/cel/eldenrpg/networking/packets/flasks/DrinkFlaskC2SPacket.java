package fr.cel.eldenrpg.networking.packets.flasks;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public record DrinkFlaskC2SPacket() implements CustomPayload {

    private static final DrinkFlaskC2SPacket INSTANCE = new DrinkFlaskC2SPacket();

    public static final Id<DrinkFlaskC2SPacket> ID = new Id<>(Identifier.of(EldenRPG.MOD_ID, "drinkflask"));
    public static final PacketCodec<RegistryByteBuf, DrinkFlaskC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(DrinkFlaskC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        if (player.isInCreativeMode() || player.isSpectator()) return;

        IPlayerDataSaver playerData = (IPlayerDataSaver) player;

        // on vérifie si le joueur peut boire
        if (FlasksData.getFlasks(playerData) > 0) {
            // délai entre les fioles
            long currentTime = System.currentTimeMillis();
            long lastFlaskDrunk = playerData.eldenRPG_Mod$getLastFlaskDrunk();
            int cooldownMillis = 1000; // 1 seconde

            if (currentTime - lastFlaskDrunk < cooldownMillis) return;

            playerData.eldenRPG_Mod$setLastFlaskDrunk(currentTime);
            player.setSprinting(false);

            // TODO animation

            FlasksData.removeFlasks(playerData, 1);
            player.playSoundToPlayer(ModSounds.DRINK_FLASK, SoundCategory.PLAYERS, 0.5F, 1.0F);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, FlasksData.getLevelFlasks(playerData), false, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1, false, false, false));
            FlasksData.syncFlasks(FlasksData.getFlasks(playerData), player);
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}