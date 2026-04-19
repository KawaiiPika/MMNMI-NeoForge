package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import xyz.pixelatedw.mineminenomi.api.enums.CanvasSize;

public class BlocksHelper {
   public static final EnumProperty<CanvasSize> CANVAS_SIZE = EnumProperty.m_61587_("canvas_size", CanvasSize.class);
   public static final IntegerProperty AGE_4 = IntegerProperty.m_61631_("age", 0, 4);

   public static boolean always(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public static boolean never(BlockState state, BlockGetter reader, BlockPos pos) {
      return false;
   }

   public static boolean never(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entityType) {
      return false;
   }

   public static boolean always(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entityType) {
      return true;
   }
}
