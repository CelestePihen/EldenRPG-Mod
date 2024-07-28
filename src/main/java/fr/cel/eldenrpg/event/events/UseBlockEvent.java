package fr.cel.eldenrpg.event.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class UseBlockEvent implements UseBlockCallback {

    public static void init() {
        UseBlockCallback.EVENT.register(new UseBlockEvent());
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
        if (block == Blocks.CRAFTING_TABLE || block == Blocks.FURNACE || block == Blocks.BLAST_FURNACE || block == Blocks.SMOKER || block == Blocks.BARREL) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }

}