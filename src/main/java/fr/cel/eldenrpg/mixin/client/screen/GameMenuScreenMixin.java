package fr.cel.eldenrpg.mixin.client.screen;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    @Shadow @Final private static Text SAVING_LEVEL_TEXT;

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "disconnect", at = @At("HEAD"), cancellable = true)
    private void disconnect(CallbackInfo ci) {
        ci.cancel();

        boolean bl = this.client.isInSingleplayer();
        ServerInfo serverInfo = this.client.getCurrentServerEntry();
        this.client.world.disconnect();
        if (bl) {
            this.client.disconnect(new MessageScreen(SAVING_LEVEL_TEXT));
        } else {
            this.client.disconnect();
        }

        TitleScreen titleScreen = new TitleScreen();
        if (bl) {
            this.client.setScreen(titleScreen);
        } else if (serverInfo != null && serverInfo.isRealm()) {
            this.client.setScreen(new RealmsMainScreen(titleScreen));
        } else {
            this.client.setScreen(titleScreen);
        }
    }

}