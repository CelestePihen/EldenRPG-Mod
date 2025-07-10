package fr.cel.eldenrpg.client.overlay;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FlasksHudOverlay implements HudElement {

    private static final Identifier FLASKS = Identifier.of(EldenRPG.MOD_ID, "textures/gui/flasks/flask.png");
    private static final Identifier EMPTY_POTION = Identifier.ofVanilla("textures/item/potion.png");

    public void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        if (client.player == null) return;

        if (client.player.getAbilities().invulnerable) return;

        int xPosition = client.getWindow().getScaledWidth() / 2 - 153;
        int yPosition = client.getWindow().getScaledHeight() - 34;

        IPlayerDataSaver playerData = (IPlayerDataSaver) client.player;
        Optional<Integer> flasks = playerData.getPersistentData().getInt("flasks");
        Optional<Integer> levelFlask = playerData.getPersistentData().getInt("levelFlasks");

        if (flasks.isEmpty() || levelFlask.isEmpty()) return;

        drawContext.drawText(client.textRenderer, "" + flasks.get(), xPosition - 4, yPosition + 15, Colors.WHITE, true);
        drawContext.drawText(client.textRenderer, "+" + levelFlask.get(), xPosition - 12, yPosition + 25, Colors.WHITE, true);

        if (flasks.get() <= 0) {
            renderFlasks(EMPTY_POTION, drawContext, xPosition, yPosition);
        } else {
            renderFlasks(FLASKS, drawContext, xPosition, yPosition);
        }
    }

    private void renderFlasks(Identifier texture, DrawContext drawContext, int x, int y) {
        drawContext.drawTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, 0, 0, 32, 32, 32, 32);
    }

}