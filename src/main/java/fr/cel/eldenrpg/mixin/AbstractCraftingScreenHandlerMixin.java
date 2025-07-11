package fr.cel.eldenrpg.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.AbstractCraftingScreenHandler;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCraftingScreenHandler.class)
public abstract class AbstractCraftingScreenHandlerMixin extends AbstractRecipeScreenHandler {

    @Shadow @Final protected RecipeInputInventory craftingInventory;
    @Shadow @Final protected CraftingResultInventory craftingResultInventory;

    @Shadow @Final private int width;
    @Shadow @Final private int height;

    public AbstractCraftingScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "addResultSlot", at = @At("HEAD"), cancellable = true)
    private void addResultSlot(PlayerEntity player, int x, int y, CallbackInfoReturnable<Slot> cir) {
        cir.setReturnValue(this.addSlot(new CraftingResultSlot(player, this.craftingInventory, this.craftingResultInventory, 0, -100, -100)));
    }

    @Inject(method = "addInputSlots", at = @At("HEAD"), cancellable = true)
    private void addInputSlots(int x, int y, CallbackInfo ci) {
        ci.cancel();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.addSlot(new Slot(this.craftingInventory, j + i * this.width, -100, -100));
            }
        }
    }

}