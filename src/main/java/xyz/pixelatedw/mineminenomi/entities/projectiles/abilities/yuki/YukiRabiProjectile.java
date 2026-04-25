package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class YukiRabiProjectile extends NuProjectileEntity {

    public YukiRabiProjectile(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
        this.setDamage(3.5F);
    }

    public YukiRabiProjectile(Level level, LivingEntity owner, Ability ability) {
        super(level, owner, ability);
        this.setDamage(3.5F);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(ModEffects.FROSTBITE, 100, 1));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }
}
