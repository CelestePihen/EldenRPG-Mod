package fr.cel.eldenrpg.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.menu.BackpackMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class SlotsScreen extends AbstractContainerScreen<BackpackMenu> {

    private static final ResourceLocation GUI = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/gui/slots_inventory.png");
    private static final ResourceLocation CREATIVE_INVENTORY_TABS_IMAGE = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private final int slotAmount;

    public SlotsScreen(BackpackMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        slotAmount = menu.getSlotAmount();
    }

    @Override
    protected void init() {
        super.init();

        Button inventoryButton = Button.builder(Component.empty(), button -> {
            if (getMinecraft().gameMode.isServerControlledInventory()) {
                getMinecraft().player.sendOpenInventory();
            } else {
                getMinecraft().getTutorial().onOpenInventory();
                getMinecraft().setScreen(new InventoryScreen(getMinecraft().player));
            }
        }).bounds(leftPos, topPos - 28, 26, 30).build();

        Button mapButton = Button.builder(Component.empty(), button -> {
            minecraft.setScreen(new MapScreen());
        }).bounds(leftPos + 26, topPos - 28, 26, 30).build();

        addWidget(inventoryButton);
        addWidget(mapButton);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        renderTabButton(pGuiGraphics, new ItemStack(Blocks.CRAFTING_TABLE), leftPos);
        renderTabButton(pGuiGraphics, new ItemStack(Items.MAP), leftPos + 26);
        renderTabButton(pGuiGraphics, new ItemStack(Blocks.CHEST), leftPos + 52);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(GUI, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        for (int i = 0; i < slotAmount; i++) {
            int x = i % 9;
            int y = i / 9;
            pGuiGraphics.blit(GUI, leftPos + 7 + x * 18, topPos + 16 + y * 18, 7, 83, 18, 18);
        }
    }

    private void renderTabButton(GuiGraphics pGuiGraphics, ItemStack itemStack, int x) {
        RenderSystem.enableBlend();

        int x1 = x;
        int y = topPos - 30;

        pGuiGraphics.blit(CREATIVE_INVENTORY_TABS_IMAGE, x1, y, 0, 0, 26, 32);

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, 0.0F, 100.0F);

        x1 += 5;
        y += 9;

        pGuiGraphics.renderItem(itemStack, x1, y);
        pGuiGraphics.renderItemDecorations(this.font, itemStack, x1, y);
        pGuiGraphics.pose().popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
