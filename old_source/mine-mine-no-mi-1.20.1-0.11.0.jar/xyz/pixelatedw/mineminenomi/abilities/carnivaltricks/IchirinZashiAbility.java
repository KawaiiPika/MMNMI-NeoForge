package xyz.pixelatedw.mineminenomi.abilities.carnivaltricks;

import java.util.List;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class IchirinZashiAbility extends DropHitAbility {
   private static final int COOLDOWN = 200;
   private static final float RANGE = 3.5F;
   private static final float DAMAGE = 20.0F;
   public static final RegistryObject<AbilityCore<IchirinZashiAbility>> INSTANCE = ModRegistry.registerAbility("ichirin_zashi", "Ichirin Zashi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user leaps into the air and drops down on ground hitting the target on their descent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, IchirinZashiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), DealDamageComponent.getTooltip(20.0F), RangeComponent.getTooltip(3.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public IchirinZashiAbility(AbilityCore<IchirinZashiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.animationComponent});
      this.continuousComponent.addStartEvent(100, this::onStartContinuityEvent);
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
   }

   public void onLanding(LivingEntity entity) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 3.5F);
      targets.remove(entity);

      for(LivingEntity target : targets) {
         if (this.hitTrackerComponent.canHit(target) && entity.m_142582_(target)) {
            this.dealDamageComponent.hurtTarget(entity, target, 20.0F);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 200.0F);
      this.animationComponent.stop(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() > 10.0F && !this.hasLanded()) {
         if (entity.m_20096_()) {
            this.setLanded();
         } else if (entity.m_20202_() != null && entity.m_20202_().m_20096_()) {
            this.setLanded();
         }
      }

   }

   private void onStartContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.STAB_DOWN);
      Vec3 speed = entity.m_20154_().m_82541_();
      if (entity.m_20202_() != null) {
         AbilityHelper.setDeltaMovement(entity.m_20202_(), speed.f_82479_, 1.3, speed.f_82481_);
      } else {
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 1.3, speed.f_82481_);
      }
   }
}
