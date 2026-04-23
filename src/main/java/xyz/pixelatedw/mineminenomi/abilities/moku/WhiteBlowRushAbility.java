package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.WhiteBlowEntity;

public class WhiteBlowRushAbility extends Ability {

    private static final int REPEATS = 8;
    private static final int TICKS_BETWEEN = 3;

    public WhiteBlowRushAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        // Handled in onTick
    }

    @Override
    protected void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide) {
            if (duration % TICKS_BETWEEN == 0) {
                WhiteBlowEntity blow = new WhiteBlowEntity(entity.level(), entity);
                blow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.75F, 4.0F);
                entity.level().addFreshEntity(blow);

                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(),
                    net.minecraft.sounds.SoundSource.PLAYERS, 0.5F, 1.5F);
            }

            if (duration >= REPEATS * TICKS_BETWEEN) {
                this.stop(entity);
                this.startCooldown(entity, 120);
            }
        } else {
             if (duration >= REPEATS * TICKS_BETWEEN) {
                 this.stop(entity);
             }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.white_blow_rush");
    }
}
