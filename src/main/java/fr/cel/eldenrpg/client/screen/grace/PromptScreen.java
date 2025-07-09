package fr.cel.eldenrpg.client.screen.grace;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class PromptScreen extends Screen {

    private final Screen parent;

    /* TODO: choix "oui ou non" avec affichage de la question / message */
    public PromptScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        this.addDrawableChild(ButtonWidget.builder(Text.literal("OK"), (button) -> close())
                .dimensions(this.width / 2 - 75, this.height / 2 - 10, 150, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, this.height / 2 - 30, 16777215);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

}