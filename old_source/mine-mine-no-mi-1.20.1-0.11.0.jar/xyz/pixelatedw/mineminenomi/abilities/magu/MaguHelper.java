package xyz.pixelatedw.mineminenomi.abilities.magu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class MaguHelper {
   public static final BlockProtectionRule GRIEF_RULE;

   public static void generateLavaPool(Level level, BlockPos pos, int size) {
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_49991_.m_49966_()).setRule(GRIEF_RULE).setSize(size);
      placer.generate(level, pos, BlockGenerators.SPHERE);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE_ORE})).addBannedTags(ModTags.Blocks.BLOCK_PROT_LAVA_IMMUNE).build();
   }
}
