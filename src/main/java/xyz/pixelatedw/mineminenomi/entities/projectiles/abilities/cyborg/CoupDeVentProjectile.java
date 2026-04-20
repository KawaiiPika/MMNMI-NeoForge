package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.AbilityProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class CoupDeVentProjectile extends AbilityProjectile {

    public CoupDeVentProjectile(EntityType<? extends CoupDeVentProjectile> type, Level world) {
        super(type, world);
    }

    public CoupDeVentProjectile(Level world, LivingEntity owner) {
        super(ModEntities.COUP_DE_VENT.get(), owner, world);
        this.setDamage(50.0F);
        this.setMaxLife(15);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide() && result.getEntity() instanceof LivingEntity target) {
            double xPower = (this.random.nextDouble() - 0.5) * 4.0;
            double zPower = (this.random.nextDouble() - 0.5) * 4.0;

            target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 5));
            target.setDeltaMovement(xPower, 2.0, zPower);
            target.hasImpulse = true;
            target.fallDistance = 0.0F;
        }
    }
}
