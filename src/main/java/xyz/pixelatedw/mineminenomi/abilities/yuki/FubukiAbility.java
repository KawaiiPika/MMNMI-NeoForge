package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FubukiAbility extends Ability {

    private static final int COOLDOWN = 400;
    private static final int MAX_CHARGE_TICKS = 100;
    private static final int DAMAGE_INTERVAL = 20;

    public FubukiAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "yuki_yuki_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % DAMAGE_INTERVAL == 0) {
            int multiplier = (int) (duration / DAMAGE_INTERVAL) + 1;
            int currentRange = 5 * multiplier;
            float currentDamage = 4.0F * multiplier;

            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(currentRange)).forEach(target -> {
                if (target != entity) {
                    target.hurt(entity.damageSources().mobAttack(entity), currentDamage);
                }
            });
        }

        if (entity.level().isClientSide) {
             for (int i = 0; i < 5; i++) {
                 entity.level().addParticle(net.minecraft.core.particles.ParticleTypes.SNOWFLAKE,
                     entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 10,
                     entity.getY() + entity.getRandom().nextDouble() * 5,
                     entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 10,
                     0, 0, 0);
             }
        }

        if (duration >= MAX_CHARGE_TICKS) {
            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.fubuki");
    }
}
