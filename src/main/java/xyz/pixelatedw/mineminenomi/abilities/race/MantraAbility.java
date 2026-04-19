package xyz.pixelatedw.mineminenomi.abilities.race;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MantraAbility extends Ability {

    @Override
    protected void startUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.mantra.start"));
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.mantra.stop"));
        }
    }

    @Override
    public float onHurt(LivingEntity entity, DamageSource source, float amount) {
        if (this.isUsing(entity)) {
            if (entity.getRandom().nextFloat() < 0.2F) { // 20% dodge chance
                if (!entity.level().isClientSide) {
                    xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper.sendAbilityMessage(entity, Component.translatable("ability.mineminenomi.mantra.dodge"));
                }
                return 0.0F;
            }
        }
        return amount;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.mantra");
    }
}
