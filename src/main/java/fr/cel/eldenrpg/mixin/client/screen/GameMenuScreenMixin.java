package fr.cel.eldenrpg.mixin.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "disconnect", at = @At("HEAD"), cancellable = true)
    private static void disconnect(MinecraftClient client, Text disconnectReason, CallbackInfo ci) {
        ci.cancel();

        boolean isInSinglePlayer = client.isInSingleplayer();
        if (client.world != null) {
            client.world.disconnect(disconnectReason);
        }

        if (isInSinglePlayer) {
            client.disconnectWithSavingScreen();
        } else {
            client.disconnectWithProgressScreen();
        }

        client.setScreen(new TitleScreen());
    }

}