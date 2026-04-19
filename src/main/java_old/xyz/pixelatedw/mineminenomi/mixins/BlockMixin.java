package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;

@Mixin({Block.class})
public class BlockMixin {
   @Inject(
      method = {"popResource"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void mineminenomi$popResource(Level level, BlockPos pos, ItemStack stack, CallbackInfo ci) {
      if (!level.f_46443_) {
         if (NuWorld.isChallengeDimension(level)) {
            ci.cancel();
            return;
         }

         Optional<ProtectedArea> area = ProtectedAreasData.get((ServerLevel)level).getProtectedArea(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
         if (area.isPresent() && ((ProtectedArea)area.get()).canDestroyBlocks() && ((ProtectedArea)area.get()).canRestoreBlocks()) {
            ci.cancel();
            return;
         }
      }

   }
}
