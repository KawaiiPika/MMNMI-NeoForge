package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class HikenEntity extends ThrowableItemProjectile {

    public HikenEntity(EntityType<? extends HikenEntity> type, Level level) {
        super(type, level);
    }

    public HikenEntity(Level level, LivingEntity owner) {
        super(ModEntities.HIKEN.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                    this.getX() + (this.random.nextDouble() - 0.5), 
                    this.getY() + (this.random.nextDouble() - 0.5), 
                    this.getZ() + (this.random.nextDouble() - 0.5), 
                    0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 15.0F);
                target.setRemainingFireTicks(100);
            }
            this.explode();
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.explode();
            this.discard();
        }
    }

    private void explode() {
        this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 3.0F, true, Level.ExplosionInteraction.MOB);
    }
}
