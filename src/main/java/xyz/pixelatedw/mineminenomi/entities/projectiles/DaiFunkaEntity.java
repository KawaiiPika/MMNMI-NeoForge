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

public class DaiFunkaEntity extends ThrowableItemProjectile {

    public DaiFunkaEntity(EntityType<? extends DaiFunkaEntity> type, Level level) {
        super(type, level);
    }

    public DaiFunkaEntity(Level level, LivingEntity owner) {
        super(ModEntities.DAI_FUNKA.get(), owner, level);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.MAGMA_BLOCK;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.LAVA, 
                    this.getX() + (this.random.nextDouble() - 0.5) * 1.5, 
                    this.getY() + (this.random.nextDouble() - 0.5) * 1.5, 
                    this.getZ() + (this.random.nextDouble() - 0.5) * 1.5, 
                    0, 0, 0);
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE, 
                    this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity target && this.getOwner() instanceof LivingEntity owner) {
                target.hurt(this.damageSources().mobAttack(owner), 20.0F);
                target.setRemainingFireTicks(200);
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
        this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 4.0F, true, Level.ExplosionInteraction.MOB);
        // Place some fire/lava?
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                net.minecraft.core.BlockPos pos = this.blockPosition().offset(i, 0, j);
                if (this.level().isEmptyBlock(pos)) {
                    this.level().setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState());
                }
            }
        }
    }

    @Override
    public boolean deflect(net.minecraft.world.entity.projectile.ProjectileDeflection deflection, net.minecraft.world.entity.Entity entity, net.minecraft.world.entity.Entity owner, boolean deflects) {
        return false; // These elemental projectiles are too dense/volatile to deflect normally
    }
}
