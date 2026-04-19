package xyz.pixelatedw.mineminenomi.api.effects;

import net.minecraft.world.entity.LivingEntity;

public interface IVanishEffect {
   boolean isVanished(LivingEntity var1, int var2, int var3);

   boolean disableParticles();
}
