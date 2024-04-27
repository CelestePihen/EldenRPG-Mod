package fr.cel.eldenrpg.block.custom;

import fr.cel.eldenrpg.capabilities.bonfires.BonfireList;
import fr.cel.eldenrpg.capabilities.bonfires.PlayerBonfireProvider;
import fr.cel.eldenrpg.capabilities.flasks.PlayerFlasksProvider;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.bonfires.BonfireDataSyncS2CPacket;
import fr.cel.eldenrpg.networking.packet.flasks.FlasksDataSyncS2CPacket;
import fr.cel.eldenrpg.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class BonfireBlock extends Block {

    // faire model 3D qui ressemble à :
    // https://media.sketchfab.com/models/b0d68c8f4cd0487da3d1fb8327ab1044/thumbnails/1284c50549804436af85a32618855e7a/99a6abdb401e4ba1a11772ac3381908f.jpeg
    public BonfireBlock() {
        super(Properties.of().lightLevel((s) -> 15).mapColor(MapColor.PODZOL).noLootTable());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;

            pPlayer.getCapability(PlayerFlasksProvider.PLAYER_FLASKS).ifPresent(flasks -> {
                flasks.addFlasks(14);
                ModMessages.sendToPlayer(new FlasksDataSyncS2CPacket(flasks.getFlasks()), serverPlayer);
            });

            BonfireList.getBonfires().forEach((bonfirePos, name) -> {
                if (checkCampfire(pPos, bonfirePos)) {
                    pPlayer.getCapability(PlayerBonfireProvider.PLAYER_BONFIRE).ifPresent(bonfire -> {
                        if (!bonfire.getBonfires().contains(pPos)) {
                            bonfire.addBonfire(bonfirePos);
                            ModMessages.sendToPlayer(new BonfireDataSyncS2CPacket(bonfirePos, name), serverPlayer);
                            serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("eldenrpg.title.setspawn").withStyle(ChatFormatting.GOLD)));
                            serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(20, 50, 20));
                        }
                    });
                }
            });

            pPlayer.setHealth(pPlayer.getMaxHealth());
            serverPlayer.setRespawnPosition(pLevel.dimension(), pPos.north(), pPlayer.getYRot(), true, true);
        } else {
            pLevel.playSound(pPlayer, pPos, ModSounds.LOST_GRACE_DISCOVERED.get(), SoundSource.AMBIENT, 0.5F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return super.getLightEmission(state, level, pos);
    }

    private boolean checkCampfire(BlockPos camp1, BlockPos camp2) {
        return (camp1.getX() == camp2.getX()) && (camp1.getY() == camp2.getY()) && (camp1.getZ() == camp2.getZ());
    }


}