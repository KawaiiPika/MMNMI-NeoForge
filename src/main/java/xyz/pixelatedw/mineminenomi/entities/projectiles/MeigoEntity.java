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

public class MeigoEntity extends ThrowableItemProjectile {

    public MeigoEntity(EntityType<? extends MeigoEntity> type, Level level) {
        super(type, level);
    }

    public MeigoEntity(Level level, LivingEntity owner) {
        super(ModEntities.MEIGO.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.MAGMA_BLOCK;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 30.0F); // Very high damage
                target.setRemainingFireTicks(300);
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public boolean deflect(net.minecraft.world.entity.projectile.ProjectileDeflection deflection, net.minecraft.world.entity.Entity entity, net.minecraft.world.entity.Entity owner, boolean deflects) {
        return false; // These elemental projectiles are too dense/volatile to deflect normally
    }
}
