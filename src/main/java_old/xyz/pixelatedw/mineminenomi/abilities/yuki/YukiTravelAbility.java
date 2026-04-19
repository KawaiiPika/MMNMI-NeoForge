package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class YukiTravelAbility extends LogiaBlockBypassingAbility {
   public static final RegistryObject<AbilityCore<YukiTravelAbility>> INSTANCE = ModRegistry.registerAbility("yuki_block_bypassing", "Yuki Block Bypassing", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, YukiTravelAbility::new)).addDescriptionLine(DESCRIPTION).setIcon(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/snow.png")).build("mineminenomi"));

   public YukiTravelAbility(AbilityCore<YukiTravelAbility> core) {
      super(core);
   }

   public void spawnParticles(LivingEntity entity) {
   }

   public boolean canGoThrough(BlockState state) {
      return state.m_204336_(ModTags.Blocks.LOGIA_BLOCK_PASS_YUKI);
   }
}
