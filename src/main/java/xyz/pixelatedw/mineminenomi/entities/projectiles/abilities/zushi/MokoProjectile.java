package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class MokoProjectile extends NuProjectileEntity {

    public MokoProjectile(EntityType<? extends MokoProjectile> type, Level level) {
        super(type, level);
    }

    public MokoProjectile(Level level, LivingEntity owner, Ability ability) {
        super((net.minecraft.world.entity.EntityType<? extends xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity>)ModEntities.MOKO_PROJECTILE.get(), owner, level);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.setDeltaMovement(0.0, -5.0, 0.0);
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 100, 10, false, false));
        }
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            for(int i = 0; i < 25; ++i) {
                double offsetX = this.level().random.nextDouble() / 2.0D;
                double offsetY = this.level().random.nextDouble() / 2.0D;
                double offsetZ = this.level().random.nextDouble() / 2.0D;
                xyz.pixelatedw.mineminenomi.particles.SimpleParticleData data = new xyz.pixelatedw.mineminenomi.particles.SimpleParticleData(xyz.pixelatedw.mineminenomi.init.ModParticleTypes.GASU.get());
                if (this.level() instanceof net.minecraft.server.level.ServerLevel sl) {
                    for(net.minecraft.server.level.ServerPlayer p : sl.players()) {
                        sl.sendParticles(p, data, true, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
}
