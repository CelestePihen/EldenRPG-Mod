package fr.cel.eldenrpg.client.screen.map;

import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.GracesData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.math.BlockPos;

public class GracesSelectionScreen extends Screen {
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private final Screen parent;

    public GracesSelectionScreen(Screen parent) {
        super(Text.translatable("eldenrpg.gracesselection.screen.title"));
        this.parent = parent;
        this.layout.setFooterHeight(53);
    }

    @Override
    protected void init() {
        super.init();

        if (client == null) return;

        this.layout.addHeader(this.title, this.textRenderer);
        GracesSelectionListWidget gracesSelectionList = this.layout.addBody(new GracesSelectionListWidget(this.client));
        this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, button -> client.setScreen(parent)).width(200).build());

        this.layout.forEachChild(this::addDrawableChild);

        this.layout.refreshPositions();
        gracesSelectionList.position(this.width, this.layout);
    }

    private class GracesSelectionListWidget extends AlwaysSelectedEntryListWidget<GracesSelectionListWidget.GraceEntry> {
        public GracesSelectionListWidget(final MinecraftClient minecraftClient) {
            super(minecraftClient, GracesSelectionScreen.this.width, GracesSelectionScreen.this.height  - 33 - 53, 33, 18);

            if (minecraftClient.player == null) return;

            GracesData.getPlayerGraces(((IPlayerDataSaver) minecraftClient.player)).forEach(pos -> {
                Text text = GracesData.getGraceName(BlockPos.fromLong(pos));
                GraceEntry entry = new GraceEntry(BlockPos.fromLong(pos), text, minecraftClient);
                this.addEntry(entry);
            });

            if (this.getSelectedOrNull() != null) {
                this.centerScrollOn(this.getSelectedOrNull());
            }
        }

        @Override
        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        public class GraceEntry extends Entry<GraceEntry> {
            private final BlockPos pos;
            private final Text graceName;
            private final MinecraftClient client;

            public GraceEntry(final BlockPos pos, final Text graceName, final MinecraftClient client) {
                this.pos = pos;
                this.graceName = graceName;
                this.client = client;
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                context.drawCenteredTextWithShadow(GracesSelectionScreen.this.textRenderer, this.graceName, GracesSelectionScreen.this.width / 2, y + entryHeight / 2 - 9 / 2, Colors.WHITE);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                client.setScreen(new LoadingScreen(pos.north()));
                return true;
            }

            @Override
            public Text getNarration() {
                return Text.translatable("narrator.select", this.graceName);
            }
        }
    }

}