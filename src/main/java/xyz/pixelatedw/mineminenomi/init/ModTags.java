package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ModTags {

   public static class DamageTypes {
      public static final TagKey<DamageType> BYPASSES_LOGIA = tag("bypasses_logia");
      public static final TagKey<DamageType> BYPASSES_FRIENDLY_PROTECTION = tag("bypasses_friendly_protection");
      public static final TagKey<DamageType> UNAVOIDABLE = tag("unavoidable");

      private static TagKey<DamageType> tag(String id) {
         return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }
   }
}
