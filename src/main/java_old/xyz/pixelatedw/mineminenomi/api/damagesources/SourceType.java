package xyz.pixelatedw.mineminenomi.api.damagesources;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public enum SourceType {
   UNKNOWN((ResourceLocation)null),
   SLASH(ModResources.SOURCE_TYPE_SLASH),
   BLUNT(ModResources.SOURCE_TYPE_BLUNT),
   INDIRECT((ResourceLocation)null),
   FIST(ModResources.SOURCE_TYPE_FIST),
   PHYSICAL(ModResources.SOURCE_TYPE_PHYSICAL),
   INTERNAL(ModResources.SOURCE_TYPE_INTERNAL),
   PROJECTILE(ModResources.SOURCE_TYPE_PROJECTILE),
   BULLET(ModResources.SOURCE_TYPE_BULLET),
   FRIENDLY((ResourceLocation)null),
   LOGIA_BYPASS((ResourceLocation)null);

   private final @Nullable ResourceLocation texture;

   private SourceType(ResourceLocation texture) {
      this.texture = texture;
   }

   public @Nullable ResourceLocation getTexture() {
      return this.texture;
   }

   public String getUnlocalizedName() {
      return this.toString().toLowerCase();
   }

   // $FF: synthetic method
   private static SourceType[] $values() {
      return new SourceType[]{UNKNOWN, SLASH, BLUNT, INDIRECT, FIST, PHYSICAL, INTERNAL, PROJECTILE, BULLET, FRIENDLY, LOGIA_BYPASS};
   }
}
