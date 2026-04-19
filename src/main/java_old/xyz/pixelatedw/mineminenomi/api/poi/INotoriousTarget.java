package xyz.pixelatedw.mineminenomi.api.poi;

import net.minecraft.util.RandomSource;

public interface INotoriousTarget {
   RandomSource getTrackedRandom();

   TrackedNPC getTrackedData();
}
