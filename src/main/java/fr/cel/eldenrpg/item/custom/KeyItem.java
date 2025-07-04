package fr.cel.eldenrpg.item.custom;

import fr.cel.eldenrpg.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class KeyItem extends Item {

    public KeyItem() {
        super(new Settings().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (world.isClient()) return TypedActionResult.pass(itemStack);

        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            BlockHitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.ANY);
            if (hitResult.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            } else {
                BlockState state = world.getBlockState(hitResult.getBlockPos());
                if (!state.isOf(Blocks.IRON_DOOR)) return TypedActionResult.pass(itemStack);

                BlockPos targetPos = hitResult.getBlockPos().offset(state.get(Properties.HORIZONTAL_FACING));

                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.teleport(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, false);
                serverPlayer.setSpawnPoint(world.getRegistryKey(), targetPos, 0.0F, true, false);

                serverPlayer.getInventory().remove(stack -> stack.getItem() == ModItems.KEY, Integer.MAX_VALUE, player.getInventory());
            }
        }

        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.eldenrpg.key.desc"));
    }

}