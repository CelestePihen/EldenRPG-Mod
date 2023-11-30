package fr.cel.eldenrpg.networking.packet.flasks;

import fr.cel.eldenrpg.capabilities.flasks.PlayerFlasksProvider;
import fr.cel.eldenrpg.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DrinkFlaskC2SPacket {

    public DrinkFlaskC2SPacket() {

    }

    public DrinkFlaskC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks -> {
                if (flasks.getFlasks() > 0) {
                    flasks.subFlasks(1);
                    player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 1));
                    ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), player);
                }
            });
        });
    }

}
