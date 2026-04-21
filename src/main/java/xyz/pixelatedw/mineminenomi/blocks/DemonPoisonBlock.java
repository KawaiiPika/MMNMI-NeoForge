package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class DemonPoisonBlock extends PoisonBlock {
   public void entityInside(BlockState state, net.minecraft.world.level.Level world, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity living) {
         if (!living.hasEffect(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.DOKU_POISON.get()))) {
            living.addEffect(new MobEffectInstance(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.DOKU_POISON.get()), 100, 2));
         }
      }

   }
}
