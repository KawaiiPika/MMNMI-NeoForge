package xyz.pixelatedw.mineminenomi.api.events;

import javax.annotation.Nonnull;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;

public class MobEffectAfterAddedEvent extends MobEffectEvent {
   public MobEffectAfterAddedEvent(LivingEntity living, MobEffectInstance newEffect) {
      super(living, newEffect);
   }

   @Nonnull
   public MobEffectInstance getEffectInstance() {
      return super.getEffectInstance();
   }
}
