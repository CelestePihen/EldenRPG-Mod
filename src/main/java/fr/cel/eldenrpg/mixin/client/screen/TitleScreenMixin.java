package fr.cel.eldenrpg.mixin.client.screen;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.client.screen.RecommendationScreen;
import fr.cel.eldenrpg.util.IScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow @Nullable protected abstract Text getMultiplayerDisabledText();
    @Shadow private float backgroundAlpha;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgetsNormal", at = @At("HEAD"), cancellable = true)
    private void changeButtons(int y, int spacingY, CallbackInfo ci) {
        ci.cancel();

        if (client == null) return;

        Text text = this.getMultiplayerDisabledText();
        boolean bl = text == null;
        Tooltip tooltip = text != null ? Tooltip.of(text) : null;

        if (EldenRPG.isIDE) {
            this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) ->
                            this.client.setScreen(new SelectWorldScreen(this)))
                    .dimensions(this.width / 2 - 100, y, 200, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("EldenRPG"), (button) ->
                            ConnectScreen.connect(this, this.client, ServerAddress.parse("localhost"),
                                    new ServerInfo("EldenRPG", "localhost", ServerInfo.ServerType.OTHER),
                                    false,
                                    null))
                    .dimensions(this.width / 2 - 100, y + spacingY, 200, 20).tooltip(tooltip).build()).active = bl;
        } else {
            this.addDrawableChild(ButtonWidget.builder(Text.literal("EldenRPG"), (button) ->
                            ConnectScreen.connect(this, this.client, ServerAddress.parse("95.111.253.89:25501"),
                                    new ServerInfo("EldenRPG", "95.111.253.89:25501", ServerInfo.ServerType.OTHER),
                                    false,
                                    null))
                    .dimensions(this.width / 2 - 100, y + spacingY, 200, 20).tooltip(tooltip).build()).active = bl;
        }

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.recommendation"), (buttonWidget) ->
                        this.client.setScreen(new RecommendationScreen(this)))
                .dimensions(this.width / 2 - 100, y + spacingY * 2, 200, 20).tooltip(tooltip).build());
    }

    @Inject(method = "renderPanoramaBackground", at = @At("HEAD"), cancellable = true)
    private void renderCustomBackground(DrawContext context, float delta, CallbackInfo ci) {
        ci.cancel();
        ((IScreen)this).eldenRPG_Mod$getCustomPanorama().render(context, this.width, this.height, this.backgroundAlpha, delta);
    }

}