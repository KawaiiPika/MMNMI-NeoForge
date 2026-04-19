package xyz.pixelatedw.mineminenomi.api.entities;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public interface ITamableEntity {
   LivingEntity getOwner();

   @Nullable
   default LivingEntity getOwnerIfAlive() {
      LivingEntity owner = this.getOwner();
      return owner != null && owner.m_6084_() ? owner : null;
   }
}
