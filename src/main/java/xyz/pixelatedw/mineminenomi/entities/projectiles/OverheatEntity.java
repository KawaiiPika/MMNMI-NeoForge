package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class OverheatEntity extends ThrowableProjectile {
    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    public OverheatEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public OverheatEntity(Level level, LivingEntity thrower) {
        super(ModEntities.OVERHEAT.get(), thrower, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.hurt(this.damageSources().inFire(), 20.0F);
            target.setRemainingFireTicks(100);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.level().explode(this, result.getLocation().x, result.getLocation().y, result.getLocation().z, 3.0F, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
