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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin extends Screen {

    @Unique private final int eldenRPG_Mod$imageWidth;
    @Unique private final int eldenRPG_Mod$imageHeight;

    @Unique private int eldenRPG_Mod$leftPos;
    @Unique private int eldenRPG_Mod$topPos;

    @Unique private static final ResourceLocation CREATIVE_INVENTORY_TABS_IMAGE = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    protected InventoryScreenMixin(Component pTitle) {
        super(pTitle);

        this.eldenRPG_Mod$imageWidth = 176;
        this.eldenRPG_Mod$imageHeight = 166;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        this.eldenRPG_Mod$leftPos = (this.width - this.eldenRPG_Mod$imageWidth) / 2;
        this.eldenRPG_Mod$topPos = (this.height - this.eldenRPG_Mod$imageHeight) / 2;

        Button mapButton = Button.builder(Component.empty(), button -> {
            minecraft.setScreen(new MapScreen());
        }).bounds(eldenRPG_Mod$leftPos + 26, eldenRPG_Mod$topPos - 28, 26, 30).build();

        Button backpackButton = Button.builder(Component.empty(), button -> ModMessages.sendToServer(new OpenBackpackC2SPacket()))
                .bounds(eldenRPG_Mod$leftPos + 52, eldenRPG_Mod$topPos - 28, 26, 30).build();

       addWidget(mapButton);
       addWidget(backpackButton);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        eldenRPG_Mod$renderTabButton(pGuiGraphics, new ItemStack(Blocks.CRAFTING_TABLE), eldenRPG_Mod$leftPos);
        eldenRPG_Mod$renderTabButton(pGuiGraphics, new ItemStack(Items.MAP), eldenRPG_Mod$leftPos + 26);
        eldenRPG_Mod$renderTabButton(pGuiGraphics, new ItemStack(Blocks.CHEST), eldenRPG_Mod$leftPos + 52);
    }

    @Unique
    private void eldenRPG_Mod$renderTabButton(GuiGraphics pGuiGraphics, ItemStack itemStack, int x) {
        RenderSystem.enableBlend();

        int x1 = x;
        int y = eldenRPG_Mod$topPos - 30;

        pGuiGraphics.blit(CREATIVE_INVENTORY_TABS_IMAGE, x1, y, 0, 0, 26, 32);

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, 0.0F, 100.0F);

        x1 += 5;
        y += 9;

        pGuiGraphics.renderItem(itemStack, x1, y);
        pGuiGraphics.renderItemDecorations(this.font, itemStack, x1, y);
        pGuiGraphics.pose().popPose();
        RenderSystem.disableBlend();
    }

}