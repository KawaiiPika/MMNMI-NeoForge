package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.world.damagesource.DamageSource;

public interface ILivingEntityExtension {
    default void onDamageTake(DamageSource source, float amount) {}
}
