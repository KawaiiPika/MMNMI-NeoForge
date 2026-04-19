package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.PoisonImmunityAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class DokuPoisonEffect extends DamageOverTimeEffect implements ITransmisibleByTouchEffect {
   public DokuPoisonEffect() {
      super(WyHelper.hexToRGB("#802460").getRGB(), (entity) -> ModDamageSources.getInstance().poison(), 10.0F, 20);
      this.setDamageScaling((a) -> this.getBaseDamage() + (float)a);
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      boolean isDokuUser = AbilityCapability.get(entity).map((props) -> (PoisonImmunityAbility)props.getEquippedAbility((AbilityCore)PoisonImmunityAbility.INSTANCE.get())).isPresent();
      if (!isDokuUser) {
         super.m_6742_(entity, amplifier);
      }

   }

   public boolean isTransmisibleByTouch() {
      return true;
   }

   public boolean isLingering() {
      return true;
   }
}
