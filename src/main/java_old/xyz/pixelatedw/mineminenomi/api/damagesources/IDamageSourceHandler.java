package xyz.pixelatedw.mineminenomi.api.damagesources;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public interface IDamageSourceHandler {
   static IDamageSourceHandler getHandler(DamageSource source) {
      return (IDamageSourceHandler)source;
   }

   IDamageSourceHandler clone();

   boolean isInitialized();

   Map<AbilityCore<?>, CompoundTag> getTags();

   @Nullable CompoundTag getAbilityTags(AbilityCore<?> var1);

   void addAbilityPiercing(float var1, AbilityCore<?> var2);

   float getAbilityPiercing(@Nullable AbilityCore<?> var1);

   void addGlobalPiercing(float var1);

   float getGlobalPiercing();

   void bypassLogia();

   boolean isBypassingLogia();

   void setUnavoidable();

   boolean isUnavoidable();

   void reset();

   void clearDamage();

   void addVanillaPunch(float var1);

   void addAbilityDamage(float var1, @Nullable AbilityCore<?> var2);

   void addDamage(float var1, SourceElement var2, SourceHakiNature var3, SourceType... var4);

   void addDamage(float var1, NuDamageValue var2);

   Set<Map.Entry<NuDamageValue, Float>> getDamageValues();

   float getTotalDamage();

   float getTotalBaseDamage();

   float getTotalAbilityDamage();

   float getTotalAbilityDamageForType(SourceType var1);

   float getTotalAbilityDamageForHaki(SourceHakiNature var1);

   boolean hasElement(SourceElement var1);

   boolean hasHakiNature(SourceHakiNature var1);

   boolean hasType(SourceType var1);

   boolean hasAbility(AbilityCore<?> var1);

   Set<AbilityCore<?>> getAbilitiesForElement(SourceElement var1);

   Set<AbilityCore<?>> getAbilitiesForHakiNature(SourceHakiNature var1);

   Set<AbilityCore<?>> getAbilitiesForType(SourceType var1);

   public static record NuDamageValue(@Nullable AbilityCore<?> ability, SourceElement element, SourceHakiNature hakiNature, SourceType... sourceTypes) {
      public boolean hasType(SourceType type) {
         return Arrays.stream(this.sourceTypes()).anyMatch((t) -> t.equals(type));
      }

      public String toString() {
         String ablName = this.ability != null ? this.ability.getLocalizedName().getString() : "null";
         return String.format("[ability=\"%s\", element=%s, hakiNature=%s, sourceTypes=%s]", ablName, this.element, this.hakiNature, Arrays.toString(this.sourceTypes));
      }
   }

   public static class IntTag extends Tag<Integer> {
      public IntTag(Integer value) {
         super(value);
      }
   }

   public static class BoolTag extends Tag<Boolean> {
      public BoolTag(Boolean value) {
         super(value);
      }
   }

   public static class FloatTag extends Tag<Float> {
      public FloatTag(Float value) {
         super(value);
      }
   }

   public abstract static class Tag<T> {
      private T value;

      public Tag(T value) {
         this.value = value;
      }

      public T value() {
         return this.value;
      }
   }
}
