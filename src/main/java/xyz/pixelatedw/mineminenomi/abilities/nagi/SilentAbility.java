package xyz.pixelatedw.mineminenomi.abilities.nagi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class SilentAbility extends Ability {

    public SilentAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "nagi_nagi_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public void onTick(LivingEntity entity, long duration) {
        if (!entity.level().isClientSide && duration % 15 == 0) {
            entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 20, 0, false, false));
            for (var target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(30.0))) {
                if (target instanceof LivingEntity livingTarget) {
                    // Target check for friendly faction skipped for brevity, just applies to everyone in range
                    livingTarget.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 20, 0, false, false));
                }
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.silent");
    }
}
