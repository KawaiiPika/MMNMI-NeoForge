package xyz.pixelatedw.mineminenomi.abilities.kilo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionHand;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class KiloPunch5000Ability extends Ability {

    public KiloPunch5000Ability() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "kilo_kilo_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false));
            entity.swing(InteractionHand.MAIN_HAND, true);

            var aabb = entity.getBoundingBox().inflate(3.0).move(entity.getLookAngle().scale(1.5));
            for (LivingEntity target : entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity)) {
                target.hurt(entity.damageSources().mobAttack(entity), 20.0F);
            }
            this.startCooldown(entity, 140);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.5000_kilo_punch");
    }
}
