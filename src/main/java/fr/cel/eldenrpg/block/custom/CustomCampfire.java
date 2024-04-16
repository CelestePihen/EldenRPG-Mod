package fr.cel.eldenrpg.block.custom;

import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.firecamp.SetSpawnC2SPacket;
import fr.cel.eldenrpg.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

import java.util.function.ToIntFunction;

public class CustomCampfire extends Block {

    // faire model 3D qui ressemble à :
    // https://media.sketchfab.com/models/b0d68c8f4cd0487da3d1fb8327ab1044/thumbnails/1284c50549804436af85a32618855e7a/99a6abdb401e4ba1a11772ac3381908f.jpeg
    public CustomCampfire() {
        super(Properties.of().lightLevel((s) -> 15).mapColor(MapColor.PODZOL).noLootTable());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            Minecraft.getInstance().gui.setTitle(Component.translatable("eldenrpg.title.setspawn").withStyle(ChatFormatting.GOLD));
            Minecraft.getInstance().gui.setTimes(20, 50, 20);

            pLevel.playSound(pPlayer, pPos, ModSounds.LOST_GRACE_DISCOVERED.get(), SoundSource.AMBIENT, 0.5F, 1.0F);

            ModMessages.sendToServer(new SetSpawnC2SPacket(pPos));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return super.getLightEmission(state, level, pos);
    }
}