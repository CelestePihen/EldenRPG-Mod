package fr.cel.eldenrpg.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.menu.SlotsMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SlotsScreen extends AbstractContainerScreen<SlotsMenu> {

    private final ResourceLocation gui = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/gui/slots_inventory.png");
    private final int slotAmount;

    public SlotsScreen(SlotsMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        slotAmount = pMenu.getSlotAmount();
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, gui);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(gui, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
        for (int i = 0; i < slotAmount; i++) {
            int x = i % 9;
            int y = i / 9;
            pGuiGraphics.blit(gui, relX + 7 + x * 18, relY + 16 + y * 18, 7, 83, 18, 18);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752);
        pGuiGraphics.drawString(this.font, playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752);
    }

}
