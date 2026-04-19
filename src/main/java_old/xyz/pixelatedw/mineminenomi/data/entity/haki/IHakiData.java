package xyz.pixelatedw.mineminenomi.data.entity.haki;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;

public interface IHakiData extends INBTSerializable<CompoundTag> {
   float getTotalHakiExp();

   float getMaxHakiExp();

   int getHakiOveruse();

   int getMaxOveruse();

   void alterHakiOveruse(int var1);

   void setHakiOveruse(int var1);

   float getKenbunshokuHakiExp();

   boolean alterKenbunshokuHakiExp(float var1, StatChangeSource var2);

   void setKenbunshokuHakiExp(float var1);

   float getBusoshokuHakiExp();

   boolean alterBusoshokuHakiExp(float var1, StatChangeSource var2);

   void setBusoshokuHakiExp(float var1);

   int getHaoshokuHakiColour();

   void setHaoshokuHakiColour(int var1);
}
