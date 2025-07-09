package fr.cel.eldenrpg.mixin.client.screen;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoDrawer.class)
public abstract class LogoDrawerMixin {

    @Shadow @Final private boolean ignoreAlpha;
    @Shadow @Final private boolean minceraft;

    @Unique private static final Identifier LOGO = Identifier.of(EldenRPG.MOD_ID, "textures/gui/title/eldenrpg.png");
    @Unique private static final Identifier ELDERNPG = Identifier.of(EldenRPG.MOD_ID, "textures/gui/title/eldernpg.png");

    @Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", at = @At("HEAD"), cancellable = true)
    private void drawLogo(DrawContext context, int screenWidth, float alpha, int y, CallbackInfo ci) {
        int i = screenWidth / 2 - 128;
        float f = this.ignoreAlpha ? 1.0F : alpha;
        int j = ColorHelper.getWhite(f);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.minceraft ? ELDERNPG : LOGO, i, y, 0.0F, 0.0F, 256, 44, 256, 64, j);

        ci.cancel();
    }

}