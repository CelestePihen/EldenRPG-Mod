package fr.cel.eldenrpg.mixin.client;

import fr.cel.eldenrpg.EldenRPG;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
	private void changeWindowTitle(CallbackInfoReturnable<String> cir) {
		if (EldenRPG.isIDE) {
			cir.setReturnValue("MC Dev 1.21 - EldenRPG 0.1");
		}
		cir.setReturnValue("Minecraft 1.21 - EldenRPG 0.1");
	}

}