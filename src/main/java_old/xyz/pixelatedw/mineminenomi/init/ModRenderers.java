package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class ModRenderers {
   public static void init() {
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.OPE.get(), RenderType.m_110466_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.BARRIER.get(), RenderType.m_110466_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.STRING_WALL.get(), RenderType.m_110463_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.KAIROSEKI_BARS.get(), RenderType.m_110463_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.CANDY.get(), RenderType.m_110466_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.CUSTOM_SPAWNER.get(), RenderType.m_110463_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.ORI_BARS.get(), RenderType.m_110463_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.FLAG.get(), RenderType.m_110463_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.MANGROVE_LEAVES.get(), RenderType.m_110463_());
      ItemBlockRenderTypes.setRenderLayer((Block)ModBlocks.TANGERINE_CROP.get(), RenderType.m_110463_());
   }
}
