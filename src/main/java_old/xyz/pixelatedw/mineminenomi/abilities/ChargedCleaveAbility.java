package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ChargedCleaveAbility extends Ability {
   private static final int CHARGE_TIME = 10;
   private static final int COOLDOWN = 100;
   private static final float DAMAGE = 20.0F;
   private static final int RANGE = 5;
   public static final RegistryObject<AbilityCore<ChargedCleaveAbility>> INSTANCE = ModRegistry.registerAbility("charged_cleave", "Charged Cleave", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ChargedCleaveAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ChargeComponent.getTooltip(10.0F), RangeComponent.getTooltip(5.0F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi"));
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addEndEvent(this::stopChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final DealDamageComponent damageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private int bleedingPower = 0;
   private int bleedingTime = 40;

   public ChargedCleaveAbility(AbilityCore<ChargedCleaveAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.damageComponent, this.rangeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresMeleeWeapon);
      this.addUseEvent(this::onUseEvent);
   }

   public void setBleedingPower(int power) {
      this.bleedingPower = power;
   }

   public void setBleedingTime(int time) {
      this.bleedingTime = time;
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 10.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 10, 0));
      this.animationComponent.start(entity, ModAnimations.CHARGE_CLEAVE);
   }

   private void stopChargeEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 5.0F, 2.0F)) {
         if (this.damageComponent.hurtTarget(entity, target, 20.0F)) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.BLEEDING.get(), this.bleedingTime, this.bleedingPower));
            Vec3 dirVec = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
            AbilityHelper.setDeltaMovement(target, dirVec.f_82479_, 0.2, dirVec.f_82481_);
         }
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }
}
