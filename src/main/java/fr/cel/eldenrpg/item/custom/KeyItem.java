package fr.cel.eldenrpg.item.custom;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class KeyItem extends Item {

    public KeyItem() {
        super(new Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EldenRPG.MOD_ID, "key")))
                .maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) return ActionResult.PASS;

        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            BlockHitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.ANY);
            if (hitResult.getType() == HitResult.Type.MISS) {
                return ActionResult.PASS;
            } else {
                BlockState state = world.getBlockState(hitResult.getBlockPos());
                if (!state.isOf(Blocks.IRON_DOOR)) return ActionResult.PASS;

                BlockPos targetPos = hitResult.getBlockPos().offset(state.get(Properties.HORIZONTAL_FACING));

                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.teleport(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, false);
                serverPlayer.setSpawnPoint(new ServerPlayerEntity.Respawn(world.getRegistryKey(), targetPos, 0.0F, true), false);

                serverPlayer.getInventory().remove(stack -> stack.getItem() == ModItems.KEY, Integer.MAX_VALUE, player.getInventory());
            }
        }

        return super.use(world, player, hand);
    }

    record KeyTooltipComponent() implements TooltipAppender {
        @Override
        public void appendTooltip(TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components) {
            textConsumer.accept(Text.translatable("item.eldenrpg.key.desc"));
        }
    }

}