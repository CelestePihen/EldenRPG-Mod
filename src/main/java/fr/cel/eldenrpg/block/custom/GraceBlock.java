package fr.cel.eldenrpg.block.custom;

import fr.cel.eldenrpg.networking.packets.graces.screen.OpenGraceScreenS2CPacket;
import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import fr.cel.eldenrpg.util.data.GracesData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Colors;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GraceBlock extends Block {

    /**
     * Le bloc qui fait office de Site de Grace.
     * Ici, on fait en sorte qu'il émette une luminosité au max, que sa couleur sur les maps soit marron et qu'il drop rien
     */
    public GraceBlock() {
        super(Settings.create().luminance(value -> 15).mapColor(MapColor.BROWN).dropsNothing());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;

            // TODO animation assis
//            PlayerAnimAPI.playPlayerAnim(serverPlayer.getServerWorld(), serverPlayer, Identifier.of(EldenRPG.MOD_ID, "grace_sit"));

            FlasksData.addFlasks(playerDataSaver, 15);
            serverPlayer.setHealth(serverPlayer.getMaxHealth());
            serverPlayer.setSpawnPoint(world.getRegistryKey(), pos.north(), serverPlayer.getPitch(), true, true);

            GracesData.getGraces().forEach((gracePos, text) -> {
                if (checkCampfire(pos, gracePos)) {
                    if (!GracesData.getGraces(playerDataSaver).contains(pos.asLong())) {
                        GracesData.addGrace(playerDataSaver, gracePos);
                        serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(Text.translatable("eldenrpg.title.lostgracediscovered").withColor(Colors.YELLOW)));
                        serverPlayer.networkHandler.sendPacket(new TitleFadeS2CPacket(20, 50, 20));
                        serverPlayer.playSoundToPlayer(ModSounds.LOST_GRACE_DISCOVERED, SoundCategory.BLOCKS, 0.5F, 1.0F);
                    } else {
                        ServerPlayNetworking.send(serverPlayer, new OpenGraceScreenS2CPacket(text));
                    }
                }
            });
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hit);
    }

    private boolean checkCampfire(BlockPos camp1, BlockPos camp2) {
        return (camp1.getX() == camp2.getX()) && (camp1.getY() == camp2.getY()) && (camp1.getZ() == camp2.getZ());
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return super.getAmbientOcclusionLightLevel(state, world, pos);
    }

}