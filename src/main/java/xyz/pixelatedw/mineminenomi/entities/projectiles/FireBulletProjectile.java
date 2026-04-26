package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class FireBulletProjectile extends NuProjectileEntity {
    public FireBulletProjectile(EntityType<? extends FireBulletProjectile> type, Level world) {
        super(type, world);
    }

    public FireBulletProjectile(Level world, LivingEntity thrower) {
        super(ModEntities.FIRE_BULLET.get(), thrower, world);
        this.setDamage(6.0F);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity target) {
            target.igniteForSeconds(5);
        }
    }
}
