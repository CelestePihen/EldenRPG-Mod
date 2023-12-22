package fr.cel.eldenrpg.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class WindowTitleMixin {

    @Inject(method = "updateTitle()V", at = @At("HEAD"), cancellable = true)
    private void updateTitle(CallbackInfo c) {
        c.cancel();
        Minecraft.getInstance().getWindow().setTitle("Minecraft 1.20.1 - EldenRPG 0.1");
    }

}
