package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.resources.ResourceLocation;

public class AbilityComponentKey<C extends AbilityComponent<?>> {
   private final ResourceLocation id;

   public AbilityComponentKey(ResourceLocation id) {
      this.id = id;
   }

   public static <C extends AbilityComponent<?>> AbilityComponentKey<C> key(ResourceLocation id) {
      return new AbilityComponentKey<C>(id);
   }

   public ResourceLocation getId() {
      return this.id;
   }
}
