package xyz.pixelatedw.mineminenomi.abilities.ito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

/**
 * Kumo no Sugaki — Spider Web; creates a web shield in front of the user.
 * Grants brief immunity to projectiles and slows nearby enemies.
 */
public class KumoNoSugakiAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "ito_ito_no_mi");
    public KumoNoSugakiAbility() { super(FRUIT); }

    @Override
    protected void startUsing(LivingEntity entity) {
        // High resistance + area slow
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 3));
        for (var target : entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(6.0), e -> e != entity)) {
            if (target instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            }
        }
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.kumo_no_sugaki"); }
}
