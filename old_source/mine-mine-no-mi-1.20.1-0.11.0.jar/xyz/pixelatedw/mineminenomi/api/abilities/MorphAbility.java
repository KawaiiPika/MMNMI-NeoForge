package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Map;
import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;

public abstract class MorphAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(110, this::startContinuityEvent).addTickEvent(110, this::tickContinuityEvent).addEndEvent(110, this::stopContinuityEvent);
   protected final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   protected final MorphComponent morphComponent = new MorphComponent(this);
   protected final PoolComponent poolComponent;

   public MorphAbility(AbilityCore<? extends MorphAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.MORPHS, new AbilityPool[0]);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent, this.morphComponent, this.poolComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresTransformationSpace);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Optional<MorphInfo> currentMorph = (Optional)DevilFruitCapability.get(entity).map((propsx) -> propsx.getCurrentMorph()).get();
      if (currentMorph.isPresent() && !((MorphInfo)currentMorph.get()).equals(this.getTransformation())) {
         IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (props != null) {
            for(IAbility abl : props.getEquippedAbilitiesWith((AbilityComponentKey)ModAbilityComponents.MORPH.get(), (AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get())) {
               if (abl != this) {
                  ((ContinuousComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).get()).stopContinuity(entity);
               }
            }
         }
      }

      this.continuousComponent.triggerContinuity(entity, this.getContinuityHoldTime());
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      float initialHealthPercentage = entity.m_21223_() / entity.m_21233_();
      entity.m_20124_(Pose.STANDING);
      this.morphComponent.startMorph(entity, this.getTransformation());
      this.statsComponent.applyModifiers(entity);
      float newHealth = entity.m_21233_() * initialHealthPercentage;
      entity.m_21153_(newHealth);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!this.morphComponent.isMorphed()) {
         this.continuousComponent.stopContinuity(entity);
      }

      for(Map.Entry<String, PartEntity<?>> entry : this.morphComponent.getParts().entrySet()) {
         String name = (String)entry.getKey();
         PartEntity<?> part = (PartEntity)entry.getValue();
         Vec3 position = part.m_20182_();
         this.handlePart(name, part, this);
         part.f_19854_ = position.f_82479_;
         part.f_19855_ = position.f_82480_;
         part.f_19856_ = position.f_82481_;
         part.f_19790_ = position.f_82479_;
         part.f_19791_ = position.f_82480_;
         part.f_19792_ = position.f_82481_;
      }

   }

   private void stopContinuityEvent(LivingEntity entity, IAbility ability) {
      float initialHealthPercentage = entity.m_21223_() / entity.m_21233_();
      this.morphComponent.stopMorph(entity);
      this.statsComponent.removeModifiers(entity);
      this.cooldownComponent.startCooldown(entity, this.getCooldownTicks());
      float newHealth = entity.m_21233_() * initialHealthPercentage;
      entity.m_21153_(newHealth);
   }

   public abstract MorphInfo getTransformation();

   public float getContinuityHoldTime() {
      return -1.0F;
   }

   public float getCooldownTicks() {
      return 10.0F;
   }

   public void handlePart(String name, PartEntity<?> part, IAbility ability) {
   }
}
