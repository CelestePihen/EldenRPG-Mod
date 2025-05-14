package fr.cel.eldenrpg.event.events;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
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
//        if (world.isClient()) return ActionResult.PASS;
        if (player.isInCreativeMode()) return ActionResult.PASS;

        BlockState state = world.getBlockState(hitResult.getBlockPos());
        Block block = state.getBlock();

        // utility blocks
        if (block == Blocks.CRAFTING_TABLE || block == Blocks.FURNACE || block == Blocks.BLAST_FURNACE ||
                block == Blocks.SMOKER || block == Blocks.BARREL || block == Blocks.CARTOGRAPHY_TABLE) {
            return ActionResult.FAIL;
        }

        // pot
        if (block instanceof FlowerPotBlock || block instanceof DecoratedPotBlock) {
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }

}