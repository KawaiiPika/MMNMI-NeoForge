package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.BlocksHelper;

public class SkyBlockBlock extends Block {
   public static final IntegerProperty TYPE = IntegerProperty.m_61631_("type", 0, 3);

   public SkyBlockBlock() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283779_).m_60953_((state) -> 1).m_60978_(0.7F).m_60999_().m_60918_(SoundType.f_56745_).m_60922_(BlocksHelper::never));
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(TYPE, 0));
   }

   public @Nullable BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(TYPE, context.m_43725_().f_46441_.m_188503_(4));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{TYPE});
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public void m_142072_(Level level, BlockState state, BlockPos pos, Entity entity, float fallDamage) {
      entity.m_142535_(fallDamage, 0.05F, entity.m_269291_().m_268989_());
   }
}
