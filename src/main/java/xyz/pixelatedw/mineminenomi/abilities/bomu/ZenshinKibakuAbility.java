package xyz.pixelatedw.mineminenomi.abilities.bomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class ZenshinKibakuAbility extends Ability {

    private static final int CHARGE_TICKS = 100;

    public ZenshinKibakuAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "bomu_bomu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Sounds or particles for starting
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (!this.isUsing(entity)) return; // Short-circuit if cancelled early

            // To emulate charge logic: stopping early is possible if user triggers stop (e.g., via key release),
            // but for simplicity we will just execute based on duration when duration == CHARGE_TICKS.
            // In a more complex system we would track early release, but standard Abilities just finish when duration completes.

            if (duration >= CHARGE_TICKS) {
                float power = 10.0F; // Max charge

                // Explode
                entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), power, net.minecraft.world.level.Level.ExplosionInteraction.MOB);

                this.stop(entity);
                this.startCooldown(entity, 140);
            }
        } else {
            if (duration < CHARGE_TICKS) {
                if (duration % 5 == 0) {
                     for(int i = 0; i < 3; i++) {
                         entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.LAVA,
                            entity.getX() + (entity.getRandom().nextDouble() - 0.5),
                            entity.getY() + entity.getRandom().nextDouble() * 2,
                            entity.getZ() + (entity.getRandom().nextDouble() - 0.5),
                            0, 0, 0);
                     }
                }
            } else {
                this.stop(entity);
            }
        }
    }

    // Override stopUsing to handle early detonation if user cancels early (optional, but good for charge abilities)
    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
             long currentDuration = getDuration(entity);
             if (currentDuration > 10 && currentDuration < CHARGE_TICKS) { // If held for at least a little bit
                 float percent = (float) currentDuration / CHARGE_TICKS;
                 float power = percent * 10.0F;
                 float cooldown = currentDuration + 40.0F;

                 entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), power, net.minecraft.world.level.Level.ExplosionInteraction.MOB);
                 this.startCooldown(entity, (long) cooldown);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.zenshin_kibaku");
    }
}
