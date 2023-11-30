package fr.cel.eldenrpg.client.gui;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.MapTeleportationC2SPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.Color;

public class MapScreen extends Screen {

    public static final ResourceLocation CARTE_DEV = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/gui/carte_dev.png");

    private final int imageWidth, imageHeight;

    private boolean isActive = true;

    public MapScreen() {
        super(Component.translatable("eldenrpg.map.screen.title"));

        this.imageWidth = 339;
        this.imageHeight = 335;
    }

    @Override
    protected void init() {
        super.init();

        int middle_width = this.width / 2;
        int middle_height = this.height / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Activer/Désactiver l'image de la map"), action -> isActive = !isActive).build());

        tpButton("Zone 1", 2.5, 138, 5.5, middle_width - 50, middle_height, 120, 80);
        tpButton("Zone 2", 2.5, 138, -6.5, middle_width - 50, middle_height - 120, 105, 100);
        tpButton("Zone 3", 9.5, 138, -6.5, middle_width + 55, middle_height - 100, 110, 100);
        tpButton("Zone 4", -10.5, 138, -6.5, middle_width - 115, middle_height - 150, 65, 125);
        tpButton("Zone 5", -15.5, 138, -6.5, middle_width - 170, middle_height - 150, 55, 125);
        tpButton("Zone 6", -10.5, 138, 5.5, middle_width - 170, middle_height - 20, 120, 80);
        tpButton("Zone 7", 3.5, 138, 17.5, middle_width - 170, middle_height + 60, 120, 85);
        tpButton("§cZone 8", 9.5, 138, 17.5, middle_width + 90, middle_height + 60, 65, 106);
        tpButton("§cZone 9", 2.5, 138, 17.5, middle_width - 30, middle_height + 80, 120, 86);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;

        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (isActive) {
            pGuiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        }

        pGuiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, Color.WHITE.getRGB());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void tpButton(String text, double x, double y, double z, int pX, int pY, int width, int height) {
        this.addRenderableWidget(Button.builder(Component.literal(text), (action) -> {
            ModMessages.sendToServer(new MapTeleportationC2SPacket(x, y, z));
        }).bounds(pX, pY, width, height).build());
    }

}