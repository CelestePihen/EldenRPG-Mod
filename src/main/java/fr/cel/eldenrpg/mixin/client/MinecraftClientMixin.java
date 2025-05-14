package fr.cel.eldenrpg.mixin.client;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
	private void changeWindowTitle(CallbackInfoReturnable<String> cir) {
		if (EldenRPG.isIDE) {
			cir.setReturnValue("EldenRPG 0.1 - MC Dev 1.21.1 - " + MinecraftClient.getInstance().getSession().getUsername());
		} else {
			cir.setReturnValue("EldenRPG 0.1 - MC 1.21.1 - " + MinecraftClient.getInstance().getSession().getUsername());
		}
	}

}