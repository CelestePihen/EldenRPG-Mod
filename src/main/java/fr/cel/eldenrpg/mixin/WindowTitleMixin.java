package fr.cel.eldenrpg.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class WindowTitleMixin {

	@Inject(method = "updateWindowTitle", at = @At("HEAD"), cancellable = true)
	private void init(CallbackInfo c) {
		c.cancel();
		MinecraftClient.getInstance().getWindow().setTitle("Minecraft 1.21 - EldenRPG 0.1");
	}

}