package fr.cel.eldenrpg.networking.packet.firecamp;

import fr.cel.eldenrpg.capabilities.firecamp.CampfireList;
import fr.cel.eldenrpg.capabilities.firecamp.PlayerCampfireProvider;
import fr.cel.eldenrpg.capabilities.flasks.PlayerFlasksProvider;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetSpawnC2SPacket {

    private final BlockPos blockPos;

    public SetSpawnC2SPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public SetSpawnC2SPacket(FriendlyByteBuf buf) {
        this.blockPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks -> {
                flasks.addFlasks(14);
                ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), player);
            });

            CampfireList.CAMPFIRES.forEach((blockPos, name) -> {
                if (checkCampfire(this.blockPos, blockPos)) {
                    player.getCapability(PlayerCampfireProvider.PLAYER_CAMPFIRE).ifPresent(campfire -> {
                        campfire.addCampfire(blockPos);
                        ModMessages.sendToPlayer(new FirecampsDataSyncS2CPacket(blockPos, name), player);
                    });
                }

            });

            player.setHealth(player.getMaxHealth());
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(20);
            Minecraft.getInstance().getConnection().setTitleText(new ClientboundSetTitleTextPacket(Component.translatable("eldenrpg.title.setspawn")));
            Minecraft.getInstance().getConnection().setTitlesAnimation(new ClientboundSetTitlesAnimationPacket(20, 50, 20));
            player.setRespawnPosition(level.dimension(), blockPos.north(), 90.0F, true, true);

        });

    }

    private boolean checkCampfire(BlockPos camp1, BlockPos camp2) {
        return (camp1.getX() == camp2.getX()) && (camp1.getY() == camp2.getY()) && (camp1.getZ() == camp2.getZ());
    }

}
