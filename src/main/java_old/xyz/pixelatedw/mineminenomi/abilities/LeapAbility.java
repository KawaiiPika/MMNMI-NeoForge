package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class LeapAbility extends DropHitAbility {
   private static final float COOLDOWN = 120.0F;
   private static final float DAMAGE = 10.0F;
   private static final float RANGE = 2.5F;
   public static final RegistryObject<AbilityCore<LeapAbility>> INSTANCE = ModRegistry.registerAbility("leap", "Leap", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Leaps at the enemy and damages all nearby entities on landing.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, LeapAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F), DealDamageComponent.getTooltip(10.0F), RangeComponent.getTooltip(2.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private double leapHeight = (double)1.0F;
   private double leapDistance = 1.4;

   public LeapAbility(AbilityCore<LeapAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent});
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   public double getLeapHeight() {
      return this.leapHeight;
   }

   public void setLeapHeight(double height) {
      this.leapHeight = height;
   }

   public double getLeapDistance() {
      return this.leapDistance;
   }

   public void setLeapDistance(double distance) {
      this.leapDistance = distance;
   }

   public void onLanding(LivingEntity entity) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 2.5F)) {
         if (this.hitTrackerComponent.canHit(target)) {
            this.dealDamageComponent.hurtTarget(entity, target, 10.0F);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 120.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      Vec3 speed = entity.m_20154_().m_82542_(this.leapDistance, (double)1.0F, this.leapDistance);
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, this.leapHeight, speed.f_82481_);
   }
}
