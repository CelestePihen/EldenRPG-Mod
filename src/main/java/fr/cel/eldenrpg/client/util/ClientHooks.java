package fr.cel.eldenrpg.client.util;

import fr.cel.eldenrpg.client.gui.screen.NPCScreen;
import fr.cel.eldenrpg.entity.custom.EldenNPC;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ClientHooks {

    public static void openNPCScreen(EldenNPC npc) {
        MinecraftClient.getInstance().setScreen(new NPCScreen(npc));
    }

}
