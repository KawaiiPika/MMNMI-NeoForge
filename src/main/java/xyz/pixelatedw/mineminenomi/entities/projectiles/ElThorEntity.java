package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.core.BlockPos;

public class ElThorEntity extends ThrowableProjectile {

    public ElThorEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public ElThorEntity(Level level, LivingEntity shooter) {
        super(ModEntities.EL_THOR.get(), shooter, level);
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide()) {
            LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level());
            if (bolt != null) {
                bolt.moveTo(this.position());
                this.level().addFreshEntity(bolt);
            }
            this.discard();
        }
        super.onHit(result);
    }
}
