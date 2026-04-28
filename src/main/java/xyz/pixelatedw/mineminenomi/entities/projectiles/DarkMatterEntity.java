package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class DarkMatterEntity extends ThrowableProjectile {

    public DarkMatterEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public DarkMatterEntity(Level level, LivingEntity shooter) {
        super(ModEntities.DARK_MATTER.get(), shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide()) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 4.0F, Level.ExplosionInteraction.BLOCK);
            this.discard();
        }
        super.onHit(result);
    }
}
