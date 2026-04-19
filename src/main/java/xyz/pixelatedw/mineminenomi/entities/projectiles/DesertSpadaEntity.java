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

public class DesertSpadaEntity extends ThrowableItemProjectile {

    public DesertSpadaEntity(EntityType<? extends DesertSpadaEntity> type, Level level) {
        super(type, level);
    }

    public DesertSpadaEntity(Level level, LivingEntity owner) {
        super(ModEntities.DESERT_SPADA.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SAND;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                this.level().addParticle(new net.minecraft.core.particles.BlockParticleOption(net.minecraft.core.particles.ParticleTypes.FALLING_DUST, net.minecraft.world.level.block.Blocks.SAND.defaultBlockState()), 
                    this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 18.0F);
                // Dehydration effect (simplified as hunger + weakness)
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.HUNGER, 200, 1));
                target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 1));
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
}
