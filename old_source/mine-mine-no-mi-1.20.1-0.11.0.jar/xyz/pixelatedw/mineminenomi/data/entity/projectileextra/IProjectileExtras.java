package xyz.pixelatedw.mineminenomi.data.entity.projectileextra;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public interface IProjectileExtras extends INBTSerializable<CompoundTag> {
   void setProjectileBusoshokuImbued(boolean var1);

   boolean isProjectileBusoshokuImbued();

   void setProjectileBusoshokuShrouded(boolean var1);

   boolean isProjectileBusoshokuShrouded();

   void setProjectileHaoshokuInfused(boolean var1);

   boolean isProjectileHaoshokuInfused();

   void setWeaponUsed(ItemStack var1);

   ItemStack getWeaponUsed();
}
