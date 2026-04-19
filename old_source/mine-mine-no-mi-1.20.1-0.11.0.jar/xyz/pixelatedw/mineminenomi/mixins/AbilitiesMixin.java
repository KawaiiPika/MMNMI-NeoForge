package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.IPlayerAbilities;

@Mixin({Abilities.class})
public class AbilitiesMixin implements IPlayerAbilities {
   private boolean hasCustomFlight = false;

   public boolean hasCustomFlight() {
      return this.hasCustomFlight;
   }

   public void setCustomFlight(boolean hasCustomFlight) {
      this.hasCustomFlight = hasCustomFlight;
   }

   @Inject(
      method = {"getFlyingSpeed()F"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void getFlyingSpeed(CallbackInfoReturnable<Float> cir) {
      if (this.hasCustomFlight) {
         cir.setReturnValue(0.0F);
      }

   }
}
