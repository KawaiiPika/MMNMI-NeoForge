package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.LivingEntity;

public interface IGoalPartner<T extends LivingEntity> {
   T getPartner();

   void setPartner(T var1);
}
