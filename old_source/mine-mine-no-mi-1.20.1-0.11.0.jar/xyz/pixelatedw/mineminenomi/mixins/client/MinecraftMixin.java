package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.ModMain;

@Mixin({Minecraft.class})
public abstract class MinecraftMixin {
   @Shadow
   private boolean f_91012_;

   @Inject(
      method = {"runTick"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Minecraft;pause:Z",
   opcode = 181,
   shift = Shift.AFTER
)}
   )
   private void mineminenomi$runTickReturn(boolean pRenderLevel, CallbackInfo ci) {
      if (this.f_91012_) {
         ModMain.PAUSABLE_TIMER.pause();
      } else {
         ModMain.PAUSABLE_TIMER.start();
      }

   }
}
