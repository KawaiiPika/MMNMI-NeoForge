package xyz.pixelatedw.mineminenomi.abilities.goro;

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

public class GoroTravelAbility extends LogiaBlockBypassingAbility {
   public static final RegistryObject<AbilityCore<GoroTravelAbility>> INSTANCE = ModRegistry.registerAbility("goro_block_bypassing", "Goro Block Bypassing", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, GoroTravelAbility::new)).addDescriptionLine(DESCRIPTION).setIcon(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/gold_block.png")).build("mineminenomi"));

   public GoroTravelAbility(AbilityCore<GoroTravelAbility> core) {
      super(core);
   }

   public void spawnParticles(LivingEntity entity) {
   }

   public boolean canGoThrough(BlockState state) {
      return state.m_204336_(ModTags.Blocks.LOGIA_BLOCK_PASS_GORO);
   }
}
