package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityMobEffectInstance extends MobEffectInstance {
   private final IAbility parent;

   public AbilityMobEffectInstance(IAbility parent, MobEffect effect) {
      this(parent, effect, 0, 0);
   }

   public AbilityMobEffectInstance(IAbility parent, MobEffect effect, int duration, int amp) {
      this(parent, effect, duration, amp, false, true);
   }

   public AbilityMobEffectInstance(IAbility parent, MobEffect effect, int duration, int amp, boolean isAmbient, boolean isVisible) {
      this(parent, effect, duration, amp, isAmbient, isVisible, isVisible);
   }

   public AbilityMobEffectInstance(IAbility parent, MobEffect effect, int duration, int amp, boolean isAmbient, boolean isVisible, boolean showIcon) {
      super(effect, duration, amp, isAmbient, isVisible, showIcon);
      this.parent = parent;
   }

   public IAbility getParent() {
      return this.parent;
   }
}
