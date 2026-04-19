package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TensionHormoneAbility extends PunchAbility {
   private static final int COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<TensionHormoneAbility>> INSTANCE = ModRegistry.registerAbility("tension_hormone", "Tension Hormone", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user injects the target with special hormones providing a supply of adrenaline that strengthens them.", (Object)null), ImmutablePair.of("§aSHIFT-USE§r: User injects themselves", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TensionHormoneAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ContinuousComponent.getTooltip()).setSourceType(SourceType.FIST, SourceType.FRIENDLY).build("mineminenomi");
   });

   public TensionHormoneAbility(AbilityCore<TensionHormoneAbility> core) {
      super(core);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6047_()) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.TENSION_HORMONE.get(), 200, 1));
         this.cooldownComponent.startCooldown(entity, 400.0F);
      }

   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.TENSION_HORMONE.get(), 200, 1));
      return true;
   }

   public int getUseLimit() {
      return 1;
   }

   public float getPunchCooldown() {
      return 400.0F;
   }
}
