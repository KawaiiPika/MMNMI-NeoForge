package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class SangoEntity extends ThrowableProjectile {

    public SangoEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public SangoEntity(Level level, LivingEntity shooter) {
        super(ModEntities.SANGO.get(), shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide()) {
            // Sango effect logic here
            this.discard();
        }
        super.onHit(result);
    }
}
