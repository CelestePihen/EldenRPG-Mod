package fr.cel.eldenrpg.client.screen.grace;

import fr.cel.eldenrpg.networking.packets.flasks.AddChargeC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.IncreaseFlaskC2SPacket;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.Optional;

public class FlasksScreen extends Screen {

    private final Screen parent;

    public FlasksScreen(Text text, Screen parent) {
        super(text);
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.flasks.screen.addCharge"), (button) -> convertSeedToFlask())
                .dimensions(this.width / 2 - 75, this.height / 2 - 40, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.flasks.screen.increaseAmount"), (button) -> convertTearToLevel())
                .dimensions(this.width / 2 - 120, this.height / 2 - 10, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("eldenrpg.flasks.screen.cancel"), (button) -> close())
                .dimensions(this.width / 2 - 75, this.height / 2 + 20, 150, 20).build());

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

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

    private void convertSeedToFlask() {
        if (client == null) return;
        if (client.player == null) return;

        IPlayerDataSaver playerData = (IPlayerDataSaver) client.player;

        Optional<Integer> goldenSeeds = playerData.getPersistentData().getInt("goldenSeed");
        if (goldenSeeds.isPresent() && goldenSeeds.get() == 0) {
            client.setScreen(new PromptScreen(Text.translatable("eldenrpg.flasks.screen.noGoldenSeed"), this));
            return;
        }

        Optional<Integer> maxFlasks = playerData.getPersistentData().getInt("maxFlasks");
        int seedsRequired;
        if (maxFlasks.isPresent() && maxFlasks.get() < 6) {
            seedsRequired = 1;
        } else if (maxFlasks.isPresent() && maxFlasks.get() < 8) {
            seedsRequired = 2;
        } else if (maxFlasks.isPresent() && maxFlasks.get() < 10) {
            seedsRequired = 3;
        } else if (maxFlasks.isPresent() && maxFlasks.get() < 12) {
            seedsRequired = 4;
        } else {
            seedsRequired = 5;
        }

        if (goldenSeeds.isPresent() && goldenSeeds.get() >= seedsRequired) {
            client.setScreen(new PromptScreen(Text.translatable("eldenrpg.flasks.screen.flaskIncreased"), this));
            ClientPlayNetworking.send(new IncreaseFlaskC2SPacket());
            return;
        }

        client.setScreen(new PromptScreen(Text.translatable("eldenrpg.flasks.screen.notEnoughGS"), this));
    }

    private void convertTearToLevel() {
        if (client == null) return;
        if (client.player == null) return;

        IPlayerDataSaver playerData = (IPlayerDataSaver) client.player;
        Optional<Integer> sacredTear = playerData.getPersistentData().getInt("sacredTear");
        if (sacredTear.isPresent() && sacredTear.get() == 0) {
            client.setScreen(new PromptScreen(Text.translatable("eldenrpg.flasks.screen.noTear"), this));
            return;
        }

        client.setScreen(new PromptScreen(Text.translatable("eldenrpg.flasks.screen.chargeAdded"), this));
        ClientPlayNetworking.send(new AddChargeC2SPacket());
    }

}
