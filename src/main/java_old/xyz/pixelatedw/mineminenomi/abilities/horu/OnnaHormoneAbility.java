package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class OnnaHormoneAbility extends PunchAbility {
   private static final int COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<OnnaHormoneAbility>> INSTANCE = ModRegistry.registerAbility("onna_hormone", "Onna Hormone", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By injecting an enemy with special hormones, the user can inflict moderate debuffs on their enemies", (Object)null), ImmutablePair.of("§aSHIFT-USE§r: User injects themselves", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, OnnaHormoneAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ContinuousComponent.getTooltip()).setSourceType(SourceType.FIST, SourceType.FRIENDLY).build("mineminenomi");
   });

   public OnnaHormoneAbility(AbilityCore<OnnaHormoneAbility> core) {
      super(core);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6047_()) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 300, 2));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 300, 1));
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 300, 0));
         this.continuousComponent.stopContinuity(entity);
      }

   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 300, 2));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 300, 1));
      target.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 300, 0));
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 400.0F;
   }
}
