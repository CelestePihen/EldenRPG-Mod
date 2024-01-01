package fr.cel.eldenrpg.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.client.data.ClientFlaskData;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;

public class FlasksHudOverlay {

    private static final ResourceLocation FLASKS = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/flasks/flask.png");

    public static final IGuiOverlay HUD_FLASKS = (((gui, guiGraphics, partialTick, width, height) -> {
        int x = width / 2;
        int y = height / 2;

        int imageWidth = 32;
        int imageHeight = 32;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, FLASKS);
        guiGraphics.blit(FLASKS, x - 450, y + 200, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        guiGraphics.drawString(gui.getFont(), "" + ClientFlaskData.getPlayerFlasks(), x - 420, y + 222, Color.WHITE.getRGB());
    }));

}