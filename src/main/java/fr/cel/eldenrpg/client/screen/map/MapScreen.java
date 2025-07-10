package fr.cel.eldenrpg.client.screen.map;

import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.MapsData;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MapScreen extends Screen {

    // Textures de la Carte
    private static final Identifier MAP = Identifier.of(EldenRPG.MOD_ID, "textures/gui/map/map.png");
    private static final Identifier GRAY_MAP = Identifier.of(EldenRPG.MOD_ID, "textures/gui/map/gray_map.png");
    private static final Identifier PLAYER_DECORATION = Identifier.of("minecraft", "textures/map/decorations/player.png");

    private final int imageWidth = 240, imageHeight = 240;
    private int leftPos, topPos;

    private ButtonWidget gracesButton;
    private ButtonWidget plusButton;
    private ButtonWidget minusButton;

    // Gérer le zoom / déplacement
    private float zoom = 1.0F, targetZoom = 1.0F;
    private float offsetX = 0, offsetY = 0;
    private float targetOffsetX = 0, targetOffsetY = 0;
    private boolean isDragging = false;

    public MapScreen() {
        super(Text.translatable("eldenrpg.map.screen.title"));
    }

    @Override
    public void init() {
        super.init();

        this.leftPos = (this.width - imageWidth) / 2;
        this.topPos = (this.height - imageHeight) / 2;

        this.gracesButton = this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.map.screen.gracesselection"),
                button -> this.client.setScreen(new GracesSelectionScreen(this)))
                .dimensions(leftPos - 87, topPos + 5, 80, 20).build());

        this.plusButton = this.addDrawableChild(ButtonWidget.builder(Text.of("+"), button -> zoomIn(false))
                .dimensions(leftPos + imageWidth + 5, topPos + 5, 20, 20).build());
        this.minusButton = this.addDrawableChild(ButtonWidget.builder(Text.of("-"), button -> zoomOut(false))
                .dimensions(leftPos + imageWidth + 5, topPos + 30, 20, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        if (this.client == null) return;
        IPlayerDataSaver playerData = (IPlayerDataSaver) this.client.player;

        // Interpolation
        this.zoom = MathHelper.lerp(0.15f, this.zoom, this.targetZoom);
        this.offsetX = MathHelper.lerp(0.15f, this.offsetX, this.targetOffsetX);
        this.offsetY = MathHelper.lerp(0.15f, this.offsetY, this.targetOffsetY);

        // Appliquer le zoom et le déplacement
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(this.leftPos + this.imageWidth / 2f, this.topPos + this.imageHeight / 2f, context.getMatrices());
        context.getMatrices().scale(this.zoom, this.zoom, context.getMatrices());
        context.getMatrices().translate(-this.imageWidth / 2f + this.offsetX, -this.imageHeight / 2f + this.offsetY, context.getMatrices());

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

        context.getMatrices().popMatrix();

        // Curseur du joueur sur la carte
        if (this.client != null && this.client.player != null) {
            double playerX = this.client.player.getX();
            double playerZ = this.client.player.getZ();
            float yaw = this.client.player.getYaw();

            // Coordonnées réelles du centre de la carte
            double centerX = 163.0;
            double centerZ = -97.0;

            float scale = 2.1f;

            // Calcul position relative du joueur par rapport au centre de la carte
            float relativeX = (float) ((playerX - centerX) / scale);
            float relativeY = (float) ((playerZ - centerZ) / scale);

            // Appliquer zoom et offset
            float mapCenterX = this.leftPos + this.imageWidth / 2f;
            float mapCenterY = this.topPos + this.imageHeight / 2f;

            float drawX = mapCenterX + (relativeX + this.offsetX) * this.zoom;
            float drawY = mapCenterY + (relativeY + this.offsetY) * this.zoom;

            int iconSize = 8;

            float mapYaw = (yaw + 180f) % 360f;
            if (mapYaw < 0) mapYaw += 360f;

            float snappedAngle = getVanillaMapRotation(mapYaw);

            context.getMatrices().pushMatrix();
            context.getMatrices().translate(drawX, drawY);
            context.getMatrices().rotate((float) Math.toRadians(snappedAngle));
            context.getMatrices().translate(-iconSize / 2f, -iconSize / 2f);
            context.drawTexture(RenderPipelines.GUI_TEXTURED, PLAYER_DECORATION, 0, 0, 0f, 0f, iconSize, iconSize, iconSize, iconSize);
            context.getMatrices().popMatrix();
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderPartMap(int part, IPlayerDataSaver playerData, DrawContext context, int x, int y, int width, int height) {
        if (contains(MapsData.getMapsId(playerData), part)) {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, MAP, x, y, x, y, width, height, this.imageWidth, this.imageHeight);
        } else {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, GRAY_MAP, x, y, x, y, width, height, this.imageWidth, this.imageHeight);
        }
    }

    private boolean contains(int[] array, int value) {
        for (int v : array) {
            if (v == value) return true;
        }
        return false;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    /* Systèmes de zoom et de déplacement */

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean handled = super.mouseClicked(mouseX, mouseY, button);

        if (button == 0 && !handled) {
            this.isDragging = true;
            return true;
        }

        return handled;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.isDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.isDragging) {
            this.targetOffsetX += (float) (deltaX / this.zoom);
            this.targetOffsetY += (float) (deltaY / this.zoom);
            clampOffsets();
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount != 0) {
            float oldZoom = this.targetZoom;

            if (verticalAmount > 0) {
                zoomIn(true);
            } else if (verticalAmount < 0) {
                zoomOut(true);
            }

            float zoomFactor = this.targetZoom / oldZoom;
            float mapCenterX = (float) (mouseX - this.leftPos - this.imageWidth / 2.0);
            float mapCenterY = (float) (mouseY - this.topPos - this.imageHeight / 2.0);

            this.targetOffsetX -= mapCenterX * (1 - 1 / zoomFactor);
            this.targetOffsetY -= mapCenterY * (1 - 1 / zoomFactor);

            clampOffsets();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void zoomIn(boolean isMouse) {
        this.targetZoom = Math.min(this.targetZoom * 1.2f, 4.0f);
        if (!isMouse) clampOffsets();
    }

    private void zoomOut(boolean isMouse) {
        this.targetZoom = Math.max(this.targetZoom / 1.2f, 0.5f);
        if (!isMouse) clampOffsets();
    }

    private void clampOffsets() {
        float maxOffsetX = Math.max((this.imageWidth * this.targetZoom - this.width) / (2 * this.targetZoom), 0);
        float maxOffsetY = Math.max((this.imageHeight * this.targetZoom - this.height) / (2 * this.targetZoom), 0);

        this.targetOffsetX = MathHelper.clamp(this.targetOffsetX, -maxOffsetX, maxOffsetX);
        this.targetOffsetY = MathHelper.clamp(this.targetOffsetY, -maxOffsetY, maxOffsetY);
    }

    /**
     * Même logique que l'icône du joueur avec une map vanilla
     * @param yaw Le Yaw du joueur
     */
    private float getVanillaMapRotation(float yaw) {
        double adjustedYaw = yaw < 0.0 ? yaw - 8.0 : yaw + 8.0;
        int segment = (int) (adjustedYaw * 16.0 / 360.0) % 16;
        return segment * 22.5f;
    }

}