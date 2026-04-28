package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class VenomRoadEntity extends ThrowableProjectile {
    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    public VenomRoadEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public VenomRoadEntity(Level level, LivingEntity thrower) {
        super(ModEntities.VENOM_ROAD.get(), thrower, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target && this.getOwner() != null) {
            this.getOwner().teleportTo(target.getX(), target.getY(), target.getZ());
            target.hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 10.0F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide && this.getOwner() != null) {
            this.getOwner().teleportTo(result.getLocation().x, result.getLocation().y + 1, result.getLocation().z);
            this.discard();
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
