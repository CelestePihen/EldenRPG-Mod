package fr.cel.eldenrpg.client.gui.screen;

import fr.cel.eldenrpg.networking.packets.graces.screen.OpenChestC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class GraceScreen extends Screen {

    public GraceScreen(Text text) {
        super(text);
    }

    @Override
    protected void init() {
        super.init();

        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        // TODO
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("Augmenter sa vie"), (button) -> {
            player.sendMessage(Text.literal("Menu pour Augmenter sa vie"));
        }).dimensions(this.width / 2 - 75, this.height / 2 - 56, 150, 20).build());

        // TODO
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("Fioles"), (button) -> {
            player.sendMessage(Text.literal("Menu pour gérer les Fioles"));
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
        context.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, 8, 16777215);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}