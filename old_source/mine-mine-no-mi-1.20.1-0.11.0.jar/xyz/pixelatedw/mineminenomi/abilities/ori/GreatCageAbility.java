package xyz.pixelatedw.mineminenomi.abilities.ori;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GreatCageAbility extends Ability {
   private static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 40;
   private static final int MAX_COOLDOWN = 340;
   public static final RegistryObject<AbilityCore<GreatCageAbility>> INSTANCE = ModRegistry.registerAbility("great_cage", "Great Cage", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a big cage trapping the user and all nearby entities in it.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GreatCageAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 340.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);
   private SimpleBlockPlacer placer;

   public GreatCageAbility(AbilityCore<GreatCageAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.blockTrackerComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.blockTrackerComponent.initQueue(entity.m_9236_(), 200);
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.ORI_BARS.get()).m_49966_()).setSize(20).setRule(DefaultProtectionRules.AIR_FOLIAGE_LIQUID).setHollow().setFlag(259).setBlockQueue(this.blockTrackerComponent.getQueue());
      this.placer.generate(entity.m_9236_(), entity.m_20183_(), BlockGenerators.CUBE);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      for(BlockPos pos : this.placer.getPlacedPositions()) {
         entity.m_9236_().m_46597_(pos, Blocks.f_50016_.m_49966_());
      }

      float cooldown = 40.0F + this.continuousComponent.getContinueTime() / 4.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE_LIQUID})).setBypassGriefRule().build();
   }
}
