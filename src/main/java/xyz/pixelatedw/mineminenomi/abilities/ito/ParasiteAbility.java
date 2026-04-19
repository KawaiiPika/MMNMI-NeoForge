package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Parasite — string threads attached to an enemy, controlling their movement. Implemented as heavy slow + weak pull. */
public class ParasiteAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi");
    public ParasiteAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(2.0).move(look.scale(12.0)))) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 5));
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WEAKNESS, 200, 2));
                // Pull toward user
                Vec3 pull = entity.position().subtract(living.position()).normalize().scale(1.5);
                living.setDeltaMovement(pull);
                living.hurtMarked = true;
                return;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.parasite"); }
}
