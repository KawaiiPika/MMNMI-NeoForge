package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.nbt.CompoundTag;

// Dummy for Phase 2. NBT logic port deferred to Phase 3.
public class WantedPosterData {
    public static WantedPosterData from(CompoundTag tag) {
        return new WantedPosterData();
    }

    public void checkIfExpired() {}

    public CompoundTag write() {
        return new CompoundTag();
    }
}
