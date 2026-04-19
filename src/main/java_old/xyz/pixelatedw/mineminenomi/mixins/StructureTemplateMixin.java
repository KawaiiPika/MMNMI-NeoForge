package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.level.structure.processor.IgnoreWaterloggingStructureProcessor;

@Mixin({StructureTemplate.class})
public class StructureTemplateMixin {
   @Inject(
      method = {"placeInWorld"},
      at = {@At("HEAD")}
   )
   public void mineminenomi$placeInWorld(ServerLevelAccessor level, BlockPos pos1, BlockPos pos2, StructurePlaceSettings settings, RandomSource rng, int i, CallbackInfoReturnable<Boolean> cir) {
      for(StructureProcessor proc : settings.m_74411_()) {
         if (proc instanceof IgnoreWaterloggingStructureProcessor) {
            settings.m_163782_(false);
            break;
         }
      }

   }
}
