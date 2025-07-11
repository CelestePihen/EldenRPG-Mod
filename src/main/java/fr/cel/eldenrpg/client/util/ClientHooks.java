package fr.cel.eldenrpg.client.util;

import fr.cel.eldenrpg.client.screen.grace.GraceScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public final class ClientHooks {

    public static void openGraceScreen(Text text) {
        MinecraftClient.getInstance().setScreen(new GraceScreen(text));
    }

}