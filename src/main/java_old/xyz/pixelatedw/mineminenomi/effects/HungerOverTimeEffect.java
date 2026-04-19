package xyz.pixelatedw.mineminenomi.effects;

import java.util.function.Function;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class HungerOverTimeEffect extends BaseEffect {
   private final float baseHunger;
   private final int freq;
   private Function<Integer, Float> hungerScaling;

   public HungerOverTimeEffect() {
      this(2.0F, 20);
   }

   public HungerOverTimeEffect(float baseDamage, int freq) {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#813d2d").getRGB());
      this.baseHunger = baseDamage;
      this.freq = freq;
      this.hungerScaling = (amp) -> this.baseHunger * (float)amp;
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % this.freq == 0;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (entity instanceof Player player) {
         float damage = Math.max(this.baseHunger, (Float)this.hungerScaling.apply(amplifier));
         int foodLevel = (int)((float)player.m_36324_().m_38702_() - damage);
         foodLevel = Math.max(foodLevel, 0);
         player.m_36324_().m_38705_(foodLevel);
      }

   }

   public float getBaseHunger() {
      return this.baseHunger;
   }

   public void setHungerScaling(Function<Integer, Float> func) {
      this.hungerScaling = func;
   }

   public boolean isLingering() {
      return true;
   }
}
