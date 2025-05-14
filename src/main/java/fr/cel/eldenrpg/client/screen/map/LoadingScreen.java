package fr.cel.eldenrpg.client.screen.map;

import fr.cel.eldenrpg.networking.packets.graces.MapTeleportationC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class LoadingScreen extends Screen {

    private final BlockPos targetPos;

    private long startTime;
    private float fadeProgress = 0.0f;
    private boolean fadeOut = false;
    private boolean waitingForInput = false;
    private boolean teleported = false;

    private static final int FADE_IN_DURATION = 2000; // millisecondes
    private static final int FADE_OUT_DURATION = 1500; // millisecondes

    public LoadingScreen(BlockPos targetPos) {
        super(Text.literal("Loading..."));
        this.targetPos = targetPos;
    }

    @Override
    protected void init() {
        super.init();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int alpha = (int) (MathHelper.clamp(fadeProgress, 0.0f, 1.0f) * 255);
        int color = (alpha << 24); // noir avec transparence

        context.fill(0, 0, width, height, color);

        if (fadeProgress > 0.9f) {
            String loadingText = fadeOut ? "" : "Appuyez pour continuer...";
            int textWidth = textRenderer.getWidth(loadingText);
            context.drawText(textRenderer, loadingText, (width - textWidth) / 2, height / 2, 0xFFFFFF, false);
        }

        // TODO ajouter conseils
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void tick() {
        if (this.client == null) return;

        long timePassed = System.currentTimeMillis() - startTime;

        if (!fadeOut) {
            fadeProgress = Math.min(1.0f, timePassed / (float) FADE_IN_DURATION);

            if (fadeProgress >= 1.0f) {
                if (!teleported) {
                    ClientPlayNetworking.send(new MapTeleportationC2SPacket(targetPos));
                    teleported = true;
                }

                waitingForInput = true;
            }
        } else {
            fadeProgress = 1.0f - Math.min(1.0f, timePassed / (float) FADE_OUT_DURATION);
            if (fadeProgress <= 0.0f) {
                client.setScreen(null);
            }
        }

        // fail-safe au cas où
        if (timePassed > 15000 && !fadeOut) {
            client.setScreen(null);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (waitingForInput) {
            startFadeOut();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (waitingForInput) {
            startFadeOut();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void startFadeOut() {
        fadeOut = true;
        waitingForInput = false;
        startTime = System.currentTimeMillis();
    }

    private boolean areChunksLoaded(BlockPos center) {
        if (this.client == null || this.client.world == null) return false;

        int radius = 2;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int chunkX = (center.getX() >> 4) + dx;
                int chunkZ = (center.getZ() >> 4) + dz;
                if (!this.client.world.isChunkLoaded(chunkX, chunkZ)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
