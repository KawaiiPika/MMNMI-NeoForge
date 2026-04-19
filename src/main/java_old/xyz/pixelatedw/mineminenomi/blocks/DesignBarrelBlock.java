package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;

public class DesignBarrelBlock extends Block {
   public static final DirectionProperty FACING;

   public DesignBarrelBlock() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_60978_(2.5F).m_60918_(SoundType.f_56736_));
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH));
   }

   public RenderShape m_7514_(BlockState pState) {
      return RenderShape.MODEL;
   }

   public BlockState m_6843_(BlockState pState, Rotation pRotation) {
      return (BlockState)pState.m_61124_(FACING, pRotation.m_55954_((Direction)pState.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState pState, Mirror pMirror) {
      return pState.m_60717_(pMirror.m_54846_((Direction)pState.m_61143_(FACING)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> pBuilder) {
      pBuilder.m_61104_(new Property[]{FACING});
   }

   public BlockState m_5573_(BlockPlaceContext pContext) {
      return (BlockState)this.m_49966_().m_61124_(FACING, pContext.m_7820_().m_122424_());
   }

   static {
      FACING = BlockStateProperties.f_61372_;
   }
}
