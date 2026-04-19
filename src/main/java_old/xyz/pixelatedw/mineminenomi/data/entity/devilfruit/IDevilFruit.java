package xyz.pixelatedw.mineminenomi.data.entity.devilfruit;

import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public interface IDevilFruit extends INBTSerializable<CompoundTag> {
   int getVersion();

   Optional<ResourceLocation> getDevilFruit();

   Item getDevilFruitItem();

   void setDevilFruit(@Nullable ResourceLocation var1);

   void setDevilFruit(AkumaNoMiItem var1);

   boolean hasAnyDevilFruit();

   boolean hasDevilFruit(RegistryObject<AkumaNoMiItem> var1);

   boolean hasDevilFruit(AkumaNoMiItem var1);

   void removeDevilFruit();

   boolean isLogia();

   boolean hasYamiPower();

   void setYamiPower(boolean var1);

   boolean hasYomiPower();

   void setYomiPower(boolean var1);

   boolean hasAwakenedFruit();

   void setAwakenedFruit(boolean var1);

   Optional<MorphInfo> getCurrentMorph();

   Set<MorphInfo> getActiveMorphs();

   boolean hasMorphInQueue(MorphInfo var1);

   void addMorph(MorphInfo var1);

   void removeMorph(MorphInfo var1);

   void removeMorph();

   void clearMorphs();

   boolean hasStrongWaterWeakness();

   void setStrongWaterWeakness(boolean var1);

   boolean hasWeakWaterWeakness();

   void setWeakWaterWeakness(boolean var1);

   boolean hasKairosekiWeakness();

   void setKairosekiWeakness(boolean var1);
}
