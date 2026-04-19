package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SunaTravelAbility extends LogiaBlockBypassingAbility {
   public static final RegistryObject<AbilityCore<SunaTravelAbility>> INSTANCE = ModRegistry.registerAbility("suna_block_bypassing", "Suna Block Bypassing", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, SunaTravelAbility::new)).addDescriptionLine(DESCRIPTION).setIcon(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/sand.png")).build("mineminenomi"));

   public SunaTravelAbility(AbilityCore<SunaTravelAbility> core) {
      super(core);
   }

   public void spawnParticles(LivingEntity entity) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SAND_BLADE_IDLE.get(), entity, entity.m_20185_(), (double)entity.m_9236_().m_6924_(Types.OCEAN_FLOOR, (int)entity.m_20185_(), (int)entity.m_20189_()), entity.m_20189_());
   }

   public boolean canGoThrough(BlockState state) {
      return state.m_204336_(ModTags.Blocks.LOGIA_BLOCK_PASS_SUNA);
   }
}
