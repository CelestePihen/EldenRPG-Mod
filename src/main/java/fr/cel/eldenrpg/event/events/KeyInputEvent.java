package fr.cel.eldenrpg.event.events;

import fr.cel.eldenrpg.client.screen.map.MapScreen;
import fr.cel.eldenrpg.networking.packets.animations.WaveC2SPacket;
import fr.cel.eldenrpg.networking.packets.flasks.DrinkFlaskC2SPacket;
import fr.cel.eldenrpg.networking.packets.roll.RollC2SPacket;
import fr.cel.eldenrpg.util.IKeyboard;
import fr.cel.eldenrpg.util.IPlayerDataSaver;
import fr.cel.eldenrpg.util.data.FlasksData;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec2f;
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
                if (client.player != null) {
                    IPlayerDataSaver playerData = (IPlayerDataSaver) client.player;
                    if (FlasksData.getFlasks(playerData) <= 0) return;
                    // TODO faire en sorte qu'on peut pas ROLL
                    if (client.player.isCreative() || client.player.isSpectator() || !client.player.isOnGround()) return;
                    IKeyboard keyboard = (IKeyboard) client.player.input;
                    keyboard.setBlockJump(true);
                    keyboard.setBlockSneak(true);
                    keyboard.setBlockSprint(true);
                    keyboard.setMovementVec(new Vec2f(0, 0));

                    ClientPlayNetworking.send(new DrinkFlaskC2SPacket());
                }
            }

            if (ROLL.wasPressed()) {
                if (client.player != null) {
                    // TODO faire en sorte qu'on peut pas DRINK_FLASK
                    if (client.player.isInFluid() || client.player.isSpectator() || !client.player.isOnGround() || client.player.isClimbing()) return;
                    IKeyboard keyboard = (IKeyboard) client.player.input;
                    keyboard.setBlockedAll(true);

                    float forward = client.player.input.getMovementInput().y;
                    float sideways = client.player.input.getMovementInput().x;
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