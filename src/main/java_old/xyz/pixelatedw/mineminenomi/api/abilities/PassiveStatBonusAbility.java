package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;

public abstract class PassiveStatBonusAbility extends PassiveAbility {
   private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   private HashMap<Attribute, Function<LivingEntity, AttributeModifier>> dynamicAttributes = new HashMap();
   private HashMap<Attribute, Double> cache = new HashMap();

   public PassiveStatBonusAbility(AbilityCore<? extends PassiveStatBonusAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.statsComponent});
      this.pauseTickComponent.addResumeEvent(100, this::resumeEvent);
      this.pauseTickComponent.addPauseEvent(100, this::pauseEvent);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
      this.setTickRate(40.0F);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         this.updateStats(entity);
      }

      if (this.pauseTickComponent.isPaused()) {
         this.statsComponent.removeModifiers(entity);
      } else if (!this.getCheck().test(entity)) {
         this.statsComponent.removeModifiers(entity);
      } else {
         this.statsComponent.applyModifiers(entity);
      }
   }

   private void resumeEvent(LivingEntity entity, IAbility ability) {
      if (this.getCheck().test(entity)) {
         this.statsComponent.applyModifiers(entity);
      }

   }

   private void pauseEvent(LivingEntity entity, IAbility ability) {
      this.statsComponent.removeModifiers(entity);
   }

   private boolean updateStats(LivingEntity entity) {
      boolean updates = false;

      for(Map.Entry<Attribute, Function<LivingEntity, AttributeModifier>> entry : this.dynamicAttributes.entrySet()) {
         AttributeModifier mod = (AttributeModifier)((Function)entry.getValue()).apply(entity);
         double bonus = mod.m_22218_();
         Double lastStat = (Double)this.cache.get(entry.getKey());
         if (lastStat == null || lastStat != bonus) {
            this.statsComponent.removeModifier(entity, (Attribute)entry.getKey(), mod);
            this.statsComponent.removeAttributeModifier((Attribute)entry.getKey());
            this.statsComponent.addAttributeModifier((Attribute)entry.getKey(), mod);
            this.cache.put((Attribute)entry.getKey(), bonus);
            updates = true;
         }
      }

      return updates;
   }

   public abstract Predicate<LivingEntity> getCheck();

   public void pushStaticAttribute(Attribute attr, AttributeModifier mod) {
      this.statsComponent.addAttributeModifier(attr, mod, this.getCheck());
   }

   public void pushStaticAttribute(Attribute attr, AttributeModifier mod, @Nullable Predicate<LivingEntity> test) {
      this.statsComponent.addAttributeModifier(attr, mod, test);
   }

   public void pushDynamicAttribute(Attribute attr, Function<LivingEntity, AttributeModifier> func) {
      this.dynamicAttributes.put(attr, func);
   }
}
