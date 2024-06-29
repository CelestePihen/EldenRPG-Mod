package fr.cel.eldenrpg.block.custom;

import fr.cel.eldenrpg.sound.ModSounds;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import fr.cel.eldenrpg.util.data.GracesData;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GraceBlock extends Block {

    // faire model 3D qui ressemble à :
    // https://media.sketchfab.com/models/b0d68c8f4cd0487da3d1fb8327ab1044/thumbnails/1284c50549804436af85a32618855e7a/99a6abdb401e4ba1a11772ac3381908f.jpeg

    /**
     * Le bloc qui fait office de Site de Grace.
     * Ici, on fait en sorte qu'il émette une luminosité au max, que sa couleur sur les maps soit marron et qu'il drop rien
     */
    public GraceBlock() {
        super(Settings.create().luminance(value -> 15).mapColor(MapColor.BROWN).dropsNothing());
    }

    /**
     * Quand un joueur fait clique droit, cela active cette méthode
     * @param state L'état du bloc
     * @param world Le monde
     * @param pos La position du bloc
     * @param player Le joueur qui a intéragi
     * @param hit La face du bloc sur lequel le joueur a intéragi
     * @return Le résultat de l'action
     */
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            IPlayerDataSaver playerDataSaver = (IPlayerDataSaver) player;

            FlasksData.addFlasks(playerDataSaver, 15);

            GracesData.getGraces().forEach((gracePos, text) -> {
                if (checkCampfire(pos, gracePos)) {
                    if (!GracesData.getGraces(playerDataSaver).contains(pos.asLong())) {
                        GracesData.addGrace(playerDataSaver, gracePos);
                        serverPlayer.networkHandler.sendPacket(new TitleS2CPacket(Text.translatable("eldenrpg.title.lostgracediscovered")));
                        serverPlayer.networkHandler.sendPacket(new TitleFadeS2CPacket(20, 50, 20));
                    }
                }
            });

            serverPlayer.setHealth(serverPlayer.getMaxHealth());
            serverPlayer.setSpawnPoint(world.getRegistryKey(), pos.north(), serverPlayer.getPitch(), true, true);
        } else {
            player.playSoundToPlayer(ModSounds.LOST_GRACE_DISCOVERED, SoundCategory.BLOCKS, 0.5F, 1.0F);
        }

        return ActionResult.SUCCESS;
    }

    private boolean checkCampfire(BlockPos camp1, BlockPos camp2) {
        return (camp1.getX() == camp2.getX()) && (camp1.getY() == camp2.getY()) && (camp1.getZ() == camp2.getZ());
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return super.getAmbientOcclusionLightLevel(state, world, pos);
    }

}
