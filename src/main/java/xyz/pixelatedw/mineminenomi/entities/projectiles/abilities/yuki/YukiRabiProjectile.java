package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;


public class YukiRabiProjectile extends ThrowableProjectile {

    public YukiRabiProjectile(EntityType<? extends YukiRabiProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public YukiRabiProjectile(Level level, LivingEntity owner) {
        super((net.minecraft.world.entity.EntityType<? extends net.minecraft.world.entity.projectile.ThrowableProjectile>) ModEntities.YUKI_RABI.get(), owner, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
             this.level().addParticle(ParticleTypes.ITEM_SNOWBALL,
                 this.getX() + (this.random.nextDouble() - 0.5) * 0.5,
                 this.getY() + (this.random.nextDouble() - 0.5) * 0.5,
                 this.getZ() + (this.random.nextDouble() - 0.5) * 0.5,
                 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.getOwner() != null && result.getEntity() != this.getOwner()) {
             result.getEntity().hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 3.5F);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
             this.discard();
        }
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {}
}
