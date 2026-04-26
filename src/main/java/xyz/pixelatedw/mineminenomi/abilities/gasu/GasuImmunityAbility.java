package xyz.pixelatedw.mineminenomi.abilities.gasu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class GasuImmunityAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "gasu_gasu_no_mi");

    public GasuImmunityAbility() {
        super(FRUIT);
    }

    @Override
    public boolean isPassive() {
        return true;
    }

    @Override
    protected void startUsing(LivingEntity entity) {}

    @Override
    public void tick(LivingEntity entity) {
        super.tick(entity);
        if (entity.hasEffect(net.minecraft.world.effect.MobEffects.POISON)) entity.removeEffect(net.minecraft.world.effect.MobEffects.POISON);
        if (entity.hasEffect(ModEffects.DOKU_POISON)) entity.removeEffect(ModEffects.DOKU_POISON);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.gasu_immunities");
    }
}
