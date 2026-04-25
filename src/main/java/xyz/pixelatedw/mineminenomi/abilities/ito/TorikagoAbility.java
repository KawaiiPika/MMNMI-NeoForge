package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Torikago — Birdcage; creates a massive inescapable string cage.
 * Currently implemented as a mass-slow + damage pulse AoE around the user.
 */
public class TorikagoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi");
    public TorikagoAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Cage deployed — heavy slow on all nearby
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(20.0))) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 400, 3));
                living.hurt(entity.damageSources().mobAttack(entity), 4.0F);
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.torikago"); }
}
