package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class KairosekiBulletProjectile extends NuProjectileEntity {
    public KairosekiBulletProjectile(EntityType<? extends KairosekiBulletProjectile> type, Level world) {
        super(type, world);
    }

    public KairosekiBulletProjectile(Level world, LivingEntity thrower) {
        super(ModEntities.KAIROSEKI_BULLET.get(), thrower, world);
        this.setDamage(8.0F);
    }

    @Override
    protected void onHitEntity(EntityHitResult target) {
        super.onHitEntity(target);
        if (!this.level().isClientSide() && target.getEntity() instanceof LivingEntity livingTarget) {
            // Kairoseki effect: Disable abilities briefly
            PlayerStats stats = PlayerStats.get(livingTarget);
            if (stats != null) {
                // For now, give them the handcuffed effect to simulate seastone suppression
                livingTarget.addEffect(new net.minecraft.world.effect.MobEffectInstance(xyz.pixelatedw.mineminenomi.init.ModEffects.HANDCUFFED_KAIROSEKI, 200, 0));
            }
        }
    }
}
