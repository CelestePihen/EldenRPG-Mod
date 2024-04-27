package fr.cel.eldenrpg.client.gui;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.client.gui.bonfires.BonfiresSelectionList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MapScreen extends Screen {

    public static final ResourceLocation CARTE_DEV = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/gui/carte_dev.png");

    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    private BonfiresSelectionList bonfiresSelectionList;

    public MapScreen() {
        super(Component.translatable("eldenrpg.map.screen.title"));

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        this.bonfiresSelectionList = new BonfiresSelectionList(minecraft, this);
        this.addWidget(bonfiresSelectionList);

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        this.bonfiresSelectionList.render(guiGraphics, mouseX, mouseY, partialTick);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(CARTE_DEV, leftPos + 100, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        // TODO mettre un zoom comme ça plus de problèmes avec GUI SCALE (en vrai je sais pas si ça va vraiment marcher mais bon autant essayer)
        // TODO avoir la motiv de le faire un jour
//        if (ClientMapsData.getPlayerMaps().contains(0)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(1)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(2)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(3)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(4)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(5)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(6)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(7)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }
//
//        if (ClientMapsData.getPlayerMaps().contains(8)) {
//            guiGraphics.blit(CARTE_DEV, leftPos, topPos, 0, 0, 10, 10, imageWidth, imageHeight);
//        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}