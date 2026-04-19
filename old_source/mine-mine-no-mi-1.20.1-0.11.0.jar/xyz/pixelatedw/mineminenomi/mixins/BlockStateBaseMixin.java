package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

@Mixin({BlockBehaviour.BlockStateBase.class})
public abstract class BlockStateBaseMixin {
   @Shadow
   protected abstract BlockState m_7160_();

   @Inject(
      method = {"randomTick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$randomTick(ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
      if (NuWorld.isChallengeDimension(level)) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mineminenomi$getCollisionShape(BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
      if (context instanceof EntityCollisionContext collisionContext) {
         Entity entity = collisionContext.m_193113_();
         if (entity instanceof LivingEntity) {
            boolean flagAllowedToGoThrough = LogiaBlockBypassingAbility.isAllowedToGoThrough((LivingEntity)entity, this.m_7160_());
            boolean flagCrouching = entity.m_20161_();
            boolean flagLower = (double)pos.m_123342_() > entity.m_20186_() - (double)1.0F;
            if (flagAllowedToGoThrough && (flagCrouching || flagLower)) {
               cir.setReturnValue(Shapes.m_83040_());
            }
         }
      }

   }
}
