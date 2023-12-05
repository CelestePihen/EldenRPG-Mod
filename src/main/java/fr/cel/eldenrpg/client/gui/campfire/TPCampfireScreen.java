package fr.cel.eldenrpg.client.gui.campfire;

import fr.cel.eldenrpg.client.gui.MapScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class TPCampfireScreen extends Screen {

    private CampfiresSelectionList campfiresSelectionList;

    public TPCampfireScreen() {
        super(Component.translatable("eldenrpg.map.screen.tpcampfires"));
    }

    @Override
    public void init() {
        super.init();

        this.campfiresSelectionList = new CampfiresSelectionList(this.minecraft, this);
        this.addWidget(campfiresSelectionList);

        this.addRenderableWidget(Button.builder(Component.translatable("gui.back"),
                pButton -> getMinecraft().setScreen(new MapScreen())).bounds(this.width / 2 - 80, this.height - 38, 150, 20).build());

        this.addRenderableWidget(new StringWidget(0, 11, this.width, 9, this.title, this.font));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.campfiresSelectionList.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

}
