package fr.cel.eldenrpg.client.utils;

import fr.cel.eldenrpg.client.gui.NPCScreen;
import fr.cel.eldenrpg.entity.EldenNPC;
import net.minecraft.client.Minecraft;

public class ClientHooks {

    public static void openNPCScreen(EldenNPC npc) {
        Minecraft.getInstance().setScreen(new NPCScreen(npc));
    }

}
