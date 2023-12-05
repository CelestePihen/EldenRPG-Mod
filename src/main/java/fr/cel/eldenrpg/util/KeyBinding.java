package fr.cel.eldenrpg.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_ELDENRPG = "key.category.eldenrpg.eldenrpg";

    public static final String KEY_OPEN_MAP = "key.eldenrpg.open_map";
    public static final String KEY_DRINK_FLASK = "key.eldenrpg.drink_flask";
    public static final String KEY_SLOTS = "key.eldenrpg.slots";

    public static final KeyMapping OPEN_MAP = new KeyMapping(
            KEY_OPEN_MAP, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, KEY_CATEGORY_ELDENRPG);

    public static final KeyMapping DRINK_FLASK = new KeyMapping(
            KEY_DRINK_FLASK, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, KEY_CATEGORY_ELDENRPG);

    public static final KeyMapping SLOTS = new KeyMapping(
            KEY_SLOTS, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY_ELDENRPG);

}