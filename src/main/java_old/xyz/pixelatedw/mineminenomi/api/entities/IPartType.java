package xyz.pixelatedw.mineminenomi.api.entities;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.entity.PartEntity;

public interface IPartType {
   PartEntityType<? extends PartEntity<? extends Entity>, ? extends Entity> getPartType();
}
