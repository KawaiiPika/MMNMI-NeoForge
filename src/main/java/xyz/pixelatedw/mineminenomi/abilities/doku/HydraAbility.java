package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Hydra — Magellan's signature massive three-headed poison hydra.
 * Large AoE, heavy poison + direct damage.
 */
public class HydraAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doku_doku_no_mi");
    public HydraAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Triple-head bite: hit AoE in front, above-left, and above-right
        var look = entity.getLookAngle();
        double[][] offsets = {
            {look.x, look.y, look.z},                           // center head
            {-look.z * 0.7 + look.x * 0.3, 0.4, look.x * 0.7 + look.z * 0.3},  // left head
            {look.z * 0.7 + look.x * 0.3, 0.4, -look.x * 0.7 + look.z * 0.3}   // right head
        };
        for (double[] off : offsets) {
            var dir = new net.minecraft.world.phys.Vec3(off[0], off[1], off[2]).normalize().scale(8.0);
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(dir))) {
                if (target instanceof LivingEntity living) {
                    living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.POISON, 400, 3));
                    living.hurt(entity.damageSources().mobAttack(entity), 10.0F);
                }
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.hydra"); }
}
