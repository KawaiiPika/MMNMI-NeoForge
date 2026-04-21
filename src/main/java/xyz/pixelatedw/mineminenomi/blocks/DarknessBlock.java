package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import xyz.pixelatedw.mineminenomi.abilities.yami.BlackHoleAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
// // import xyz.pixelatedw.mineminenomi.data.entity.IAbilityData;
// // import xyz.pixelatedw.mineminenomi.data.entity.IDevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class DarknessBlock extends TrapAbilityBlock {
   public DarknessBlock() {
      super(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.COLOR_BLACK).noOcclusion().strength(-1.0F, 3600000.0F));
      this.setDamageDealt(6);
      this.setHorizontalFallSpeed(0.03);
      this.setVerticalFallSpeed(0.3);
      this.setDamageSource((level) -> level.damageSources().magic());
      this.setPotionEffect(() -> new MobEffectInstance(net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)ModEffects.MOVEMENT_BLOCKED.get()), 2, 0, false, false, false));
   }

   public boolean canWalkOn(LivingEntity entity) {
      boolean hasFruit = false; // DevilFruitCapability.hasDevilFruit(entity, ModFruits.YAMI_YAMI_NO_MI);
      boolean hasAbility = false; // AbilityCapability.hasUnlockedAbility(entity, (AbilityCore)BlackHoleAbility.INSTANCE.get());
      return hasFruit || hasAbility;
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
      return super.updateShape(state, facing, facingState, world, pos, facingPos);
   }

   public boolean canSurvive(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
      return world.getBlockState(pos.below()).getBlock() == ModBlocks.DARKNESS.get() ? true : Block.canSupportCenter(world, pos.below(), Direction.DOWN);
   }
}
