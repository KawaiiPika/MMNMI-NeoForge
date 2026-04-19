package xyz.pixelatedw.mineminenomi.mixins;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

@Mixin({ServerLevel.class})
public class ServerLevelMixin {
   @Inject(
      method = {"save"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$save(@Nullable ProgressListener pProgress, boolean pFlush, boolean pSkipSave, CallbackInfo ci) {
      ServerLevel world = (ServerLevel)this;
      if (NuWorld.isChallengeDimension(world)) {
         ci.cancel();
      }

   }
}
