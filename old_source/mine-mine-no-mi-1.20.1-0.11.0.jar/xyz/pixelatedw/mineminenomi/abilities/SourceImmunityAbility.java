package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;

public abstract class SourceImmunityAbility extends PassiveAbility {
   public static final SourceImmunityInfo EMPTY = new SourceImmunityInfo();
   public static final SourceImmunityInfo FIRE_IMMUNITY = (new SourceImmunityInfo()).addSourceRule((ds) -> ds.m_269533_(DamageTypeTags.f_268745_) && !ds.m_269150_().m_203565_(DamageTypes.f_268546_) && !ds.m_269150_().m_203565_(ModDamageTypes.SUN_INCINERATION)).addSourceRule((ds) -> IDamageSourceHandler.getHandler(ds).hasElement(SourceElement.FIRE));
   public static final SourceImmunityInfo MAGMA_IMMUNITY = (new SourceImmunityInfo()).addSourceRule((ds) -> ds.m_269533_(DamageTypeTags.f_268745_) && !ds.m_269150_().m_203565_(ModDamageTypes.SUN_INCINERATION));
   public static final SourceImmunityInfo LIGHTNING_IMMUNITY;
   protected final SourceImmunityInfo immunityInfo;
   protected final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageCheck);

   public SourceImmunityAbility(AbilityCore<? extends SourceImmunityAbility> ability, @Nullable SourceImmunityInfo immunityInfo) {
      super(ability);
      this.immunityInfo = immunityInfo == null ? EMPTY : immunityInfo;
      this.addComponents(new AbilityComponent[]{this.damageTakenComponent});
   }

   public abstract boolean isDamageTaken(LivingEntity var1, DamageSource var2, float var3);

   public float damageCheck(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.isDamageTaken(user, damageSource, damage)) {
         return 0.0F;
      } else {
         return this.immunityInfo.isImmune(damageSource) ? 0.0F : damage;
      }
   }

   static {
      LIGHTNING_IMMUNITY = (new SourceImmunityInfo()).addSourceImmunities(DamageTypes.f_268450_).addSourceRule((ds) -> IDamageSourceHandler.getHandler(ds).hasElement(SourceElement.LIGHTNING));
   }

   public static class SourceImmunityInfo {
      private static final List<ResourceKey<DamageType>> DEFAULT_LOGIA_IMMUNITIES;
      private ArrayList<ResourceKey<DamageType>> sourceImmunities = new ArrayList();
      private ArrayList<Pair<ResourceKey<DamageType>, Integer>> sourceResistances = new ArrayList();
      private ArrayList<Predicate<DamageSource>> sourceRules = new ArrayList();

      public SourceImmunityInfo() {
         for(ResourceKey<DamageType> type : DEFAULT_LOGIA_IMMUNITIES) {
            this.addSourceImmunities(type);
         }

      }

      public boolean isImmune(ResourceKey<DamageType> source) {
         for(ResourceKey<DamageType> damageKey : this.sourceImmunities) {
            if (source.equals(damageKey)) {
               return true;
            }
         }

         return false;
      }

      public boolean isImmune(DamageSource source) {
         boolean hasSourcePass = this.sourceRules.stream().anyMatch((p) -> p.test(source));
         if (hasSourcePass) {
            return true;
         } else {
            for(ResourceKey<DamageType> damageKey : this.sourceImmunities) {
               if (source.m_269150_().m_203565_(damageKey)) {
                  return true;
               }
            }

            return false;
         }
      }

      public <T extends SourceImmunityInfo> T addSourceRule(Predicate<DamageSource> rule) {
         this.sourceRules.add(rule);
         return (T)this;
      }

      public <T extends SourceImmunityInfo> T addSourceImmunities(ResourceKey<DamageType>... sources) {
         this.sourceImmunities.addAll(Arrays.asList(sources));
         return (T)this;
      }

      public <T extends SourceImmunityInfo> T addSourceResistance(ResourceKey<DamageType> source, int resistance) {
         this.sourceResistances.add(Pair.of(source, resistance));
         return (T)this;
      }

      static {
         DEFAULT_LOGIA_IMMUNITIES = Arrays.asList(DamageTypes.f_268585_, DamageTypes.f_268469_, DamageTypes.f_268526_, DamageTypes.f_268576_, DamageTypes.f_268671_, DamageTypes.f_268659_);
      }
   }
}
