package fr.cel.eldenrpg.mixin.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {

    @Shadow private float mouseX;
    @Shadow private float mouseY;

    @Shadow @Final private RecipeBookWidget recipeBook;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        ci.cancel();

        if (this.client.interactionManager.hasCreativeInventory()) {
            this.client.setScreen(new CreativeInventoryScreen(
                    this.client.player, this.client.player.networkHandler.getEnabledFeatures(), this.client.options.getOperatorItemsTab().getValue()
            ));
        } else {
            super.init();
            this.titleX = 9999;
            this.recipeBook.initialize(this.width, this.height, this.client, this.width < 379, this.handler);
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
