package fr.cel.eldenrpg.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.cel.eldenrpg.EldenRPG;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import fr.cel.eldenrpg.networking.packets.npc.NPCDataC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class NPCScreen extends Screen {

    private static final Identifier GUI = Identifier.of(EldenRPG.MOD_ID, "textures/gui/npc.png");

    private final MinecraftClient minecraft = MinecraftClient.getInstance();

    private final EldenNPC npc;
    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private ButtonWidget doneButton;
    private TextFieldWidget editName;
    private CheckboxWidget isBaby;

    public NPCScreen(EldenNPC npc) {
        super(Text.translatable("eldenrpg.npc.screen.title"));

        this.imageWidth = 256;
        this.imageHeight = 256;

        this.npc = npc;
    }

    @Override
    protected void init() {
        super.init();

        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;

        editName = new TextFieldWidget(minecraft.textRenderer, leftPos + 80, topPos + 35, 100, 20, Text.translatable("eldenrpg.npc.screen.editName"));
        editName.setMaxLength(15);
        editName.setText(npc.getCustomName().getString());
        addSelectableChild(editName);

        isBaby = CheckboxWidget.builder(Text.empty(), minecraft.textRenderer).pos(leftPos + 60, topPos + 62).maxWidth(20).checked(npc.isBaby()).build();
        addSelectableChild(isBaby);

        doneButton = ButtonWidget.builder(ScreenTexts.DONE, (button) -> onDone()).dimensions(width / 2 - 40, height / 2 + 60, 80, 20).build();
        addSelectableChild(doneButton);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String nameEditText = editName.getText();
        this.init(client, width, height);
        editName.setText(nameEditText);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTick) {
        super.render(context, mouseX, mouseY, partialTick);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        context.drawTexture(GUI, leftPos, topPos + 25, 0, 0, imageWidth, imageHeight);

        context.drawText(minecraft.textRenderer, Text.translatable("eldenrpg.npc.screen.editName"), leftPos + 10, topPos + 40, Color.ORANGE.getRGB(), true);
        context.drawText(minecraft.textRenderer,  Text.translatable("eldenrpg.npc.screen.isBaby"), leftPos + 10, topPos + 65, Color.ORANGE.getRGB(), true);

        editName.render(context, mouseX, mouseY, partialTick);
        isBaby.render(context, mouseX, mouseY, partialTick);
        doneButton.render(context, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void onDone() {
        ClientPlayNetworking.send(new NPCDataC2SPacket(npc.getId(), editName.getText(), isBaby.isChecked()));
        MinecraftClient.getInstance().setScreen(null);
    }

}