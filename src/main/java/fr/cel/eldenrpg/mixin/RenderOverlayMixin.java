package fr.cel.eldenrpg.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Colors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class RenderOverlayMixin {

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    private void render(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        ci.cancel();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        float h = client.player.getHealth();

        int barWidth = 180;
        int barHeight = 10;
        int barX = (context.getScaledWindowWidth() - barWidth) / 2;
        int barY = (context.getScaledWindowHeight() - barHeight) - 55;

        int currentHealthWidth = (int) ((double) h / maxHealth * barWidth);

        context.fill(barX, barY, barX + currentHealthWidth, barY + barHeight, Colors.RED);

        String healthText = String.format("%.1f", h) + " / " + String.format("%.1f", maxHealth);
        int textX = barX + (barWidth - client.textRenderer.getWidth(healthText)) / 2;
        context.drawText(client.textRenderer, healthText, textX, barY + 1, Colors.WHITE, true);
    }

    @Inject(method = "renderFood", at = @At("HEAD"), cancellable = true)
    private void render(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        ci.cancel();
    }

}
