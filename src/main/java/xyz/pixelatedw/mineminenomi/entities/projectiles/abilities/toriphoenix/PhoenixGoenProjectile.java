package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.toriphoenix;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticleData;

public class PhoenixGoenProjectile extends AbilityProjectile {
    private Vec3 lookVec;

    public PhoenixGoenProjectile(EntityType<? extends PhoenixGoenProjectile> type, Level level) {
        super(type, level);
    }

    public PhoenixGoenProjectile(Level level, LivingEntity owner, Vec3 lookVec) {
        super(xyz.pixelatedw.mineminenomi.init.ModEntities.PHOENIX_GOEN.get(), owner, level);
        this.setDamage(10.0F);
        this.setMaxLife(30);
        this.lookVec = lookVec;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target && this.lookVec != null) {
            Vec3 speed = this.lookVec.multiply(1.5, 0.0, 1.5);
            target.setDeltaMovement(target.getDeltaMovement().add(speed.x, 0.15, speed.z));
            target.fallDistance = 0.0F;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            float mult = (float) this.life / this.maxLife * 1.25F;
            for (int i = 0; i < 25.0F * mult; ++i) {
                double offsetX = (this.random.nextDouble() - 0.5) * mult * 2;
                double offsetY = (this.random.nextDouble() - 0.5) * mult * 2;
                double offsetZ = (this.random.nextDouble() - 0.5) * mult * 2;

                SimpleParticleData data = new SimpleParticleData(ModParticleTypes.BLUE_FLAME.get());
                data.setLife(8);
                data.setSize(3.0F * mult);
                this.level().addParticle(data, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 0.0, 0.0, 0.0);
            }
        }
    }
}
