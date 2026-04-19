package xyz.pixelatedw.mineminenomi.abilities.bari;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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

public class BarrierBallAbility extends Ability {
   private static final int HOLD_TIME = 300;
   private static final int COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<BarrierBallAbility>> INSTANCE = ModRegistry.registerAbility("barrier_ball", "Barrier Ball", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user creates a spherical barrier where they're pointing.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BarrierBallAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(300.0F)).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public BarrierBallAbility(AbilityCore<BarrierBallAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.blockTrackerComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 300.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity, (double)64.0F);
      Level world = entity.m_9236_();
      if (mop != null) {
         BlockPos pos = BlockPos.m_274561_(mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_);
         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.BARRIER.get()).m_49966_()).setSize(5).setThickness(2).setRule(GRIEF_RULE).setHollow();
         placer.generate(world, pos, BlockGenerators.SPHERE);
         this.blockTrackerComponent.addPositions(placer.getPlacedPositions());
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      for(BlockPos pos : this.blockTrackerComponent.getPositions()) {
         if (entity.m_9236_().m_8055_(pos).m_60734_() == ModBlocks.BARRIER.get()) {
            entity.m_9236_().m_46597_(pos, Blocks.f_50016_.m_49966_());
         }
      }

      this.blockTrackerComponent.clearPositions();
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR})).build();
   }
}
