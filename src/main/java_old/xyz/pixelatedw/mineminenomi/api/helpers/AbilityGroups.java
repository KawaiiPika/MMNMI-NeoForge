package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class AbilityGroups {
   private static final List<ResourceLocation> FRUIT_GROUPS = new ArrayList();
   private static final HashMap<ResourceLocation, Set<RegistryObject<? extends AbilityCore<?>>>> STANDLONE_GROUPS = new HashMap();
   private static final List<ResourceLocation> ALL_IDS = new ArrayList();
   private static final List<String> ALL_STRING_IDS = new ArrayList();

   public static void addFruitGroup(ResourceLocation id) {
      FRUIT_GROUPS.add(id);
      ALL_IDS.add(id);
      ALL_STRING_IDS.add(id.toString());
   }

   public static void addStandaloneGroup(ResourceLocation id, Set<RegistryObject<? extends AbilityCore<?>>> abilities) {
      STANDLONE_GROUPS.put(id, abilities);
      ALL_IDS.add(id);
      ALL_STRING_IDS.add(id.toString());
   }

   public static List<ResourceLocation> getAllIds() {
      return ALL_IDS;
   }

   public static List<String> getAllStringIds() {
      return ALL_STRING_IDS;
   }

   public static Set<RegistryObject<? extends AbilityCore<?>>> getAbilities(ResourceLocation id) {
      if (!FRUIT_GROUPS.isEmpty()) {
         for(ResourceLocation fid : FRUIT_GROUPS) {
            if (fid.equals(id)) {
               Item item = (Item)ForgeRegistries.ITEMS.getValue(id);
               if (item instanceof AkumaNoMiItem) {
                  AkumaNoMiItem fruit = (AkumaNoMiItem)item;
                  return fruit.getRegistryAbilities();
               }
            }
         }
      }

      if (!STANDLONE_GROUPS.isEmpty()) {
         for(Map.Entry<ResourceLocation, Set<RegistryObject<? extends AbilityCore<?>>>> entry : STANDLONE_GROUPS.entrySet()) {
            if (((ResourceLocation)entry.getKey()).equals(id)) {
               return (Set)entry.getValue();
            }
         }
      }

      return Sets.newHashSet();
   }
}
