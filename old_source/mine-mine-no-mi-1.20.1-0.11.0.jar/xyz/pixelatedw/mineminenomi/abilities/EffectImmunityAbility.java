package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public abstract class EffectImmunityAbility extends PassiveAbility {
   public static final ImmunityInfo HEAT_RESISTANCES;
   protected final MobEffectComponent mobEffectComponent = new MobEffectComponent(this);
   private final ImmunityInfo immunityInfo;

   public EffectImmunityAbility(AbilityCore<? extends EffectImmunityAbility> core, ImmunityInfo info) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.mobEffectComponent});
      this.immunityInfo = info;
      this.mobEffectComponent.setEffectCheck(this::checkEffectEvent);
   }

   private boolean checkEffectEvent(LivingEntity player, MobEffectInstance effect) {
      if (AbilityHelper.isAffectedByWater(player)) {
         return true;
      } else {
         ArrayList<Supplier<MobEffect>> immunityEffects = this.immunityInfo.getImmunityEffects();
         ArrayList<Supplier<MobEffect>> resistanceEffects = this.immunityInfo.getResistanceEffects();
         ArrayList<Integer> resistanceEffectsReduction = this.immunityInfo.getResistanceEffectsReduction();
         if (resistanceEffects.size() > 0) {
            for(int i = 0; i < resistanceEffects.size(); ++i) {
               if (((MobEffect)((Supplier)resistanceEffects.get(i)).get()).equals(effect.m_19544_()) && effect.m_19564_() < 1) {
                  int duration = effect.m_19557_() / (Integer)resistanceEffectsReduction.get(i);
                  if (duration <= 20) {
                     return false;
                  }

                  ((IMobEffectInstanceMixin)effect).setDuration(duration);
               }
            }
         }

         return !((List)immunityEffects.stream().map((s) -> (MobEffect)s.get()).collect(Collectors.toList())).contains(effect.m_19544_());
      }
   }

   static {
      HEAT_RESISTANCES = (new ImmunityInfo()).addImmunityEffects(ModEffects.FROSTBITE, ModEffects.CANDY_STUCK, ModEffects.CANDLE_LOCK).addResistanceEffect((Supplier)ModEffects.FROZEN, 10).addResistanceEffect((Supplier)ModEffects.STICKY, 6);
   }

   public static class ImmunityInfo {
      private ArrayList<Supplier<MobEffect>> immunityEffects = new ArrayList();
      private ArrayList<Supplier<MobEffect>> resistanceEffects = new ArrayList();
      private ArrayList<Integer> resistanceEffectsReduction = new ArrayList();

      public ImmunityInfo clone() {
         ImmunityInfo info = new ImmunityInfo();
         info.immunityEffects = this.immunityEffects;
         info.resistanceEffects = this.resistanceEffects;
         info.resistanceEffectsReduction = this.resistanceEffectsReduction;
         return info;
      }

      public ImmunityInfo combine(ImmunityInfo other) {
         this.immunityEffects.addAll(other.immunityEffects);
         this.resistanceEffects.addAll(other.resistanceEffects);
         this.resistanceEffectsReduction.addAll(other.resistanceEffectsReduction);
         return this;
      }

      public ArrayList<Supplier<MobEffect>> getImmunityEffects() {
         return this.immunityEffects;
      }

      public ArrayList<Supplier<MobEffect>> getResistanceEffects() {
         return this.resistanceEffects;
      }

      public ArrayList<Integer> getResistanceEffectsReduction() {
         return this.resistanceEffectsReduction;
      }

      public <T extends ImmunityInfo> T addImmunityEffects(MobEffect... immunityEffects) {
         for(MobEffect effect : immunityEffects) {
            this.immunityEffects.add((Supplier)() -> effect);
         }

         return (T)this;
      }

      public <T extends ImmunityInfo> T addImmunityEffects(Supplier<MobEffect>... immunityEffects) {
         this.immunityEffects.addAll(Arrays.asList(immunityEffects));
         return (T)this;
      }

      public <T extends ImmunityInfo> T addResistanceEffect(MobEffect resistanceEffect, int reduction) {
         this.resistanceEffects.add((Supplier)() -> resistanceEffect);
         this.resistanceEffectsReduction.add(reduction);
         return (T)this;
      }

      public <T extends ImmunityInfo> T addResistanceEffect(Supplier<MobEffect> resistanceEffect, int reduction) {
         this.resistanceEffects.add(resistanceEffect);
         this.resistanceEffectsReduction.add(reduction);
         return (T)this;
      }

      public <T extends ImmunityInfo> T addLogiaImmunities() {
         this.addResistanceEffect((Supplier)ModEffects.MOVEMENT_BLOCKED, 2);
         return (T)this;
      }
   }
}
