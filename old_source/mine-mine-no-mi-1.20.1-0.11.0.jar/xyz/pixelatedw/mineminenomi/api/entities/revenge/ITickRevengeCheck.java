package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.entity.LivingEntity;

public interface ITickRevengeCheck extends IRevengeCheck {
   boolean check(LivingEntity var1);
}
