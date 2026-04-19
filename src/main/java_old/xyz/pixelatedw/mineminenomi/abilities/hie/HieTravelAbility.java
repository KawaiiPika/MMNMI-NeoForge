package xyz.pixelatedw.mineminenomi.abilities.hie;

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

public class HieTravelAbility extends LogiaBlockBypassingAbility {
   public static final RegistryObject<AbilityCore<HieTravelAbility>> INSTANCE = ModRegistry.registerAbility("hie_block_bypassing", "Hie Block Bypassing", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, HieTravelAbility::new)).addDescriptionLine(DESCRIPTION).setIcon(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/ice.png")).build("mineminenomi"));

   public HieTravelAbility(AbilityCore<HieTravelAbility> core) {
      super(core);
   }

   public void spawnParticles(LivingEntity entity) {
   }

   public boolean canGoThrough(BlockState state) {
      return state.m_204336_(ModTags.Blocks.LOGIA_BLOCK_PASS_HIE);
   }
}
