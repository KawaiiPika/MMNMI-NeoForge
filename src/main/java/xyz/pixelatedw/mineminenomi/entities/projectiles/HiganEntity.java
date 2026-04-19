package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class HiganEntity extends ThrowableItemProjectile {
    public HiganEntity(EntityType<? extends HiganEntity> type, Level world) {
        super(type, world);
    }

    public HiganEntity(Level world, LivingEntity owner) {
        super(xyz.pixelatedw.mineminenomi.init.ModEntities.HIGAN.get(), owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            this.level().addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        if (this.tickCount > 40) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
            target.setRemainingFireTicks(60);
            target.hurt(this.damageSources().mobAttack(owner), 4.0F);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }
}
