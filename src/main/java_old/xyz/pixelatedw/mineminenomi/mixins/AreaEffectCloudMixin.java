package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.effects.ILingeringEffect;

@Mixin({AreaEffectCloud.class})
public class AreaEffectCloudMixin {
   @Inject(
      method = {"addEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void addEffect(MobEffectInstance effect, CallbackInfo ci) {
      if (effect.m_19544_() instanceof ILingeringEffect && !((ILingeringEffect)effect.m_19544_()).isLingering()) {
         ci.cancel();
      }

   }
}
