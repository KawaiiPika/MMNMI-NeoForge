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

public class SangoEntity extends ThrowableItemProjectile {

    public SangoEntity(EntityType<? extends SangoEntity> type, Level level) {
        super(type, level);
    }

    public SangoEntity(Level level, LivingEntity owner) {
        super(ModEntities.SANGO.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_INGOT; // Represents electricity
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, 
                this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().lightningBolt(), 20.0F);
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
