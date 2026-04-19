package xyz.pixelatedw.mineminenomi.abilities.suke;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

public class SukePunchAbility extends Ability {

    public SukePunchAbility() {
        super(ResourceLocation.fromNamespaceAndPath("mineminenomi", "suke_suke_no_mi"));
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.INVISIBILITY, 100, 0));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.suke_punch");
    }
}
