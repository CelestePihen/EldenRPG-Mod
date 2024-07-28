package fr.cel.eldenrpg.mixin.client.screen;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IScreen;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin implements IScreen {

    @Shadow public int width;
    @Shadow public int height;

    @Unique private static final CubeMapRenderer CUSTOM_PANORAMA_RENDERER = new CubeMapRenderer(Identifier.of(EldenRPG.MOD_ID ,"textures/gui/title/background/panorama"));
    @Unique private static final RotatingCubeMapRenderer CUSTOM_ROTATING_PANORAMA_RENDERER = new RotatingCubeMapRenderer(CUSTOM_PANORAMA_RENDERER);

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    private void renderCustomPanorama(DrawContext context, float delta, CallbackInfo ci) {
        ci.cancel();
        CUSTOM_ROTATING_PANORAMA_RENDERER.render(context, this.width, this.height, 1.0F, delta);
    }

    @Override
    public RotatingCubeMapRenderer eldenRPG_Mod$getCustomPanorama() {
        return CUSTOM_ROTATING_PANORAMA_RENDERER;
    }

}