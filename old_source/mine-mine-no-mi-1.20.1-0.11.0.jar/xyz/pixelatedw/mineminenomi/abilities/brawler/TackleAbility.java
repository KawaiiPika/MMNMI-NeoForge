package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TackleAbility extends Ability {
   private static final float HOLD_TIME = 10.0F;
   private static final float COOLDOWN = 200.0F;
   private static final float RANGE = 1.2F;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<TackleAbility>> INSTANCE = ModRegistry.registerAbility("tackle", "Tackle", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Tackles all enemies in front of the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, TackleAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(1.2F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(15.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.PHYSICAL).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public TackleAbility(AbilityCore<TackleAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.rangeComponent, this.hitTrackerComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 10.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.animationComponent.start(entity, ModAnimations.TACKLE);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_()) {
         Vec3 look = entity.m_20154_();
         Vec3 speed = look.m_82542_(2.3, (double)0.0F, 2.3);
         entity.m_6478_(MoverType.SELF, speed);

         for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.0F, 1.2F)) {
            if (this.hitTrackerComponent.canHit(target)) {
               this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
            }
         }

         for(Entity target : this.hitTrackerComponent.getHits()) {
            target.m_6021_(entity.m_20185_() + look.f_82479_, entity.m_20186_(), entity.m_20189_() + look.f_82481_);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
