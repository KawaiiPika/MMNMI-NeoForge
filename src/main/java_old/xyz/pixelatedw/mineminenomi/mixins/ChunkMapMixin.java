package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

@Mixin({ChunkMap.class})
public class ChunkMapMixin {
   @Shadow
   @Final
   private ServerLevel f_140133_;

   @Inject(
      method = {"save"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void mineminenomi$save(ChunkAccess access, CallbackInfoReturnable<Boolean> cir) {
      if (NuWorld.isChallengeDimension(this.f_140133_)) {
         cir.setReturnValue(false);
      }

   }
}
