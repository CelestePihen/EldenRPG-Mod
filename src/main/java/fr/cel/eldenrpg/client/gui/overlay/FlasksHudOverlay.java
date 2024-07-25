package fr.cel.eldenrpg.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class FlasksHudOverlay implements HudRenderCallback {

    private static final Identifier FLASKS = Identifier.of(EldenRPG.MOD_ID, "textures/flasks/flask.png");
    private static final Identifier EMPTY_POTION = Identifier.ofVanilla("textures/item/potion.png");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        PlayerEntity player = client.player;
        if (player == null) return;
        if (player.getAbilities().invulnerable) return;

        int xPosition = client.getWindow().getScaledWidth() / 2 - 150;
        int yPosition = client.getWindow().getScaledHeight() - 34;

        int flasks = FlasksData.getFlasks((IPlayerDataSaver) player);

        drawContext.drawText(client.textRenderer, "" + flasks, xPosition - 4, yPosition + 15, Colors.WHITE, true);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (flasks <= 0) {
            renderFlasks(EMPTY_POTION, drawContext, xPosition, yPosition);
        } else {
            renderFlasks(FLASKS, drawContext, xPosition, yPosition);
        }
    }

    private void renderFlasks(Identifier texture, DrawContext drawContext, int x, int y) {
        drawContext.drawTexture(texture, x, y, 0, 0, 32, 32, 32, 32);
    }

}