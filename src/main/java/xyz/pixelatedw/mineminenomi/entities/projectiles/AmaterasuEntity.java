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

public class AmaterasuEntity extends ThrowableItemProjectile {

    public AmaterasuEntity(EntityType<? extends AmaterasuEntity> type, Level level) {
        super(type, level);
    }

    public AmaterasuEntity(Level level, LivingEntity owner) {
        super(ModEntities.AMATERASU.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GLOWSTONE;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.END_ROD, 
                    this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 25.0F);
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 200, 1));
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
        this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 3.0F, false, Level.ExplosionInteraction.MOB);
    }
}
