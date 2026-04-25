package xyz.pixelatedw.mineminenomi.abilities.horu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class ChiyuHormoneAbility extends Ability {

    public ChiyuHormoneAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "horu_horu_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            if (entity.isShiftKeyDown()) {
                entity.addEffect(new MobEffectInstance(ModEffects.CHIYU_HORMONE, 300, 0));
                this.startCooldown(entity, 400);
            } else {
                var trace = xyz.pixelatedw.mineminenomi.api.WyHelper.rayTraceEntities(entity, 3.0);
            Entity target = trace != null ? trace.getEntity() : null;
                if (target instanceof LivingEntity livingTarget) {
                    livingTarget.addEffect(new MobEffectInstance(ModEffects.CHIYU_HORMONE, 300, 0));
                    this.startCooldown(entity, 400);
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.chiyu_hormone");
    }
}
