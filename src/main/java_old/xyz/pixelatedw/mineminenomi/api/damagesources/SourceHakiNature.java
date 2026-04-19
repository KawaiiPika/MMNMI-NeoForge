package xyz.pixelatedw.mineminenomi.api.damagesources;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public enum SourceHakiNature {
   UNKNOWN((ResourceLocation)null),
   HARDENING(ModResources.SOURCE_HAKI_HARDENING),
   IMBUING(ModResources.SOURCE_HAKI_IMBUING),
   SPECIAL(ModResources.SOURCE_HAKI_SPECIAL);

   private final @Nullable ResourceLocation texture;

   private SourceHakiNature(ResourceLocation texture) {
      this.texture = texture;
   }

   public @Nullable ResourceLocation getTexture() {
      return this.texture;
   }

   public String getUnlocalizedName() {
      return this.toString().toLowerCase();
   }

   // $FF: synthetic method
   private static SourceHakiNature[] $values() {
      return new SourceHakiNature[]{UNKNOWN, HARDENING, IMBUING, SPECIAL};
   }
}
