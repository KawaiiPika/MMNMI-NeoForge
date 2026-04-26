package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KariAbility extends Ability {

    private static final int CHARGE_TIME = 60;
    private static final float RANGE = 4.0F;

    public KariAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "goro_goro_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Charging started
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration < CHARGE_TIME) {
                entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 2, 1, false, false, false));
            } else if (duration == CHARGE_TIME) {
                entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), RANGE, false, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
                this.stop(entity);
                this.startCooldown(entity, 240);
            }
        } else {
             if (duration < CHARGE_TIME && duration % 2 == 0) {
                 for (int i = 0; i < 3; i++) {
                     entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK,
                         entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 2,
                         entity.getY() + entity.getRandom().nextDouble() * 2,
                         entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 2,
                         0, 0, 0);
                 }
             } else if (duration == CHARGE_TIME) {
                 this.stop(entity);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.kari");
    }
}
