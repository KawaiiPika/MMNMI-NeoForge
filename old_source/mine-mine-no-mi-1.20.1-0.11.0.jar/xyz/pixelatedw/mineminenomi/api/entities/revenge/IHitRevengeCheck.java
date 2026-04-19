package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface IHitRevengeCheck extends IRevengeCheck {
   boolean check(LivingEntity var1, DamageSource var2);
}
