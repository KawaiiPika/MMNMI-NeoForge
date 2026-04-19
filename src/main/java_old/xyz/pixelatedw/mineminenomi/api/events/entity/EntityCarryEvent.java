package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

public class EntityCarryEvent extends LivingEvent {
   private final @Nullable LivingEntity carryTarget;
   private final boolean isCarrying;

   public EntityCarryEvent(LivingEntity entity, @Nullable LivingEntity carryTarget, boolean isCarrying) {
      super(entity);
      this.carryTarget = carryTarget;
      this.isCarrying = isCarrying;
   }

   public @Nullable LivingEntity getCarryTarget() {
      return this.carryTarget;
   }

   public boolean isCarrying() {
      return this.isCarrying;
   }
}
