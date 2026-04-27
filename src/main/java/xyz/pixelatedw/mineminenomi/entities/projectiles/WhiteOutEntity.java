package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class WhiteOutEntity extends ThrowableItemProjectile {
    private int life = 0;
    private final int maxLife = 60;

    public WhiteOutEntity(EntityType<? extends WhiteOutEntity> type, Level world) {
        super(type, world);
    }

    public WhiteOutEntity(Level world, LivingEntity owner) {
        super(ModEntities.WHITE_OUT.get(), owner, world);
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
            for (int i = 0; i < 3; i++) {
                this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, 
                    this.getX() + (this.random.nextDouble() - 0.5) * 0.5, 
                    this.getY() + (this.random.nextDouble() - 0.5) * 0.5, 
                    this.getZ() + (this.random.nextDouble() - 0.5) * 0.5, 
                    0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(ModEffects.MOVEMENT_BLOCKED, 100, 0));
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
