package xyz.pixelatedw.mineminenomi.abilities.sniper;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public abstract class SniperProjectileAbility extends Ability {

    public SniperProjectileAbility() {}

    protected void shootProjectile(LivingEntity entity, ThrowableItemProjectile projectile, float speed, float inaccuracy) {
        projectile.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, speed, inaccuracy);
        entity.level().addFreshEntity(projectile);
        
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.ARROW_SHOOT, 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f / (entity.getRandom().nextFloat() * 0.4f + 0.8f));
        }
    }
}
