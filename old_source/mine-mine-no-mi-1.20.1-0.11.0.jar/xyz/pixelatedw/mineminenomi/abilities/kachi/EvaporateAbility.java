package xyz.pixelatedw.mineminenomi.abilities.kachi;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EvaporateAbility extends Ability {
   private static final int COOLDOWN = 240;
   private static final int HOLD_TIME = 120;
   public static final RegistryObject<AbilityCore<EvaporateAbility>> INSTANCE = ModRegistry.registerAbility("evaporate", "Evaporate", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Evaporates the water and melts ice around the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, EvaporateAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(120.0F)).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);

   public EvaporateAbility(AbilityCore<EvaporateAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility abiltiy) {
      this.continuousComponent.triggerContinuity(entity, 120.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setSize(6).setRule(GRIEF_RULE);
      Set<BlockPos> coords = placer.getPlacedPositions();
      placer.generate(entity.m_9236_(), entity.m_20183_(), BlockGenerators.SPHERE);

      for(BlockPos pos : coords) {
         if (entity.m_9236_().m_8055_(pos).m_60734_() == Blocks.f_50016_) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.EVAPORATE.get(), entity, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedTags(ModTags.Blocks.BLOCK_PROT_WATER, ModTags.Blocks.BLOCK_PROT_SNOW).addReplaceRules((world, pos, state) -> {
         if (state.m_61138_(BlockStateProperties.f_61362_)) {
            world.m_46597_(pos, (BlockState)state.m_61124_(BlockStateProperties.f_61362_, false));
            return true;
         } else if (!state.m_204336_(BlockTags.f_13047_) && state.m_60734_() != Blocks.f_50575_ && state.m_60734_() != Blocks.f_50037_ && state.m_60734_() != Blocks.f_50038_) {
            return false;
         } else {
            world.m_46597_(pos, Blocks.f_49990_.m_49966_());
            return true;
         }
      }).build();
   }
}
