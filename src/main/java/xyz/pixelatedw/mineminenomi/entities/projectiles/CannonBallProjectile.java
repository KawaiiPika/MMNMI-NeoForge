package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class CannonBallProjectile extends NuProjectileEntity {
    public CannonBallProjectile(EntityType<? extends CannonBallProjectile> type, Level world) {
        super(type, world);
    }

    public CannonBallProjectile(Level world, LivingEntity thrower) {
        super(ModEntities.CANNON_BALL.get(), thrower, world);
        this.setDamage(14.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide()) {
            this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 2.0F, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }
}
