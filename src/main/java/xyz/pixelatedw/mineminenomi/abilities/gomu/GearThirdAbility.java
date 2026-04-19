package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Gear Third — giant balloon limb, charges up then deals massive area damage. */
public class GearThirdAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gomu_gomu_no_mi");
    public GearThirdAbility() { super(FRUIT); }

    private static final int CHARGE_TICKS = 20; // 1 second

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
                xyz.pixelatedw.mineminenomi.init.ModSounds.GEAR_THIRD_SFX.get(), 
                net.minecraft.sounds.SoundSource.PLAYERS, 1.5F, 1.0F);
        }
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (duration < CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 5, 2, false, false));
            }
        } else if (duration == CHARGE_TICKS) {
            if (!entity.level().isClientSide) {
                Vec3 look = entity.getLookAngle();
                for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(6.0).move(look.scale(3.0)))) {
                    if (target instanceof LivingEntity living) {
                        living.hurt(entity.damageSources().mobAttack(entity), 22.0F);
                        living.setDeltaMovement(look.scale(3.0).add(0, 0.4, 0));
                        living.hurtMarked = true;
                    }
                }
                
                this.startCooldown(entity, 300);
            } else {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.EXPLOSION, 
                    entity.getX() + entity.getLookAngle().x * 3, 
                    entity.getY() + 1, 
                    entity.getZ() + entity.getLookAngle().z * 3, 
                    0, 0, 0);
            }
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.gear_third"); }
}
