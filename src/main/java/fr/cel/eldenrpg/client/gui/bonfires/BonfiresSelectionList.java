package fr.cel.eldenrpg.client.gui.bonfires;

import fr.cel.eldenrpg.client.data.ClientBonfiresData;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.bonfires.MapTeleportationC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class BonfiresSelectionList extends ObjectSelectionList<BonfiresSelectionList.Entry> {

    public BonfiresSelectionList(Minecraft pMinecraft, Screen screen) {
        super(pMinecraft, screen.width, screen.height, 32, screen.height - 65 + 4, 18);
        ClientBonfiresData.getBonfires().forEach((blockPos, s) -> {
            BonfiresSelectionList.Entry entry = new Entry(blockPos, s, this.minecraft);
            this.addEntry(entry);
        });
        this.centerListVertically = false;
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 5;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 20;
    }

    public class Entry extends ObjectSelectionList.Entry<BonfiresSelectionList.Entry> {
        private final BlockPos pos;
        private final Component bonfireName;
        private final Minecraft minecraft;

        public Entry(BlockPos pos, Component text, Minecraft minecraft) {
            this.pos = pos;
            this.bonfireName = text;
            this.minecraft = minecraft;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.drawCenteredString(minecraft.font, this.bonfireName, BonfiresSelectionList.this.width / 2, pTop + 1, 16777215);
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            ModMessages.sendToServer(new MapTeleportationC2SPacket(pos.north()));
            minecraft.setScreen(null);
            return true;
        }

        @Override
        public Component getNarration() {
            return Component.translatable("narrator.select", this.bonfireName);
        }

    }

}
