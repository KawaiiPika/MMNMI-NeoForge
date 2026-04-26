package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class MokuImmunityAbility extends Ability {

    public MokuImmunityAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "moku_moku_no_mi"));
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (entity.hasEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN)) {
            entity.removeEffect(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN);
        }
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.moku_immunity");
    }
}
