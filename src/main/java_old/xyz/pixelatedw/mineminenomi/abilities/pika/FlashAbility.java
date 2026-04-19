package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FlashAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int CHARGE_TIME = 40;
   private static final int MIN_RANGE = 2;
   private static final int MAX_RANGE = 16;
   public static final RegistryObject<AbilityCore<FlashAbility>> INSTANCE = ModRegistry.registerAbility("flash", "Flash", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates a bright flash of light, blinding their opponents. Longer charges means a longer distance.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, FlashAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(40.0F), RangeComponent.getTooltip(2.0F, 16.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.LIGHT).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> (double)comp.getChargePercentage() > 0.2)).addTickEvent(this::duringChargeEvent).addEndEvent(this::stopChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public FlashAbility(AbilityCore<FlashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity player, IAbility ability) {
      this.chargeComponent.startCharging(player, 40.0F);
   }

   private void duringChargeEvent(LivingEntity player, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.PIKA_CHARGING.get(), player, player.m_20185_(), player.m_20186_(), player.m_20189_());
   }

   private void stopChargeEvent(LivingEntity player, IAbility ability) {
      AbilityHelper.reduceEffect(player.m_21124_((MobEffect)ModEffects.FROZEN.get()), (double)10.0F);
      AbilityHelper.reduceEffect(player.m_21124_((MobEffect)ModEffects.FROSTBITE.get()), (double)10.0F);
      AbilityHelper.reduceEffect(player.m_21124_((MobEffect)ModEffects.CANDY_STUCK.get()), (double)10.0F);
      AbilityHelper.reduceEffect(player.m_21124_((MobEffect)ModEffects.CANDLE_LOCK.get()), (double)10.0F);
      float radius = this.chargeComponent.getChargePercentage() * 16.0F;

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(player, radius)) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 140, 3));
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FLASH.get(), player, target.m_20185_(), target.m_20186_() + (double)target.m_20192_(), target.m_20189_());
      }

      this.cooldownComponent.startCooldown(player, 200.0F);
   }
}
