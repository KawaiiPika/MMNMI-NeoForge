package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public class ElThorAbility extends Ability {

    private static final int CHARGE_TICKS = 60; // 3 seconds

    public ElThorAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"));
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive(this.getId().toString());
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), true);
            stats.sync(entity);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 1.5F);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive(this.getId().toString(), false);
            stats.sync(entity);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 5 == 0) {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, 
                    entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    entity.getEyeY() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2, 
                    0, 0, 0);
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                HitResult mop = entity.pick(128.0D, 0.0F, false);
                if (mop.getType() != HitResult.Type.MISS) {
                    net.minecraft.world.entity.LightningBolt lightning = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(entity.level());
                    if (lightning != null) {
                        lightning.moveTo(mop.getLocation());
                        entity.level().addFreshEntity(lightning);
                        
                        // AOE Damage
                        entity.level().getEntitiesOfClass(LivingEntity.class, new net.minecraft.world.phys.AABB(mop.getLocation(), mop.getLocation()).inflate(6.0)).forEach(target -> {
                            if (target != entity) {
                                target.hurt(entity.damageSources().lightningBolt(), 40.0F);
                            }
                        });
                    }
                }
            }
            stopUsing(entity);
        }
    }

    private ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath("mineminenomi", "el_thor");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.el_thor");
    }
}
