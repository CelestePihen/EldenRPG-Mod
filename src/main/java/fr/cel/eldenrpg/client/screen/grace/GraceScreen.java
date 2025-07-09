package fr.cel.eldenrpg.client.screen.grace;

import fr.cel.eldenrpg.networking.packets.graces.screen.OpenChestC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class GraceScreen extends Screen {

    public GraceScreen(Text text) {
        super(text);
    }

    @Override
    protected void init() {
        super.init();

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        // TODO à faire
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.graces.screen.increasehealth"), (button) -> {

        }).dimensions(this.width / 2 - 75, this.height / 2 - 56, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.graces.screen.flasks"), (button) -> {
            client.setScreen(new FlasksScreen(this.title, this));
        }).dimensions(this.width / 2 - 75, this.height / 2 - 26, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("container.chest"), (button) -> {
            ClientPlayNetworking.send(new OpenChestC2SPacket());
        }).dimensions(this.width / 2 - 75, this.height / 2 + 4, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.graces.screen.leave"), (button) -> close())
                .dimensions(this.width / 2 - 75, this.height / 2 + 34, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, 15, 16777215);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}