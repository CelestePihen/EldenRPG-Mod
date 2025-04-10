package fr.cel.eldenrpg.item.custom;

import fr.cel.eldenrpg.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;

import java.util.List;

public class KeyItem extends Item {

    public KeyItem() {
        super(new Settings().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient()) return ActionResult.PASS;

        if (!context.getWorld().isClient() && context.getHand() == Hand.MAIN_HAND) {
            BlockHitResult hitResult = raycast(context.getWorld(), context.getPlayer(), RaycastContext.FluidHandling.ANY);
            if (hitResult.getType() == HitResult.Type.MISS) {
                return ActionResult.PASS;
            } else {
                BlockState state = context.getWorld().getBlockState(hitResult.getBlockPos());
                if (!state.isOf(Blocks.IRON_DOOR)) return ActionResult.PASS;

                BlockPos targetPos = hitResult.getBlockPos().offset(state.get(Properties.HORIZONTAL_FACING));

                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) context.getPlayer();
                serverPlayer.teleport(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, false);
                serverPlayer.setSpawnPoint(context.getWorld().getRegistryKey(), targetPos, 0.0F, true, false);

                serverPlayer.getInventory().remove(stack -> stack.getItem() == ModItems.KEY, 64, serverPlayer.getInventory());
            }
        }

        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.eldenrpg.key.desc"));
    }

}