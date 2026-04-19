package xyz.pixelatedw.mineminenomi.api.factions;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public interface IFactionRank {
   Component getLocalizedName();

   boolean isUnlocked(LivingEntity var1);
}
