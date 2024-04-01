package fr.cel.eldenrpg.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.client.gui.MapScreen;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.backpack.OpenBackpackC2SPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin extends Screen {

    private final int imageWidth = 176;
    private final int imageHeight = 166;

    private int leftPos;
    private int topPos;

    private static final ResourceLocation CREATIVE_INVENTORY_TABS_IMAGE = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    protected InventoryScreenMixin(Component pTitle) {
        super(pTitle);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomButton(CallbackInfo ci) {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        Button mapButton = Button.builder(Component.empty(), button -> {
            minecraft.setScreen(new MapScreen());
        }).bounds(leftPos + 26, topPos - 28, 26, 30).build();

        Button backpackButton = Button.builder(Component.empty(), button -> {
            ModMessages.sendToServer(new OpenBackpackC2SPacket());
        }).bounds(leftPos + 52, topPos - 28, 26, 30).build();

       addRenderableWidget(mapButton);
       addRenderableWidget(backpackButton);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        renderTabButton(pGuiGraphics, new ItemStack(Blocks.CRAFTING_TABLE), leftPos);
        renderTabButton(pGuiGraphics, new ItemStack(Items.MAP), leftPos + 26);
        renderTabButton(pGuiGraphics, new ItemStack(Blocks.CHEST), leftPos + 52);
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

}