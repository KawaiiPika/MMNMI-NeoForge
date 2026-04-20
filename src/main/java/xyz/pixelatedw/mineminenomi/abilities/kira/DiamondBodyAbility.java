package xyz.pixelatedw.mineminenomi.abilities.kira.DiamondBodyAbility;

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

public class DiamondBodyAbility extends Ability implements IOverlayProvider {
    private static final ResourceLocation FRUIT = ResourceLocation.fromNamespaceAndPath("mineminenomi", "kira_kira_no_mi");
    public DiamondBodyAbility() { super(FRUIT); }

    private static final AbilityOverlay OVERLAY = new AbilityOverlay.Builder()
            .setOverlayPart(AbilityOverlay.OverlayPart.BODY)
            .setTexture(ModResources.DIAMOND_BODY)
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
            stats.setAbilityActive("mineminenomi:diamond_body", true);
            stats.sync(entity);
        }
    }

    @Override
    protected void stopUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats != null) {
            stats.setAbilityActive("mineminenomi:diamond_body", false);
            stats.sync(entity);
        }
    }

    @Override
    public boolean isUsing(LivingEntity entity) {
        PlayerStats stats = PlayerStats.get(entity);
        return stats != null && stats.isAbilityActive("mineminenomi:diamond_body");
    }

    @Override
    public Component getDisplayName() { return Component.translatable("ability.mineminenomi.diamond_body"); }
}
