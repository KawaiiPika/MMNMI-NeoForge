package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Raigo — massive lightning orb that detonates dealing wide-area lightning damage. */
public class RaigoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi");
    public RaigoAbility() { super(FRUIT); }

    private static final int CHARGE_TICKS = 100; // 5 seconds

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                net.minecraft.sounds.SoundEvents.BEACON_ACTIVATE, 
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.3F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (entity.level().isClientSide && duration % 3 == 0) {
                 for(int i = 0; i < 10; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, 
                        entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 6, 
                        entity.getY() + 6 + (entity.getRandom().nextDouble() - 0.5) * 6, 
                        entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 6, 
                        0, 0, 0);
                 }
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                net.minecraft.world.phys.HitResult mop = entity.pick(128.0D, 0.0F, false);
                Vec3 hitPos = mop.getType() == net.minecraft.world.phys.HitResult.Type.MISS ? entity.getEyePosition().add(entity.getLookAngle().scale(128.0D)) : mop.getLocation();
                
                entity.level().explode(entity, hitPos.x, hitPos.y, hitPos.z, 12.0F, true, net.minecraft.world.level.Level.ExplosionInteraction.BLOCK);
                
                net.minecraft.world.phys.AABB aabb = new net.minecraft.world.phys.AABB(hitPos, hitPos).inflate(20.0);
                for (net.minecraft.world.entity.Entity target : entity.level().getEntities(entity, aabb)) {
                    if (target instanceof LivingEntity livingTarget) {
                        livingTarget.hurt(entity.damageSources().lightningBolt(), 50.0F);
                        net.minecraft.world.entity.LightningBolt bolt = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(entity.level());
                        if (bolt != null) {
                            bolt.moveTo(target.position());
                            entity.level().addFreshEntity(bolt);
                        }
                    }
                }
                
                this.startCooldown(entity, 2400);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.raigo"); }
}
