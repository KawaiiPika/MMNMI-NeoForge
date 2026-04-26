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

public class WhiteBlowEntity extends ThrowableItemProjectile {

    public WhiteBlowEntity(EntityType<? extends WhiteBlowEntity> type, Level level) {
        super(type, level);
    }

    public WhiteBlowEntity(Level level, LivingEntity owner) {
        super(ModEntities.WHITE_BLOW.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.WHITE_WOOL; // Represents smoke
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.CAMPFIRE_COSY_SMOKE, 
                    this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 12.0F);
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
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
