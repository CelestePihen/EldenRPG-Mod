package fr.cel.eldenrpg.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.MapsData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MapScreen extends Screen {

    // Les textures des cartes
    private static final Identifier CARTE = Identifier.of(EldenRPG.MOD_ID, "textures/gui/map/map.png");
    private static final Identifier CARTE_DEV = Identifier.of(EldenRPG.MOD_ID, "textures/gui/map/gray_map.png");

    private final int imageWidth = 240, imageHeight = 240;
    private int leftPos, topPos;

    // Boutons
    private ButtonWidget gracesButton;
    private ButtonWidget plusButton;
    private ButtonWidget minusButton;

    // Gérer le zoom et le déplacement
    private float zoom = 1.0F;
    private float offsetX = 0;
    private float offsetY = 0;
    private boolean isDragging = false;

    public MapScreen() {
        super(Text.translatable("eldenrpg.map.screen.title"));
    }

    @Override
    public void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.gracesButton = this.addSelectableChild(ButtonWidget.builder(Text.translatable("eldenrpg.map.screen.gracesselection"), button -> client.setScreen(new GracesSelectionScreen(this)))
                .dimensions(leftPos - 87, topPos + 5, 80, 20).build());

        this.plusButton = this.addSelectableChild(ButtonWidget.builder(Text.of("+"), button -> zoomIn())
                .dimensions(leftPos + imageWidth + 5, topPos + 5, 20, 20).build());
        this.minusButton = this.addSelectableChild(ButtonWidget.builder(Text.of("-"), button -> zoomOut())
                .dimensions(leftPos + imageWidth + 5, topPos + 30, 20, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        IPlayerDataSaver playerData = (IPlayerDataSaver) client.player;
        if (playerData == null) return;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        // Appliquer le zoom et le déplacement
        context.getMatrices().push();
        context.getMatrices().translate(leftPos + imageWidth / 2f, topPos + imageHeight / 2f, 0);
        context.getMatrices().scale(zoom, zoom, 1);
        context.getMatrices().translate(-imageWidth / 2f + offsetX, -imageHeight / 2f + offsetY, 0);

        // Parties de maps
        renderPartMap(1, playerData, context, 84, 113, 93, 63);
        renderPartMap(2, playerData, context, 84, 0, 75, 113);
        renderPartMap(3, playerData, context, 159, 0, 81, 113);
        renderPartMap(4, playerData, context, 50, 0, 42, 100);
        renderPartMap(5, playerData, context, 0, 0, 50, 100);
        renderPartMap(6, playerData, context, 0, 100, 92, 50);
        renderPartMap(7, playerData, context, 0, 150, 92, 90);
        renderPartMap(8, playerData, context, 177, 113, 63, 127);
        renderPartMap(9, playerData, context, 84, 176, 93, 64);

        context.getMatrices().pop();

        // On met les Boutons par dessus la carte
        gracesButton.render(context, mouseX, mouseY, delta);
        plusButton.render(context, mouseX, mouseY, delta);
        minusButton.render(context, mouseX, mouseY, delta);
    }

    private void renderPartMap(int part, IPlayerDataSaver playerData, DrawContext context, int x, int y, int width, int height) {
        if (MapsData.getMapsId(playerData).contains(part)) {
            context.drawTexture(CARTE, x, y, x, y, width, height, imageWidth, imageHeight);
        } else {
            context.drawTexture(CARTE_DEV, x, y, x, y, width, height, imageWidth, imageHeight);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    /** Systèmes de zoom et de déplacement **/

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOverMap(mouseX, mouseY)) {
            isDragging = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isDragging) {
            offsetX += (float) (deltaX / zoom);
            offsetY += (float) (deltaY / zoom);
            clampOffsets();
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (isMouseOverMap(mouseX, mouseY)) {
            if (verticalAmount > 0) {
                zoomIn();
            } else if (verticalAmount < 0) {
                zoomOut();
            }
            clampOffsets();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void zoomIn() {
        zoom = Math.min(zoom * 1.2f, 4.0f);
    }

    private void zoomOut() {
        zoom = Math.max(zoom / 1.2f, 0.5f);
    }

    private boolean isMouseOverMap(double mouseX, double mouseY) {
        return mouseX >= leftPos && mouseX < leftPos + imageWidth && mouseY >= topPos && mouseY < topPos + imageHeight;
    }


    private void clampOffsets() {
        float maxOffsetX = Math.max((imageWidth * zoom - this.width) / (2 * zoom), 0);
        float maxOffsetY = Math.max((imageHeight * zoom - this.height) / (2 * zoom), 0);

        offsetX = MathHelper.clamp(offsetX, -maxOffsetX, maxOffsetX);
        offsetY = MathHelper.clamp(offsetY, -maxOffsetY, maxOffsetY);
    }

}