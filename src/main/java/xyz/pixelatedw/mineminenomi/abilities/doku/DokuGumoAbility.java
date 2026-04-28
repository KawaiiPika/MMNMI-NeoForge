package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class DokuGumoAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "doku_doku_no_mi");
    private static final int DURATION = 200;

    public DokuGumoAbility() {
        super(FRUIT);
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
            net.minecraft.sounds.SoundEvents.SLIME_SQUISH,
            net.minecraft.sounds.SoundSource.PLAYERS, 2.0F, 0.5F);
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(6.0)).forEach(target -> {
                if (target != entity) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 60, 0));
                }
            });
        }

        if (duration >= DURATION) {
            this.stop(entity);
            this.startCooldown(entity, 300);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.doku_gumo");
    }
}
