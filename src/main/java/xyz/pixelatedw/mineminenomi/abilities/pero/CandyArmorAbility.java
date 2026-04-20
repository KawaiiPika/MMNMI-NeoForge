package xyz.pixelatedw.mineminenomi.abilities.pero;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;

import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IOverlayProvider;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;


/** Candy Armor — Perospero coats themselves in hardened candy, gaining damage resistance. */
public class CandyArmorAbility extends Ability implements IOverlayProvider {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "pero_pero_no_mi");
    public CandyArmorAbility() { super(FRUIT); }

    private static final AbilityOverlay OVERLAY = new AbilityOverlay.Builder()
            .setTexture(ModResources.CANDY_ARMOR)
            .setColor(WyHelper.hexToRGB("#FFFFFF99"))
            .build();

    @Override
    public AbilityOverlay getOverlay(LivingEntity entity) {
        return OVERLAY;
    }


    @Override
    protected void startUsing(LivingEntity entity) {
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
            net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 200, 3));
        entity.addEffect(new net.minecraft.world.effect.MobEffectInstance(
            net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.candy_armor"); }
}
