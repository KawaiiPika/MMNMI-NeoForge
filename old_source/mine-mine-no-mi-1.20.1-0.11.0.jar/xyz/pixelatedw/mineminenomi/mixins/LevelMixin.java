package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;

@Mixin({Level.class})
public class LevelMixin {
   @Inject(
      method = {"setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/level/chunk/LevelChunk;setBlockState(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;",
   shift = Shift.AFTER
)}
   )
   public void mineminenomi$setBlock(BlockPos pos, BlockState state, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> ci) {
      Level level = (Level)this;
      if (!level.m_5776_() && level instanceof ServerLevel serverLevel) {
         if (!state.m_60795_() && NuWorld.isChallengeDimension(level)) {
            InProgressChallenge challenge = ChallengesWorldData.get().getInProgressChallengeFor(serverLevel);
            if (challenge != null) {
               challenge.trackBlockPos(pos);
            }
         }
      }

   }
}
