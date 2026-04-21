package xyz.pixelatedw.mineminenomi.abilities.pero;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/** Candy Wave — Perospero fires a wave of candy that ensnares and slows enemies. */
public class CandyWaveAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "pero_pero_no_mi");
    public CandyWaveAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        Vec3 look = entity.getLookAngle();
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(3.0).move(look.scale(10.0)))) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 160, 5));
                living.hurt(entity.damageSources().mobAttack(entity), 6.0F);
                living.hurtMarked = true;
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.candy_wave"); }
}
