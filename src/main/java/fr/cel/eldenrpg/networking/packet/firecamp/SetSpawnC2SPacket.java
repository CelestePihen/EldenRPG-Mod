package fr.cel.eldenrpg.networking.packet.firecamp;

import fr.cel.eldenrpg.EldenRPGMod;
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

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ServerLevel level = player.serverLevel();

            player.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks -> {
                flasks.addFlasks(14);
                ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), player);
            });

            CampfireList.getCampfires().forEach((campFirePos, name) -> {
                if (checkCampfire(this.blockPos, campFirePos)) {
                    player.getCapability(PlayerCampfireProvider.PLAYER_CAMPFIRE).ifPresent(campfire -> {
                        campfire.addCampfire(campFirePos);
                        ModMessages.sendToPlayer(new FirecampsDataSyncS2CPacket(campFirePos, name), player);
                    });
                }

            });

            player.setHealth(player.getMaxHealth());
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(20);
//            EldenRPGMod.LOGGER.info("RESPAWNPOINT AVANT: " + player.getRespawnPosition());
//            EldenRPGMod.LOGGER.info("Blockpos" + blockPos);
//            EldenRPGMod.LOGGER.info("Blockpos north" + blockPos.north());
            player.setRespawnPosition(level.dimension(), blockPos.north(), player.getYRot(), true, true);
//            EldenRPGMod.LOGGER.info("RESPAWNPOINT APRES: " + player.getRespawnPosition());
//            EldenRPGMod.LOGGER.info("BLOCKPOS APRES");
        });

    }

    private boolean checkCampfire(BlockPos camp1, BlockPos camp2) {
        return (camp1.getX() == camp2.getX()) && (camp1.getY() == camp2.getY()) && (camp1.getZ() == camp2.getZ());
    }

}
