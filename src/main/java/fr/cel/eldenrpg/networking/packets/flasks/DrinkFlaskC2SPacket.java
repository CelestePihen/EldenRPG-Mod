package fr.cel.eldenrpg.networking.packets.flasks;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record DrinkFlaskC2SPacket() implements CustomPayload {

    private static final DrinkFlaskC2SPacket INSTANCE = new DrinkFlaskC2SPacket();

    public static final Id<DrinkFlaskC2SPacket> ID = new Id<>(ModMessages.DRINK_FLASK_ID);
    public static final PacketCodec<RegistryByteBuf, DrinkFlaskC2SPacket> CODEC = PacketCodec.unit(INSTANCE);

    public static void handle(DrinkFlaskC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();
        IPlayerDataSaver entityDataSaver = (IPlayerDataSaver) player;

        if (FlasksData.getFlasks(entityDataSaver) > 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1));
            FlasksData.removeFlasks(entityDataSaver, 1);
            FlasksData.syncFlasks(FlasksData.getFlasks(entityDataSaver), player);
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}