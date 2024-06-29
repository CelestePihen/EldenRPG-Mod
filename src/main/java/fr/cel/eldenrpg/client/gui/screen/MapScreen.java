package fr.cel.eldenrpg.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MapScreen extends Screen {

    public static final Identifier CARTE_DEV = Identifier.of(EldenRPG.MOD_ID, "textures/gui/carte_dev.png");

    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    private ButtonWidget campfiresSelection;

    public MapScreen() {
        super(Text.translatable("eldenrpg.map.screen.title"));

        this.imageWidth = 240;
        this.imageHeight = 240;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        if (this.client == null) return;

        campfiresSelection = ButtonWidget.builder(Text.translatable("eldenrpg.map.screen.gracesselection"),
                button -> this.client.setScreen(new GracesSelectionScreen()))
                .dimensions(leftPos - 87, topPos + 5, 80, 20)
                .build();
        addSelectableChild(campfiresSelection);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        context.drawTexture(CARTE_DEV, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        campfiresSelection.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}