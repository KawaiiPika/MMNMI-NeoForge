package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;


public class BlackRoadProjectile extends ThrowableProjectile {

    private int life;

    public BlackRoadProjectile(EntityType<? extends BlackRoadProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public BlackRoadProjectile(Level level, LivingEntity owner) {
        // Assume ModProjectiles.BLACK_ROAD exists, if not, create it or use a default that is properly registered.
        super((net.minecraft.world.entity.EntityType<? extends net.minecraft.world.entity.projectile.ThrowableProjectile>) ModEntities.BLACK_ROAD.get(), owner, level);
        this.life = 24;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
             this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5)).forEach(target -> {
                 if (this.getOwner() != null && target != this.getOwner() && !target.hurtMarked) {
                     target.hurt(this.damageSources().mobAttack((LivingEntity) this.getOwner()), 6.0F);
                     target.hurtMarked = true;
                 }
             });
        }

        if (this.level().isClientSide) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.SQUID_INK,
                this.getX() + (this.random.nextDouble() - 0.5) * 2,
                this.getY(),
                this.getZ() + (this.random.nextDouble() - 0.5) * 2,
                0, 0, 0);
        }

        if (this.life-- <= 0) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {}
}
