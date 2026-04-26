package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class OnnaHormoneAbility extends Ability {

    public OnnaHormoneAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "horu_horu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            if (entity.isShiftKeyDown()) {
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 2));
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 300, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0));
                this.startCooldown(entity, 400);
            } else {
                var trace = xyz.pixelatedw.mineminenomi.api.WyHelper.rayTraceEntities(entity, 3.0);
            Entity target = trace != null && !trace.isEmpty() ? trace.get(0) : null;
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 2));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 300, 1));
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 0));
                    this.startCooldown(entity, 400);
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.onna_hormone");
    }
}
