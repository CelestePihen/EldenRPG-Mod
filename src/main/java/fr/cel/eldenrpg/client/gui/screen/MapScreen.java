package fr.cel.eldenrpg.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.MapsData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MapScreen extends Screen {

    public static final Identifier CARTE = Identifier.of(EldenRPG.MOD_ID, "textures/gui/map/map.png");
    public static final Identifier CARTE_DEV = Identifier.of(EldenRPG.MOD_ID, "textures/gui/map/gray_map.png");

    private final int imageWidth = 240, imageHeight = 240;
    private int leftPos, topPos;

    public MapScreen() {
        super(Text.translatable("eldenrpg.map.screen.title"));
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.map.screen.gracesselection"), button -> client.setScreen(new GracesSelectionScreen(this)))
                .dimensions(leftPos - 87, topPos + 5, 80, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        IPlayerDataSaver playerData = (IPlayerDataSaver) client.player;
        if (playerData == null) return;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // 1
        renderPartMap(1, playerData, context, 84, 113, 93, 63);

        // 2
        renderPartMap(2, playerData, context, 84, 0, 75, 113);

        // 3
        renderPartMap(3, playerData, context, 159, 0, 81, 113);

        // 4
        renderPartMap(4, playerData, context, 50, 0, 42, 100);

        // 5
        renderPartMap(5, playerData, context, 0, 0, 50, 100);

        // 6
        renderPartMap(6, playerData, context, 0, 100, 92, 50);

        // 7
        renderPartMap(7, playerData, context, 0, 150, 92, 90);

        // 8
        renderPartMap(8, playerData, context, 177, 113, 63, 127);

        // 9
        renderPartMap(9, playerData, context, 84, 176, 93, 64);
    }

    private void renderPartMap(int part, IPlayerDataSaver playerData, DrawContext context, int x, int y, int width, int height) {
        if (MapsData.getMapsId(playerData).contains(part)) {
            context.drawTexture(CARTE, leftPos + x, topPos + y, x, y, width, height, imageWidth, imageHeight);
        } else {
            context.drawTexture(CARTE_DEV, leftPos + x, topPos + y, x, y, width, height, imageWidth, imageHeight);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}