package fr.cel.eldenrpg.client.gui.campfires;

import fr.cel.eldenrpg.client.data.ClientFirecampsData;
import fr.cel.eldenrpg.networking.ModMessages;
import fr.cel.eldenrpg.networking.packet.MapTeleportationC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class CampfiresSelectionList extends ObjectSelectionList<CampfiresSelectionList.Entry> {

    public CampfiresSelectionList(Minecraft pMinecraft, Screen screen) {
        super(pMinecraft, screen.width, screen.height, 32, screen.height - 65 + 4, 18);
        ClientFirecampsData.getFirecamps().forEach((blockPos, s) -> {
            CampfiresSelectionList.Entry entry = new Entry(blockPos, s, this.minecraft);
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

    public class Entry extends ObjectSelectionList.Entry<CampfiresSelectionList.Entry> {
        private final BlockPos pos;
        private final Component campfireName;
        private final Minecraft minecraft;

        public Entry(BlockPos pos, Component text, Minecraft minecraft) {
            this.pos = pos;
            this.campfireName = text;
            this.minecraft = minecraft;
        }

        public void render(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick) {
            pGuiGraphics.drawCenteredString(minecraft.font, this.campfireName, CampfiresSelectionList.this.width / 2, pTop + 1, 16777215);
        }

        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            ModMessages.sendToServer(new MapTeleportationC2SPacket(pos.getX(), pos.getY(), pos.getZ()));
            minecraft.setScreen(null);
            return true;
        }

        public Component getNarration() {
            return Component.translatable("narrator.select", this.campfireName);
        }

    }

}
