package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;

public interface IOverlayProvider {
    AbilityOverlay getOverlay(LivingEntity entity);
}
