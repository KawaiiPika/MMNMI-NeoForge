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

public class BattoStrikeAbility extends Ability {
   private static final int CHARGE_TIME = 5;
   private static final int COOLDOWN = 300;
   private static final float DAMAGE = 20.0F;
   private static final int RANGE = 4;
   public static final RegistryObject<AbilityCore<BattoStrikeAbility>> INSTANCE = ModRegistry.registerAbility("batto_strike", "Batto Strike", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, BattoStrikeAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ChargeComponent.getTooltip(5.0F), RangeComponent.getTooltip(4.0F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi"));
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addEndEvent(this::stopChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final DealDamageComponent damageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public BattoStrikeAbility(AbilityCore<BattoStrikeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.damageComponent, this.rangeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresBluntWeapon);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 5.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0));
      this.animationComponent.start(entity, ModAnimations.BATTO_STRIKE);
   }

   private void stopChargeEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 4.0F, 2.0F)) {
         if (this.damageComponent.hurtTarget(entity, target, 20.0F)) {
            Vec3 dirVec = target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
            AbilityHelper.setDeltaMovement(target, dirVec.f_82479_, (double)1.75F, dirVec.f_82481_);
         }
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
