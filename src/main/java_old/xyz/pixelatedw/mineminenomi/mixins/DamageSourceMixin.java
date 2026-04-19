package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModTags;

@Mixin({DamageSource.class})
public class DamageSourceMixin implements IDamageSourceHandler, Cloneable {
   private static final String PIERCING_TAG = "piercing";
   private static final String LOGIA_BYPASS_TAG = "bypassLogia";
   private static final String UNAVOIDABLE_TAG = "unavoidable";
   private static final IDamageSourceHandler.NuDamageValue NON_ABILITY_VALUE;
   private Map<@Nullable AbilityCore<?>, CompoundTag> tags = new HashMap();
   private Map<IDamageSourceHandler.NuDamageValue, Float> damageValues = new HashMap();

   public IDamageSourceHandler clone() {
      DamageSource self = (DamageSource)this;
      DamageSource clone = new DamageSource(self.m_269150_(), self.m_7640_(), self.m_7639_(), self.m_7270_());
      IDamageSourceHandler cloneHandler = (IDamageSourceHandler)clone;
      ((DamageSourceMixin)cloneHandler).tags = this.tags;
      ((DamageSourceMixin)cloneHandler).damageValues = this.damageValues;
      return cloneHandler;
   }

   public boolean isInitialized() {
      return this.damageValues.size() > 0;
   }

   public Map<AbilityCore<?>, CompoundTag> getTags() {
      return this.tags;
   }

   public @Nullable CompoundTag getAbilityTags(AbilityCore<?> key) {
      Objects.requireNonNull(key, "Null ability found");

      for(Map.Entry<AbilityCore<?>, CompoundTag> entry : this.tags.entrySet()) {
         if (entry.getKey() != null && ((AbilityCore)entry.getKey()).equals(key)) {
            return (CompoundTag)entry.getValue();
         }
      }

      return null;
   }

   public void addAbilityPiercing(float value, AbilityCore<?> key) {
      Objects.requireNonNull(key, "Null ability found, use `addGlobalPiercing` for global piercing otherwise this is a bug");
      if (!this.tags.containsKey(key)) {
         this.tags.put(key, new CompoundTag());
      }

      ((CompoundTag)this.tags.get(key)).m_128350_("piercing", value);
   }

   public float getAbilityPiercing(@Nullable AbilityCore<?> key) {
      if (key == null) {
         return 0.0F;
      } else {
         for(Map.Entry<AbilityCore<?>, CompoundTag> entry : this.tags.entrySet()) {
            if (entry.getKey() != null && ((AbilityCore)entry.getKey()).equals(key)) {
               return Mth.m_14036_(((CompoundTag)entry.getValue()).m_128457_("piercing"), 0.0F, 1.0F);
            }
         }

         return 0.0F;
      }
   }

   public void addGlobalPiercing(float value) {
      this.tags.computeIfAbsent((Object)null, (k) -> new CompoundTag());
      ((CompoundTag)this.tags.get((Object)null)).m_128350_("piercing", value);
   }

   public float getGlobalPiercing() {
      this.tags.computeIfAbsent((Object)null, (k) -> new CompoundTag());
      return Mth.m_14036_(((CompoundTag)this.tags.get((Object)null)).m_128457_("piercing"), 0.0F, 1.0F);
   }

   public void bypassLogia() {
      this.tags.computeIfAbsent((Object)null, (k) -> new CompoundTag());
      ((CompoundTag)this.tags.get((Object)null)).m_128379_("bypassLogia", true);
   }

   public boolean isBypassingLogia() {
      this.tags.computeIfAbsent((Object)null, (k) -> new CompoundTag());
      DamageSource source = (DamageSource)this;
      return source.m_269533_(ModTags.DamageTypes.BYPASSES_LOGIA) || ((CompoundTag)this.tags.get((Object)null)).m_128471_("bypassLogia");
   }

   public void setUnavoidable() {
      this.tags.computeIfAbsent((Object)null, (k) -> new CompoundTag());
      ((CompoundTag)this.tags.get((Object)null)).m_128379_("unavoidable", true);
   }

   public boolean isUnavoidable() {
      this.tags.computeIfAbsent((Object)null, (k) -> new CompoundTag());
      DamageSource source = (DamageSource)this;
      return source.m_269533_(ModTags.DamageTypes.UNAVOIDABLE) || ((CompoundTag)this.tags.get((Object)null)).m_128471_("unavoidable");
   }

   public void reset() {
      this.tags = new HashMap();
      this.damageValues.clear();
   }

   public void clearDamage() {
      this.damageValues.clear();
   }

   public void addVanillaPunch(float damage) {
      this.addDamage(damage, NON_ABILITY_VALUE);
   }

   public void addAbilityDamage(float damage, @Nullable AbilityCore<?> core) {
      SourceElement element = core.getSourceElement();
      SourceHakiNature hakiNature = core.getSourceHakiNature();
      SourceType[] types = (SourceType[])core.getSourceTypes().toArray(new SourceType[0]);
      IDamageSourceHandler.NuDamageValue value = new IDamageSourceHandler.NuDamageValue(core, element, hakiNature, types);
      this.addDamage(damage, value);
   }

   public void addDamage(float damage, SourceElement element, SourceHakiNature hakiNature, SourceType... types) {
      IDamageSourceHandler.NuDamageValue value = new IDamageSourceHandler.NuDamageValue((AbilityCore)null, element, hakiNature, types);
      this.addDamage(damage, value);
   }

   public void addDamage(float damage, IDamageSourceHandler.NuDamageValue value) {
      float finalDamage = (float)Math.max((double)damage, (double)0.0F);
      this.damageValues.put(value, finalDamage);
   }

   public Set<Map.Entry<IDamageSourceHandler.NuDamageValue, Float>> getDamageValues() {
      return Collections.unmodifiableSet(this.damageValues.entrySet());
   }

   public float getTotalDamage() {
      return (Float)this.damageValues.values().stream().reduce(0.0F, (t, v) -> t + v);
   }

   public float getTotalBaseDamage() {
      return (Float)this.damageValues.entrySet().stream().filter((e) -> ((IDamageSourceHandler.NuDamageValue)e.getKey()).ability() == null).map((e) -> (Float)e.getValue()).reduce(0.0F, (t, v) -> t + v);
   }

   public float getTotalAbilityDamage() {
      return (Float)this.damageValues.entrySet().stream().filter((e) -> ((IDamageSourceHandler.NuDamageValue)e.getKey()).ability() != null).map((e) -> (Float)e.getValue()).reduce(0.0F, (t, v) -> t + v);
   }

   public float getTotalAbilityDamageForType(SourceType type) {
      float damage = 0.0F;

      for(Map.Entry<IDamageSourceHandler.NuDamageValue, Float> entry : this.damageValues.entrySet()) {
         if (((IDamageSourceHandler.NuDamageValue)entry.getKey()).ability() != null && ((IDamageSourceHandler.NuDamageValue)entry.getKey()).hasType(type)) {
            damage += (Float)entry.getValue();
         }
      }

      return damage;
   }

   public float getTotalAbilityDamageForHaki(SourceHakiNature hakiNature) {
      float damage = 0.0F;

      for(Map.Entry<IDamageSourceHandler.NuDamageValue, Float> entry : this.damageValues.entrySet()) {
         if (((IDamageSourceHandler.NuDamageValue)entry.getKey()).ability() != null && ((IDamageSourceHandler.NuDamageValue)entry.getKey()).hakiNature().equals(hakiNature)) {
            damage += (Float)entry.getValue();
         }
      }

      return damage;
   }

   public boolean hasElement(SourceElement element) {
      DamageSource source = (DamageSource)this;
      if (source instanceof BaseDamageSource baseSource) {
         boolean hasElement = baseSource.getElement().equals(element);
         if (hasElement) {
            return true;
         }
      }

      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         if (value.element().equals(element)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasHakiNature(SourceHakiNature nature) {
      DamageSource source = (DamageSource)this;
      if (source instanceof BaseDamageSource baseSource) {
         boolean hasHakiNature = baseSource.getHakiNature().equals(nature);
         if (hasHakiNature) {
            return true;
         }
      }

      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         if (value.hakiNature().equals(nature)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasType(SourceType type) {
      DamageSource source = (DamageSource)this;
      if (source instanceof BaseDamageSource baseSource) {
         boolean hasSourceType = baseSource.getTypes().contains(type);
         if (hasSourceType) {
            return true;
         }
      }

      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         if (value.hasType(type)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasAbility(AbilityCore<?> core) {
      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         if (value.ability() != null && value.ability().equals(core)) {
            return true;
         }
      }

      return false;
   }

   public Set<AbilityCore<?>> getAbilitiesForElement(SourceElement element) {
      Set<AbilityCore<?>> abilities = new HashSet();

      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         if (value.element().equals(element)) {
            abilities.add(value.ability());
         }
      }

      return abilities;
   }

   public Set<AbilityCore<?>> getAbilitiesForHakiNature(SourceHakiNature hakiNature) {
      Set<AbilityCore<?>> abilities = new HashSet();

      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         if (value.hakiNature().equals(hakiNature)) {
            abilities.add(value.ability());
         }
      }

      return abilities;
   }

   public Set<AbilityCore<?>> getAbilitiesForType(SourceType type) {
      Set<AbilityCore<?>> abilities = new HashSet();

      for(IDamageSourceHandler.NuDamageValue value : this.damageValues.keySet()) {
         for(SourceType abilityType : value.sourceTypes()) {
            if (abilityType.equals(type)) {
               abilities.add(value.ability());
               break;
            }
         }
      }

      return abilities;
   }

   static {
      NON_ABILITY_VALUE = new IDamageSourceHandler.NuDamageValue((AbilityCore)null, SourceElement.NONE, SourceHakiNature.HARDENING, new SourceType[]{SourceType.FIST});
   }
}
