package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Barrier Ball — user encases themselves in a spherical barrier.
 * Grants extreme resistance and prevents movement.
 */
public class BarrierBallAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "bari_bari_no_mi");
    public BarrierBallAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // High level resistance + slowness to simulate being in a ball
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 10));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.barrier_ball"); }
}
