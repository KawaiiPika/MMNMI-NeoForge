package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class FlashAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "pika_pika_no_mi");
    public FlashAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Blinds everyone nearby
        for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(10.0))) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 100, 0));
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.flash"); }
}
