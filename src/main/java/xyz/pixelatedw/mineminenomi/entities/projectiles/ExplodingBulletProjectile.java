package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class ExplodingBulletProjectile extends NuProjectileEntity {
    public ExplodingBulletProjectile(EntityType<? extends ExplodingBulletProjectile> type, Level world) {
        super(type, world);
    }

    public ExplodingBulletProjectile(Level world, LivingEntity thrower) {
        super(ModEntities.EXPLODING_BULLET.get(), thrower, world);
        this.setDamage(8.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide()) {
            this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 1.5F, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }
}
