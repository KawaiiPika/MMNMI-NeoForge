package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
// // import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;

public class SkyBlockBlock extends Block {
   public static final IntegerProperty TYPE = net.minecraft.world.level.block.state.properties.IntegerProperty.create("type", 0, 3);

   public SkyBlockBlock() {
      super(BlockBehaviour.Properties.of().mapColor(net.minecraft.world.level.material.MapColor.WOOL).lightLevel((state) -> 1).strength(0.7F).requiresCorrectToolForDrops().sound(net.minecraft.world.level.block.SoundType.WOOL));
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(TYPE, 0));
   }

   public @javax.annotation.Nullable BlockState getStateForPlacement(net.minecraft.world.item.context.BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(TYPE, context.getLevel().random.nextInt(4));
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TYPE});
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public void fallOn(net.minecraft.world.level.Level level, BlockState state, BlockPos pos, Entity entity, float fallDamage) {
      entity.causeFallDamage(fallDamage, 0.05F, entity.damageSources().fall());
   }
}
