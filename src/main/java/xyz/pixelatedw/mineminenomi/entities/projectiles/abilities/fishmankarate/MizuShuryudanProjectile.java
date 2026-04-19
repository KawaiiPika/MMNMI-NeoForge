package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

import java.util.List;

public class MizuShuryudanProjectile extends AbilityProjectile {

    private LivingEntity target;

    public MizuShuryudanProjectile(EntityType<? extends MizuShuryudanProjectile> type, Level world) {
        super(type, world);
    }

    public MizuShuryudanProjectile(Level world, LivingEntity owner) {
        super(ModEntities.MIZU_SHURYUDAN.get(), owner, world);
        this.setDamage(7.0F);
        this.setMaxLife(400);
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            return;
        }

        if (this.target != null && this.target.isAlive()) {
            Vec3 dir = this.target.position().add(0, this.target.getBbHeight() / 2.0, 0).subtract(this.position()).normalize();
            this.setDeltaMovement(this.getDeltaMovement().lerp(dir.scale(0.8), 0.2));
        } else {
            List<LivingEntity> nearby = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0), 
                e -> e != this.getOwner() && e.isAlive());
            if (!nearby.isEmpty()) {
                this.target = nearby.get(0);
            }
            
            if (this.tickCount % 5 == 0) {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.7));
            }
        }
    }
}
