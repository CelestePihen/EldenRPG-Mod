package fr.cel.eldenrpg.mixin.client;

import fr.cel.eldenrpg.util.IKeyboard;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
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
    private void tick(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        ci.cancel();
        if (eldenrpg$isBlocked()) {
            this.pressingForward = false;
            this.pressingBack = false;
            this.pressingLeft = false;
            this.pressingRight = false;
            this.movementForward = 0;
            this.movementSideways = 0;
            this.jumping = false;
            this.sneaking = false;
            return;
        }

        this.pressingForward = this.settings.forwardKey.isPressed();
        this.pressingBack = this.settings.backKey.isPressed();
        this.pressingLeft = this.settings.leftKey.isPressed();
        this.pressingRight = this.settings.rightKey.isPressed();
        this.movementForward = getMovementMultiplier(this.pressingForward, this.pressingBack);
        this.movementSideways = getMovementMultiplier(this.pressingLeft, this.pressingRight);
        this.jumping = this.settings.jumpKey.isPressed();
        this.sneaking = this.settings.sneakKey.isPressed();
        if (slowDown) {
            this.movementSideways *= slowDownFactor;
            this.movementForward *= slowDownFactor;
        }
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
