package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.Map;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public interface IAbility extends Comparable<IAbility> {
   void use(LivingEntity var1);

   void tick(LivingEntity var1);

   Result canUse(LivingEntity var1);

   AbilityCore<? extends IAbility> getCore();

   Component getDisplayName();

   ResourceLocation getIcon(LivingEntity var1);

   void onEquip(LivingEntity var1);

   void onRemove(LivingEntity var1);

   <C extends AbilityComponent<?>> Optional<C> getComponent(AbilityComponentKey<C> var1);

   Map<AbilityComponentKey<?>, AbilityComponent<?>> getComponents();

   boolean hasComponent(AbilityComponentKey<?> var1);

   boolean isOGCD();

   default void save(CompoundTag nbt) {
      CompoundTag components = new CompoundTag();

      for(Map.Entry<AbilityComponentKey<?>, AbilityComponent<?>> entry : this.getComponents().entrySet()) {
         CompoundTag compNbt = ((AbilityComponent)entry.getValue()).save();
         if (compNbt != null) {
            components.m_128365_(((AbilityComponentKey)entry.getKey()).getId().toString(), compNbt);
         }
      }

      nbt.m_128365_("components", components);
   }

   default void load(CompoundTag nbt) {
      CompoundTag components = nbt.m_128469_("components");

      for(Map.Entry<AbilityComponentKey<?>, AbilityComponent<?>> entry : this.getComponents().entrySet()) {
         CompoundTag compNbt = components.m_128469_(((AbilityComponentKey)entry.getKey()).getId().toString());
         if (compNbt != null) {
            ((AbilityComponent)entry.getValue()).load(compNbt);
         }
      }

   }

   default boolean isAbilityDisabled() {
      return (Boolean)this.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).map((comp) -> comp.isDisabled()).orElse(false);
   }

   default boolean isAbilityPaused() {
      return (Boolean)this.getComponent((AbilityComponentKey)ModAbilityComponents.PAUSE_TICK.get()).map((comp) -> comp.isPaused()).orElse(false);
   }

   default int compareTo(IAbility other) {
      return other == null ? 1 : this.getCore().compareTo(other.getCore());
   }
}
