package xyz.pixelatedw.mineminenomi.abilities.hie.FrostbiteAbility;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IOverlayProvider;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import java.awt.Color;

public class FrostbiteAbility extends Ability implements IOverlayProvider {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "hie_hie_no_mi");
    public FrostbiteAbility() { super(FRUIT); }

    private static final AbilityOverlay OVERLAY = new AbilityOverlay.Builder()
            .setOverlayPart(AbilityOverlay.OverlayPart.BODY)
            .setTexture(ModResources.FROSTBITE_1)
            .setColor(WyHelper.hexToRGB("#FFFFFFAA"))
            .build();

    @Override
    public AbilityOverlay getOverlay(LivingEntity entity) {
        return OVERLAY;
    }

    @Override
    protected void startUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive("mineminenomi:frostbite", true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive("mineminenomi:frostbite", false);
            stats.sync(entity);
        }
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive("mineminenomi:frostbite");
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.frostbite"); }
}
