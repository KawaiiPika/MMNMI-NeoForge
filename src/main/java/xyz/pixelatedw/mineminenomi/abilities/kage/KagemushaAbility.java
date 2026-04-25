package xyz.pixelatedw.mineminenomi.abilities.kage;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Kagemusha — Shadow Warrior; sends the user's shadow as a clone to attack.
 * Currently implemented as a fast forward shadow-strike lunge.
 */
public class KagemushaAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kage_kage_no_mi");
    public KagemushaAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        // Shadow lunge — entity briefly becomes a shadow and lunges at the target
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
            net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 10, 5));
        entity.setDeltaMovement(look.scale(3.5));
        // Hit any entity at lunge destination
        for (var target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.0).move(look.scale(5.0)), e -> e != entity)) {
            if (target instanceof LivingEntity living) {
                living.hurt(entity.damageSources().mobAttack(entity), 11.0F);
                living.setDeltaMovement(look.scale(2.0).add(0, 0.4, 0));
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.kagemusha"); }
}
