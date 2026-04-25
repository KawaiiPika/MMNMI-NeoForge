package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class DaiEnkaEnteiAbility extends Ability {

    public DaiEnkaEnteiAbility() {
        super(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "mera_mera_no_mi"));
    }

    private static final int CHARGE_TICKS = 80; // 4 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 2 == 0) {
                 for(int i = 0; i < 5; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.FLAME, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 4, 
                        entity.getY() + 4 + (entity.getRandom().nextDouble() - 0.5) * 4, 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 4, 
                        0, 0, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                net.minecraft.world.phys.HitResult mop = entity.pick(64.0D, 0.0F, false);
                Vec3 hitPos = mop.getType() == net.minecraft.world.phys.HitResult.Type.MISS ? entity.getEyePosition().add(entity.getLookAngle().scale(64.0D)) : mop.getLocation();
                
                entity.level().explode(entity, hitPos.x, hitPos.y, hitPos.z, 8.0F, true, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
                
                AABB aabb = new AABB(hitPos, hitPos).inflate(12.0);
                for (Entity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                    if (target instanceof LivingEntity livingTarget) {
                        livingTarget.hurt(entity.damageSources().explosion(entity, entity), 30.0F);
                        livingTarget.setRemainingFireTicks(400);
                    }
                }
                
                this.startCooldown(entity, 1200);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.dai_enka_entei");
    }
}
