package xyz.pixelatedw.mineminenomi.api.protection;

import net.minecraftforge.common.Tags.Blocks;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class DefaultProtectionRules {
   public static final BlockProtectionRule FOLIAGE;
   public static final BlockProtectionRule AIR;
   public static final BlockProtectionRule AIR_FOLIAGE;
   public static final BlockProtectionRule AIR_FOLIAGE_LIQUID;
   public static final BlockProtectionRule CORE;
   public static final BlockProtectionRule CORE_FOLIAGE;
   public static final BlockProtectionRule CORE_FOLIAGE_ORE;
   public static final BlockProtectionRule CORE_FOLIAGE_ORE_LIQUID;
   public static final BlockProtectionRule CORE_FOLIAGE_ORE_LIQUID_AIR;

   static {
      FOLIAGE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_FOLIAGE).build();
      AIR = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_AIR).build();
      AIR_FOLIAGE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{AIR})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_FOLIAGE).build();
      AIR_FOLIAGE_LIQUID = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{AIR_FOLIAGE})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_LIQUIDS).build();
      CORE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_CORE).build();
      CORE_FOLIAGE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{CORE})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_FOLIAGE).build();
      CORE_FOLIAGE_ORE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{CORE_FOLIAGE})).addApprovedTags(Blocks.ORES).build();
      CORE_FOLIAGE_ORE_LIQUID = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{CORE_FOLIAGE_ORE})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_LIQUIDS).build();
      CORE_FOLIAGE_ORE_LIQUID_AIR = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{CORE_FOLIAGE_ORE_LIQUID})).addApprovedTags(ModTags.Blocks.BLOCK_PROT_AIR).build();
   }
}
