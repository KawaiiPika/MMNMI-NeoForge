package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Ground Death — Crocodile's moisture-draining instant kill grab. Heavily damages one target. */
public class GroundDeathAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "suna_suna_no_mi");
    public GroundDeathAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(2.0).move(look.scale(3.0)))) {
            if (living != entity) {
                // Moisture drain — massive single-target damage
                living.hurt(entity.damageSources().mobAttack(entity), 25.0F);
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 3));
                living.hurtMarked = true;
                return;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.ground_death"); }
}
