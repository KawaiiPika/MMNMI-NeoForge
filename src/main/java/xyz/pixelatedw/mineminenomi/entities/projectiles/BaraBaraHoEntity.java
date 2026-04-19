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
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class BaraBaraHoEntity extends ThrowableItemProjectile {
    private int life = 0;
    private final int maxLife = 40;

    public BaraBaraHoEntity(EntityType<? extends BaraBaraHoEntity> type, Level world) {
        super(type, world);
    }

    public BaraBaraHoEntity(Level world, LivingEntity owner) {
        super(ModEntities.BARA_BARA_HO.get(), owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public void tick() {
        super.tick();
        life++;
        if (life > maxLife) {
            this.discard();
        }
        
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
            target.hurt(this.damageSources().mobAttack(owner), 8.0F);
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
