package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;

public class PartEntityType<P extends PartEntity<T>, T extends Entity> {
   private final PartEntityFactory<P, T> factory;

   public PartEntityType(PartEntityFactory<P, T> factory) {
      this.factory = factory;
   }

   public ResourceLocation getKey() {
      return ((IForgeRegistry)WyRegistry.PART_ENTITY_TYPES.get()).getKey(this);
   }

   public P create(T parent) {
      return this.factory.create(parent);
   }

   @FunctionalInterface
   public interface PartEntityFactory<P extends PartEntity<T>, T extends Entity> {
      P create(T var1);
   }
}
