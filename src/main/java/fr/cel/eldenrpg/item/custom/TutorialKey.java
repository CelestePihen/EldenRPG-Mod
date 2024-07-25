package fr.cel.eldenrpg.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class TutorialKey extends Item {

    private final BlockPos[] blocksPos = new BlockPos[] { new BlockPos(154, 30, -89), new BlockPos(154, 31, -89) };

    public TutorialKey() {
        super(new Settings().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        HitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.ANY);

        if (world.isClient()) return TypedActionResult.pass(itemStack);

        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            if (hitResult.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            } else {
                BlockPos pos = BlockPos.ofFloored(hitResult.getPos());
                if (pos.equals(blocksPos[0]) | pos.equals(blocksPos[1])) {
                    ((ServerPlayerEntity) player).networkHandler.requestTeleport(154.5, 30, -88.5, -90, 0);
                    player.getInventory().removeOne(itemStack);
                }
            }
        }

        return super.use(world, player, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.eldenrpg.tutorial_key.desc"));
    }

}