package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class UchimizuProjectile extends AbilityProjectile {

    public UchimizuProjectile(EntityType<? extends UchimizuProjectile> type, Level world) {
        super(type, world);
    }

    public UchimizuProjectile(Level world, LivingEntity owner) {
        super(ModEntities.UCHIMIZU.get(), owner, world);
        this.setDamage(5.0F);
        this.setMaxLife(30);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.1);
            serverLevel.sendParticles(ParticleTypes.SPLASH, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.1);
        }
    }
}
