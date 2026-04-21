package xyz.pixelatedw.mineminenomi.abilities.zou;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class GreatStompAbility extends Ability {

    private static final int COOLDOWN = 240;
    private static final float RANGE = 10.0F;
    private static final float DAMAGE = 15.0F;

    public GreatStompAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "zou_zou_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(RANGE)).forEach(target -> {
                if (target != entity) {
                    if (target.hurt(entity.damageSources().mobAttack(entity), DAMAGE)) {
                        AbilityHelper.setDeltaMovement(target, 0, 1.5, 0);
                    }
                }
            });

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(),
                net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.8F);

            this.startCooldown(entity, COOLDOWN);
            this.stop(entity);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.great_stomp");
    }
}
