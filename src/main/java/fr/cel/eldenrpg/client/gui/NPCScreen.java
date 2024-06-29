package fr.cel.eldenrpg.client.gui;

import fr.cel.eldenrpg.EldenRPGMod;
import fr.cel.eldenrpg.entity.EldenNPC;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.npc.NPCDataC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class NPCScreen extends Screen {

    private static final ResourceLocation GUI = new ResourceLocation(EldenRPGMod.MOD_ID, "textures/gui/npc.png");

    private final EldenNPC npc;
    private final int imageWidth, imageHeight;

    private int leftPos, topPos;
    private EditBox nameEdit;
    private Checkbox isBaby;

    public NPCScreen(EldenNPC npc) {
        super(Component.literal("NPC's Editor"));

        this.npc = npc;

        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> this.onDone())
                .bounds(this.width / 2 - 40, this.height / 2 + 60, 80, 20).build());

        // TODO translatable
        nameEdit = new EditBox(this.font, this.leftPos + 80, this.topPos + 40, 100, 10, Component.literal("NPC's name"));
        nameEdit.setMaxLength(15);
        nameEdit.setValue(npc.getCustomName().getString());
        this.addRenderableWidget(nameEdit);

        isBaby = new Checkbox(this.leftPos + 60, this.topPos + 60, 20, 20, Component.empty(), npc.isBaby());
        this.addRenderableWidget(isBaby);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String s = this.nameEdit.getValue();
        this.init(minecraft, width, height);
        this.nameEdit.setValue(s);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        guiGraphics.blit(GUI, this.leftPos, this.topPos + 25, 0, 0, this.imageWidth, this.imageHeight);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // TODO translatable
        guiGraphics.drawString(this.font, "NPC's name: ", this.leftPos + 10, this.topPos + 40, Color.ORANGE.getRGB());
        guiGraphics.drawString(this.font, "Is baby: ", this.leftPos + 10, this.topPos + 65, Color.ORANGE.getRGB());
    }

    @Override
    public void tick() {
        this.nameEdit.tick();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void onDone() {
        ModMessages.sendToServer(new NPCDataC2SPacket(npc.getId(), Component.literal(this.nameEdit.getValue()), isBaby.selected()));
        if (minecraft != null) this.minecraft.setScreen(null);
    }

}