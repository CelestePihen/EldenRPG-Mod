package fr.cel.eldenrpg.mixin.client;

import fr.cel.eldenrpg.util.IKeyboard;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input implements IKeyboard {

    @Shadow @Final private GameOptions settings;
    @Unique private boolean blocked = false;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        ci.cancel();
        if (eldenrpg$isBlocked()) {
            this.playerInput = new PlayerInput(false, false, false, false, false, false, false);
            this.movementVector = new Vec2f(0, 0).normalize();
            return;
        }

        this.playerInput = new PlayerInput(
                this.settings.forwardKey.isPressed(),
                this.settings.backKey.isPressed(),
                this.settings.leftKey.isPressed(),
                this.settings.rightKey.isPressed(),
                this.settings.jumpKey.isPressed(),
                this.settings.sneakKey.isPressed(),
                this.settings.sprintKey.isPressed()
        );
        float f = getMovementMultiplier(this.playerInput.forward(), this.playerInput.backward());
        float g = getMovementMultiplier(this.playerInput.left(), this.playerInput.right());
        this.movementVector = new Vec2f(g, f).normalize();
    }

    @Shadow
    private static float getMovementMultiplier(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0F;
        } else {
            return positive ? 1.0F : -1.0F;
        }
    }

    @Override
    public boolean eldenrpg$isBlocked() {
        return blocked;
    }

    @Override
    public void eldenrpg$setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

}