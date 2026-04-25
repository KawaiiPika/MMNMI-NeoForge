package xyz.pixelatedw.mineminenomi.abilities.nekoleopard;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class NekoNightVisionAbility extends Ability {

    public NekoNightVisionAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "neko_neko_no_mi_model_leopard"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            // Note: This is originally a passive ability checking for night.
            // We simulate it here as an active toggle for tests/pass 1.
            // Full passive capabilities should run in a PlayerTickEvent in NeoForge architecture.
            if (entity.level().isNight() && (!entity.hasEffect(MobEffects.NIGHT_VISION) || entity.getEffect(MobEffects.NIGHT_VISION).getDuration() <= 210)) {
                entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500, 0, false, false));
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.nekonightvision");
    }
}
