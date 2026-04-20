package xyz.pixelatedw.mineminenomi.abilities.hitodaibutsu.HitoDaibutsuPointAbility;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IOverlayProvider;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class HitoDaibutsuPointAbility extends Ability implements IOverlayProvider {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hito_hito_no_mi_daibutsu");
    public HitoDaibutsuPointAbility() { super(FRUIT); }

    private static final AbilityOverlay OVERLAY = new AbilityOverlay.Builder()
            .setOverlayPart(AbilityOverlay.OverlayPart.BODY)
            .setTexture(ModResources.BUDDHA_COATING)
            .setColor(-1426063361)
            .build();

    @Override
    public AbilityOverlay getOverlay(LivingEntity entity) {
        return OVERLAY;
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive("mineminenomi:daibutsu_form", true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive("mineminenomi:daibutsu_form", false);
            stats.sync(entity);
        }
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive("mineminenomi:daibutsu_form");
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.daibutsu_form"); }
}
