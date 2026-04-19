package xyz.pixelatedw.mineminenomi.abilities.pika;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
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

public class PikaTravelAbility extends LogiaBlockBypassingAbility {
   public static final RegistryObject<AbilityCore<PikaTravelAbility>> INSTANCE = ModRegistry.registerAbility("pika_block_bypassing", "Pika Block Bypassing", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AbilityType.PASSIVE, PikaTravelAbility::new)).addDescriptionLine(DESCRIPTION).setIcon(ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/glass.png")).build("mineminenomi"));

   public PikaTravelAbility(AbilityCore<PikaTravelAbility> core) {
      super(core);
   }

   public void spawnParticles(LivingEntity entity) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.PIKA_LOGIA.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
   }

   public boolean canGoThrough(BlockState state) {
      return state.m_204336_(ModTags.Blocks.LOGIA_BLOCK_PASS_PIKA);
   }
}
