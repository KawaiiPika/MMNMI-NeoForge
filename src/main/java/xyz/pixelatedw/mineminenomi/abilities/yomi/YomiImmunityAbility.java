package xyz.pixelatedw.mineminenomi.abilities.yomi;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class YomiImmunityAbility extends Ability {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "yomi_yomi_no_mi");

    public YomiImmunityAbility() {
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
        if (entity.hasEffect(ModEffects.FROSTBITE)) entity.removeEffect(ModEffects.FROSTBITE);
        if (entity.hasEffect(ModEffects.DIZZY)) entity.removeEffect(ModEffects.DIZZY);
        if (entity.hasEffect(ModEffects.DRUNK)) entity.removeEffect(ModEffects.DRUNK);
        if (entity.hasEffect(ModEffects.UNCONSCIOUS)) entity.removeEffect(ModEffects.UNCONSCIOUS);
        if (entity.hasEffect(net.minecraft.world.effect.MobEffects.POISON)) entity.removeEffect(net.minecraft.world.effect.MobEffects.POISON);
        if (entity.hasEffect(net.minecraft.world.effect.MobEffects.HUNGER)) entity.removeEffect(net.minecraft.world.effect.MobEffects.HUNGER);
        if (entity.hasEffect(net.minecraft.world.effect.MobEffects.BLINDNESS)) entity.removeEffect(net.minecraft.world.effect.MobEffects.BLINDNESS);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("ability.mineminenomi.yomi_immunities");
    }
}
