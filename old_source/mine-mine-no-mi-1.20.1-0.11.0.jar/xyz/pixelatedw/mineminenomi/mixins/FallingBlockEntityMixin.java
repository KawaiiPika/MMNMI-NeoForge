package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraftforge.common.util.BlockSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;

@Mixin({FallingBlockEntity.class})
public class FallingBlockEntityMixin {
   @Inject(
      method = {"tick"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
   shift = Shift.BEFORE
)}
   )
   public void mineminenomi$beforeLanding(CallbackInfo ci) {
      FallingBlockEntity entity = (FallingBlockEntity)this;
      if (!entity.m_9236_().f_46443_) {
         BlockPos pos = entity.m_20183_();
         Optional<ProtectedArea> area = ProtectedAreasData.get((ServerLevel)entity.m_9236_()).getProtectedArea(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
         if (area.isPresent()) {
            BlockSnapshot snapshot = BlockSnapshot.create(entity.m_9236_().m_46472_(), entity.m_9236_(), pos, 2);
            ((ProtectedArea)area.get()).queueForRestoration(pos, new ProtectedArea.RestorationEntry(entity.m_9236_().m_46467_(), snapshot));
         }
      }

   }
}
