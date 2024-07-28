package fr.cel.eldenrpg.mixin.client.screen;

import fr.cel.eldenrpg.util.IScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AccessibilityOnboardingScreen.class)
public abstract class AccessibilityOnboardingScreenMixin extends Screen {

    protected AccessibilityOnboardingScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    private void renderCustomPanorama(DrawContext context, float delta, CallbackInfo ci) {
        ci.cancel();
        ((IScreen)this).eldenRPG_Mod$getCustomPanorama().render(context, this.width, this.height, 1.0F, 0.0F);
    }

}
