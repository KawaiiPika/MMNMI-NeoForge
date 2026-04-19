package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class NoroNoroBeamEntity extends ThrowableItemProjectile {
    private int life = 0;
    private final int maxLife = 80;

    public NoroNoroBeamEntity(EntityType<? extends NoroNoroBeamEntity> type, Level world) {
        super(type, world);
    }

    public NoroNoroBeamEntity(Level world, LivingEntity owner) {
        super(ModEntities.NORO_NORO_BEAM.get(), owner, world);
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
            // Purple particles
            this.level().addParticle(ParticleTypes.WITCH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 9)); // Slow 10
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
