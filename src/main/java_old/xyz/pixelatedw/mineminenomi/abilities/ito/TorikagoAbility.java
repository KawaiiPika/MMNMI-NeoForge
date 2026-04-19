package xyz.pixelatedw.mineminenomi.abilities.ito;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class TorikagoAbility extends Ability {
   public static final int MAX_CAGE_SIZE = 60;
   private static final int MIN_CAGE_SIZE = 8;
   private static final int CHARGE_TIME = 60;
   private static final int MAX_COOLDOWN = 200;
   private static final float MIN_COOLDOWN = 26.666668F;
   public static final RegistryObject<AbilityCore<TorikagoAbility>> INSTANCE = ModRegistry.registerAbility("torikago", "Torikago", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates an indestructible dome made of strings, that damage anyone who touches them", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TorikagoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(26.666668F, 200.0F), ChargeComponent.getTooltip(60.0F), ContinuousComponent.getTooltip()).build("mineminenomi");
   });
   private static final BlockProtectionRule GRIEF_RULE;
   private static final BlockProtectionRule RESTORE_RULE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, true)).addStartEvent(100, this::startChargeEvent).addEndEvent(100, this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(100, this::tickContinuousEvent).addEndEvent(100, this::endContinuousEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);
   private SimpleBlockPlacer placer;
   public int cageSize = 0;
   private BlockPos centerPos;
   private Interval checkPositionInterval = new Interval(20);

   public TorikagoAbility(AbilityCore<TorikagoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.animationComponent, this.blockTrackerComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         this.chargeComponent.startCharging(entity, 60.0F);
      }
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.checkPositionInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM, 60);
      this.blockTrackerComponent.initQueue(entity.m_9236_(), 500);
      this.placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.STRING_WALL.get()).m_49966_()).setRule(GRIEF_RULE).setHollow().setBlockQueue(this.blockTrackerComponent.getQueue());
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.centerPos = entity.m_20183_();
         this.cageSize = Math.max(8, (int)(60.0F * (this.chargeComponent.getChargeTime() / this.chargeComponent.getMaxChargeTime())));
         this.placer.setSize(this.cageSize);
         this.placer.generate(entity.m_9236_(), this.centerPos, BlockGenerators.SPHERE);
         this.continuousComponent.startContinuity(entity, -1.0F);
         this.animationComponent.stop(entity);
      }
   }

   private void tickContinuousEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.centerPos != null && this.checkPositionInterval.canTick() && !this.isPositionInTorikago(entity.m_20183_())) {
            this.continuousComponent.stopContinuity(entity);
         }

         this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
      }
   }

   private void endContinuousEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.placer.setBlock(Blocks.f_50016_.m_49966_()).setRule(RESTORE_RULE);
         this.placer.generate(entity.m_9236_(), this.centerPos, BlockGenerators.SPHERE);
         this.blockTrackerComponent.runBlockQueueUntilFinished();
         this.centerPos = null;
         float cooldown = 200.0F * ((float)this.cageSize / 60.0F);
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }

   public boolean isPositionInTorikago(BlockPos pos) {
      return this.centerPos == null ? false : pos.m_123314_(this.centerPos, (double)this.cageSize);
   }

   public BlockPos getCenter() {
      return this.centerPos;
   }

   static {
      GRIEF_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE_LIQUID})).setBypassGriefRule().build();
      RESTORE_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[0])).addApprovedBlocks(ModBlocks.STRING_WALL).setBypassGriefRule().build();
   }
}
