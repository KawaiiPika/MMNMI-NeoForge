package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction8;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public abstract class WallAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(90, this::startContinuityEvent).addEndEvent(90, this::endContinuityEvent);
   protected final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public WallAbility(AbilityCore<? extends WallAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.blockTrackerComponent});
      this.addUseEvent(90, this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, (float)this.getHoldTime());
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      Direction8 dir = WyHelper.getLookDirection(entity);
      if (dir == Direction8.SOUTH) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(0, 0, -4), this.getLength(), this.getHeight(), this.getThickness(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.SOUTH_EAST) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(2, 0, -4), this.getLength(), this.getHeight(), this.getThickness(), this.getWallBlock(), this.getGriefingRule());
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(4, 0, -2), this.getThickness(), this.getHeight(), this.getLength(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.SOUTH_WEST) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(-2, 0, -4), this.getLength(), this.getHeight(), this.getThickness(), this.getWallBlock(), this.getGriefingRule());
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(-4, 0, -2), this.getThickness(), this.getHeight(), this.getLength(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.NORTH) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(0, 0, 4), this.getLength(), this.getHeight(), this.getThickness(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.NORTH_EAST) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(2, 0, 4), this.getLength(), this.getHeight(), this.getThickness(), this.getWallBlock(), this.getGriefingRule());
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(4, 0, 2), this.getThickness(), this.getHeight(), this.getLength(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.NORTH_WEST) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(-2, 0, 4), this.getLength(), this.getHeight(), this.getThickness(), this.getWallBlock(), this.getGriefingRule());
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(-4, 0, 2), this.getThickness(), this.getHeight(), this.getLength(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.EAST) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(4, 0, 0), this.getThickness(), this.getHeight(), this.getLength(), this.getWallBlock(), this.getGriefingRule());
      } else if (dir == Direction8.WEST) {
         this.createWall(entity.m_9236_(), entity.m_20183_().m_7918_(-4, 0, 0), this.getThickness(), this.getHeight(), this.getLength(), this.getWallBlock(), this.getGriefingRule());
      }

      if (this.stopAfterUse()) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void createWall(Level level, BlockPos pos, int thickness, int height, int length, BlockState state, BlockProtectionRule rule) {
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(state).setRule(rule).setSize(thickness, height, length);
      placer.generate(level, pos, BlockGenerators.CUBE);
      this.blockTrackerComponent.addPositions(placer.getPlacedPositions());
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!this.stopAfterUse()) {
         for(BlockPos pos : this.blockTrackerComponent.getPositions()) {
            BlockState currentBlock = entity.m_9236_().m_8055_(pos);
            if (currentBlock == this.getWallBlock()) {
               entity.m_9236_().m_46597_(pos, Blocks.f_50016_.m_49966_());
            }
         }

         this.blockTrackerComponent.clearPositions();
      }

   }

   protected int getHoldTime() {
      return -1;
   }

   public abstract int getThickness();

   public abstract int getHeight();

   public abstract int getLength();

   public abstract BlockState getWallBlock();

   public abstract BlockProtectionRule getGriefingRule();

   public abstract boolean stopAfterUse();
}
