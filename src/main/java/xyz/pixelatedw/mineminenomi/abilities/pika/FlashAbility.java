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
        if (!entity.level().isClientSide) {
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(10.0)).forEach(target -> {
                if (target != entity) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 100, 0));
                }
            });
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.flash"); }
}
