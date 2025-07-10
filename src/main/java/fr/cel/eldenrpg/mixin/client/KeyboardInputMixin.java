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

    // Flags individuels
    @Unique private boolean blockForward = false;
    @Unique private boolean blockBackward = false;
    @Unique private boolean blockLeft = false;
    @Unique private boolean blockRight = false;
    @Unique private boolean blockJump = false;
    @Unique private boolean blockSneak = false;
    @Unique private boolean blockSprint = false;

    @Unique private boolean forward;
    @Unique private boolean backward;
    @Unique private boolean left;
    @Unique private boolean right;
    @Unique private boolean jump;
    @Unique private boolean sneak;
    @Unique private boolean sprint;

    @Unique private Vec2f movementVec;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        ci.cancel();
        if (isBlocked()) {
            this.playerInput = new PlayerInput(forward, backward, left, right, jump, sneak, sprint);
            this.movementVector = movementVec.normalize();
            return;
        }

        this.forward = blockForward ? false : this.settings.forwardKey.isPressed();
        this.backward = blockBackward ? false : this.settings.backKey.isPressed();
        this.left = blockLeft ? false : this.settings.leftKey.isPressed();
        this.right = blockRight ? false : this.settings.rightKey.isPressed();
        this.jump = blockJump ? false : this.settings.jumpKey.isPressed();
        this.sneak = blockSneak ? false : this.settings.sneakKey.isPressed();
        this.sprint = blockSprint ? false : this.settings.sprintKey.isPressed();

        this.playerInput = new PlayerInput(forward, backward, left, right, jump, sneak, sprint);

        float x = getMovementMultiplier(this.playerInput.left(), this.playerInput.right());
        float z = getMovementMultiplier(this.playerInput.forward(), this.playerInput.backward());
        this.movementVector = new Vec2f(x, z).normalize();
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
    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public void setBlockedAll(boolean blocked) {
        this.blocked = blocked;

        this.blockForward = blocked;
        this.blockBackward = blocked;
        this.blockLeft = blocked;
        this.blockRight = blocked;
        this.blockJump = blocked;
        this.blockSneak = blocked;
        this.blockSprint = blocked;
        if (blocked) this.movementVec = new Vec2f(0, 0);
    }

    @Override
    public void setBlockForward(boolean block) {
        this.blockForward = block;
    }

    @Override
    public boolean isBlockForward() {
        return this.blockForward;
    }

    @Override
    public void setBlockBackward(boolean block) {
        this.blockBackward = block;
    }

    @Override
    public boolean isBlockBackward() {
        return this.blockBackward;
    }

    @Override
    public void setBlockLeft(boolean block) {
        this.blockLeft = block;
    }

    @Override
    public boolean isBlockLeft() {
        return this.blockLeft;
    }

    @Override
    public void setBlockRight(boolean block) {
        this.blockRight = block;
    }

    @Override
    public boolean isBlockRight() {
        return this.blockRight;
    }

    @Override
    public void setBlockJump(boolean block) {
        this.blockJump = block;
    }

    @Override
    public boolean isBlockJump() {
        return this.blockJump;
    }

    @Override
    public void setBlockSneak(boolean block) {
        this.blockSneak = block;
    }

    @Override
    public boolean isBlockSneak() {
        return this.blockSneak;
    }

    @Override
    public void setBlockSprint(boolean block) {
        this.blockSprint = block;
    }

    @Override
    public boolean isBlockSprint() {
        return this.blockSprint;
    }

    @Override
    public void setMovementVec(Vec2f movementVec) {
        this.movementVec = movementVec;
    }

}