package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xyz.pixelatedw.mineminenomi.api.ILocalLightningBoltEntity;

@Mixin({LightningBolt.class})
public abstract class LightningBoltMixin implements ILocalLightningBoltEntity {
   private boolean isLocal;

   public void setLocal() {
      this.isLocal = true;
   }

   public boolean isLocal() {
      return this.isLocal;
   }

   @ModifyArg(
      method = {"tick"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"
),
      index = 6
   )
   private float mineminenomi$setVolume(float volume) {
      return this.isLocal && volume == 10000.0F ? 5.0F : volume;
   }
}
