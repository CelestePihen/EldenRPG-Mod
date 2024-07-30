package fr.cel.eldenrpg.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

@Environment(EnvType.CLIENT)
public class RecommendationScreen extends Screen {

    private final Screen parent;

    public RecommendationScreen(Screen parent) {
        super(Text.translatable("menu.recommendation"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.BACK, (button) -> this.client.setScreen(this.parent))
                .dimensions(this.width / 2 - 36, this.height - 28, 72, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, 8, 16777215);

        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.renderdistance"), width / 2, height / 2 - 70, Colors.WHITE);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.simulationdistance"), width / 2, height / 2 - 60, Colors.WHITE);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.brightness"), width / 2, height / 2 - 50, Colors.WHITE);

        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.shader"), width / 2, height / 2 - 30, Colors.BLUE);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.shaderlightning"), width / 2, height / 2 - 20, Colors.BLUE);

        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.healthindicator"), width / 2, height / 2, Colors.GREEN);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.hiDistance"), width / 2, height / 2 + 10, Colors.GREEN);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.hiPosition"), width / 2, height / 2 + 20, Colors.GREEN);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.hiYValue"), width / 2, height / 2 + 30, Colors.GREEN);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("eldenrpg.recommendation.screen.hiScale"), width / 2, height / 2 + 40, Colors.GREEN);
    }

    @Override
    public void close() {
        client.setScreen(this.parent);
    }

    public boolean shouldPause() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

}