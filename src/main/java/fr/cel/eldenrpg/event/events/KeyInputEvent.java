package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.client.screen.map.MapScreen;
import fr.cel.eldenrpg.networking.packets.animations.WaveC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packets.roll.RollC2SPacket;
import fr.cel.eldenrpg.util.IKeyboard;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputEvent {

    public static final String KEY_CATEGORY_ELDENRPG = "key.category.eldenrpg.eldenrpg";

    public static final String KEY_OPEN_MAP = "key.eldenrpg.open_map";
    public static final String KEY_DRINK_FLASK = "key.eldenrpg.drink_flask";
    public static final String KEY_ROLL = "key.eldenrpg.roll";
    public static final String KEY_WAVE = "key.eldenrpg.wave";

    public static KeyBinding OPEN_MAP;
    public static KeyBinding DRINK_FLASK;
    public static KeyBinding ROLL;
    public static KeyBinding WAVE;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_MAP.wasPressed()) {
                client.setScreen(new MapScreen());
            }

            if (DRINK_FLASK.wasPressed()) {
                ClientPlayNetworking.send(new DrinkFlaskC2SPacket());
            }

            if (ROLL.wasPressed()) {
                if (client.player != null) {
                    if (client.player.isInFluid() || client.player.isSpectator() || !client.player.isOnGround() || client.player.isClimbing()) return;
                    IKeyboard keyboard = (IKeyboard) client.player.input;
                    keyboard.eldenrpg$setBlocked(true);

                    float forward = client.player.input.movementForward;
                    float sideways = client.player.input.movementSideways;
                    ClientPlayNetworking.send(new RollC2SPacket(forward, sideways));
                }
            }

            if (WAVE.wasPressed()) {
                ClientPlayNetworking.send(new WaveC2SPacket());
            }
        });
    }

    public static void register() {
        OPEN_MAP = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_MAP,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                KEY_CATEGORY_ELDENRPG
        ));

        DRINK_FLASK = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK_FLASK,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_ELDENRPG
        ));

        ROLL = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ROLL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KEY_CATEGORY_ELDENRPG
        ));

        WAVE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_WAVE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_ELDENRPG
        ));

        registerKeyInputs();
    }

}