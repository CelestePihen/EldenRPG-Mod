package fr.cel.eldenrpg.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.client.data.ClientFlaskData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;

public class FlasksHudOverlay implements IGuiOverlay {

    private static final ResourceLocation FLASKS = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/flasks/flask.png");
    private static final ResourceLocation EMPTY_POTION = new ResourceLocation("textures/item/potion.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (Minecraft.getInstance().player.getAbilities().invulnerable) return;
        gui.setupOverlayRenderState(true, false);

        int xPosition = screenWidth / 2 - 125;
        int yPosition = screenHeight - 34;

        guiGraphics.drawString(gui.getFont(), "" + ClientFlaskData.getPlayerFlasks(), xPosition - 4, yPosition + 15, Color.WHITE.getRGB());

        if (ClientFlaskData.getPlayerFlasks() <= 0) {
            renderFlasks(EMPTY_POTION, gui, guiGraphics, xPosition, yPosition);
        } else {
            renderFlasks(FLASKS, gui, guiGraphics, xPosition, yPosition);
        }
    }

    public void renderFlasks(ResourceLocation texture, ForgeGui gui, GuiGraphics guiGraphics, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);

        guiGraphics.blit(texture, x, y, 0, 0, 32, 32, 32, 32);

        gui.leftHeight += 10;
    }

}