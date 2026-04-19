package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.Mob;

public interface IMobDetails<T extends Mob> {
   void init(T var1);
}
