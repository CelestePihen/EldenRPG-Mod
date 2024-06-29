package fr.cel.eldenrpg.mixin;

import fr.cel.eldenrpg.client.gui.screen.MapScreen;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin extends Screen {

    @Shadow @Final private RecipeBookWidget recipeBook;
    @Unique private final int imageWidth;
    @Unique private final int imageHeight;

    @Unique private int leftPos;
    @Unique private int topPos;

    @Unique private static final Identifier CREATIVE_INVENTORY_TABS_IMAGE = Identifier.ofVanilla("container/creative_inventory/tab_top_selected_2");

    protected InventoryScreenMixin(Text title) {
        super(title);

        imageWidth = 176;
        imageHeight = 166;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        ButtonWidget mapButton = ButtonWidget.builder(Text.empty(), button ->
                        MinecraftClient.getInstance().setScreen(new MapScreen()))
                .dimensions(leftPos - 52, topPos - 110, 26, 30)
                .build();

        addSelectableChild(mapButton);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!recipeBook.isOpen()) {
            eldenRPG_Mod$renderTabButton(context, new ItemStack(Blocks.CRAFTING_TABLE), leftPos - 78);
            eldenRPG_Mod$renderTabButton(context, new ItemStack(Items.MAP), leftPos - 52);
            eldenRPG_Mod$renderTabButton(context, new ItemStack(Blocks.CHEST), leftPos - 26);
        }
    }

    @Unique
    private void eldenRPG_Mod$renderTabButton(DrawContext context, ItemStack itemStack, int x) {
        int x1 = x;
        int y = topPos - 112;

        context.drawGuiTexture(CREATIVE_INVENTORY_TABS_IMAGE, x1, y, 26, 32);

        context.getMatrices().push();
        context.getMatrices().translate(0.0F, 0.0F, 100.0F);

        x1 += 5;
        y += 9;

        context.drawItem(itemStack, x1, y);
        context.drawItemInSlot(MinecraftClient.getInstance().textRenderer, itemStack, x1, y);
        context.getMatrices().pop();
    }

}
