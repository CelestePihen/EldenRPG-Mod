package fr.cel.eldenrpg.mixin.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends RecipeBookScreen<PlayerScreenHandler> {

    @Shadow private float mouseX;
    @Shadow private float mouseY;

    public InventoryScreenMixin(PlayerScreenHandler handler, RecipeBookWidget<?> recipeBook, PlayerInventory inventory, Text title) {
        super(handler, recipeBook, inventory, title);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        ci.cancel();

        if (client.player.isInCreativeMode()) {
            client.setScreen(new CreativeInventoryScreen(
                    client.player, client.player.networkHandler.getEnabledFeatures(), client.options.getOperatorItemsTab().getValue()
            ));
        } else {
            super.init();
            this.titleX = 9999;
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ci.cancel();
        super.render(context, mouseX, mouseY, delta);

        this.drawMouseoverTooltip(context, mouseX, mouseY);
        this.mouseX = (float) mouseX;
        this.mouseY = (float) mouseY;
    }

}