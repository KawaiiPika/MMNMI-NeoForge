package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SaiseiNoHonoAbility extends Ability {
   private static final TargetPredicate TARGETS_CHECK = (new TargetPredicate()).testFriendlyFaction();
   private static final float COOLDOWN = 300.0F;
   private static final int RANGE = 10;
   private static final int HEAL = 10;
   public static final RegistryObject<AbilityCore<SaiseiNoHonoAbility>> INSTANCE = ModRegistry.registerAbility("saisei_no_hono", "Saisei No Hono", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Uses the blue flames to heal the target by hitting them. Using it while crouching will heal friendly entities around the user including the user themselves at the expense of a longer cooldown based on the number of healed entities.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SaiseiNoHonoAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PHOENIX_ASSAULT)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(), HealComponent.getTooltip(10.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).setSourceType(SourceType.FRIENDLY).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addOnHitEvent(this::hitEvent).addTryHitEvent(this::tryHitEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final HealComponent healComponent = new HealComponent(this);
   private int healed;

   public SaiseiNoHonoAbility(AbilityCore<SaiseiNoHonoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTriggerComponent, this.rangeComponent, this.healComponent});
      this.addCanUseCheck(ToriPhoenixHelper::requiresAssaultPoint);
      this.addContinueUseCheck(ToriPhoenixHelper::requiresAssaultPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.healed = 0;
      if (entity.m_6047_()) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F, TARGETS_CHECK);
         targets.add(entity);
         int healed = 0;

         for(LivingEntity target : targets) {
            ++healed;
            this.healComponent.healTarget(entity, target, 10.0F);
            target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 400, 1));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLUE_FLAMES.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
         }

         this.healed = Math.max(1, healed);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TENSEI_NO_SOEN_2.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 300.0F + (float)(this.healed * 120));
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return !this.continuousComponent.isContinuous() ? HitTriggerComponent.HitResult.PASS : HitTriggerComponent.HitResult.HIT;
   }

   private boolean hitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      this.healComponent.healTarget(entity, target, 10.0F);
      target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 400, 1));
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLUE_FLAMES.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      this.continuousComponent.stopContinuity(entity);
      return false;
   }
}
