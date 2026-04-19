package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GroundSeccoAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final int COOLDOWN = 300;
   private static final int HOLD_TIME = 200;
   private static final int CHARGE_TIME = 60;
   private static final int DISTANCE = 20;
   private static final int SIZE = 64;
   public static final RegistryObject<AbilityCore<GroundSeccoAbility>> INSTANCE = ModRegistry.registerAbility("ground_secco", "Ground Secco", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Dries out the area around the user.", (Object)null), ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s.", new Object[]{"§a" + Math.round(19.999998F) + "%§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GroundSeccoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ContinuousComponent.getTooltip(200.0F), ChargeComponent.getTooltip(60.0F)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(this::endContinuityEvent);
   private final AltModeComponent<Pattern> altModeComponent;
   private final BlockTrackerComponent blockTrackerComponent;
   private Set<SimpleBlockPlacer> placers;
   private int distance;
   private int size;
   private float sizeMod;

   public GroundSeccoAbility(AbilityCore<GroundSeccoAbility> core) {
      super(core);
      this.altModeComponent = new AltModeComponent<Pattern>(this, Pattern.class, GroundSeccoAbility.Pattern.CENTER);
      this.blockTrackerComponent = new BlockTrackerComponent(this);
      this.placers = new HashSet();
      this.distance = 20;
      this.size = 64;
      this.sizeMod = 1.0F;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.altModeComponent, this.chargeComponent, this.blockTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresDryUser);
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addCanUseCheck(SunaHelper::requiresInactiveDesertGirasole);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
      } else if (!this.chargeComponent.isCharging()) {
         int blocksPerUpdate = 500;
         this.blockTrackerComponent.initQueue(entity.m_9236_(), blocksPerUpdate);
         BlockPos arenaCenter = entity.m_20183_();
         ChallengesWorldData worldData = ChallengesWorldData.get();
         InProgressChallenge ch = worldData.getInProgressChallengeFor((ServerLevel)entity.m_9236_());
         if (ch != null) {
            arenaCenter = new BlockPos(ch.getArenaPos().m_123341_(), entity.m_20183_().m_123342_(), ch.getArenaPos().m_123343_());
            this.distance = 30;
         }

         Pattern pattern = this.altModeComponent.getCurrentMode();
         pattern.create(this, entity.m_9236_(), arenaCenter);
         int chargeTime = this.blockTrackerComponent.getQueue().getQueueSize() / blocksPerUpdate;
         this.chargeComponent.startCharging(entity, (float)chargeTime);
      }
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         ;
      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
         this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
      }
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 200.0F);

      for(SimpleBlockPlacer placer : this.placers) {
         this.blockTrackerComponent.addPositions(placer.getPlacedPositions());
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      for(BlockPos pos : this.blockTrackerComponent.getPositions()) {
         entity.m_9236_().m_7731_(pos, Blocks.f_49992_.m_49966_(), 2);
      }

      this.placers.clear();
      this.cooldownComponent.startCooldown(entity, 300.0F);
   }

   private static void setupCornersPattern(GroundSeccoAbility ability, Level world, BlockPos arenaCenter) {
      BlockPos pos1 = arenaCenter.m_122030_(ability.distance).m_122013_(ability.distance);
      createCircle(ability, world, pos1, ability.size);
      BlockPos pos2 = arenaCenter.m_122025_(ability.distance).m_122013_(ability.distance);
      createCircle(ability, world, pos2, ability.size);
      BlockPos pos3 = arenaCenter.m_122030_(ability.distance).m_122020_(ability.distance);
      createCircle(ability, world, pos3, ability.size);
      BlockPos pos4 = arenaCenter.m_122025_(ability.distance).m_122020_(ability.distance);
      createCircle(ability, world, pos4, ability.size);
   }

   private static void setupCirclePattern(GroundSeccoAbility ability, Level world, BlockPos arenaCenter) {
      BlockPos pos1 = arenaCenter.m_122013_(ability.distance);
      createCircle(ability, world, pos1, ability.size);
      BlockPos pos2 = arenaCenter.m_122013_(ability.distance).m_122030_(ability.distance);
      createCircle(ability, world, pos2, ability.size);
      BlockPos pos3 = arenaCenter.m_122030_(ability.distance);
      createCircle(ability, world, pos3, ability.size);
      BlockPos pos4 = arenaCenter.m_122020_(ability.distance).m_122030_(ability.distance);
      createCircle(ability, world, pos4, ability.size);
      BlockPos pos5 = arenaCenter.m_122020_(ability.distance);
      createCircle(ability, world, pos5, ability.size);
      BlockPos pos6 = arenaCenter.m_122020_(ability.distance).m_122025_(ability.distance);
      createCircle(ability, world, pos6, ability.size);
      BlockPos pos7 = arenaCenter.m_122025_(ability.distance);
      createCircle(ability, world, pos7, ability.size);
      BlockPos pos8 = arenaCenter.m_122013_(ability.distance).m_122025_(ability.distance);
      createCircle(ability, world, pos8, ability.size);
   }

   private static void setupCenterPattern(GroundSeccoAbility ability, Level world, BlockPos arenaCenter) {
      int size = ability.size * 2;
      createCircle(ability, world, arenaCenter, size);
   }

   private static void setupCrossPattern(GroundSeccoAbility ability, Level world, BlockPos arenaCenter) {
      createCircle(ability, world, arenaCenter, ability.size);
      BlockPos pos2 = arenaCenter.m_122013_(ability.distance);
      createCircle(ability, world, pos2, ability.size);
      BlockPos pos3 = arenaCenter.m_122020_(ability.distance);
      createCircle(ability, world, pos3, ability.size);
      BlockPos pos4 = arenaCenter.m_122030_(ability.distance);
      createCircle(ability, world, pos4, ability.size);
      BlockPos pos5 = arenaCenter.m_122025_(ability.distance);
      createCircle(ability, world, pos5, ability.size);
   }

   private static void createCircle(GroundSeccoAbility ability, Level world, BlockPos pos, int size) {
      int finalSize = Math.round((float)size * ability.sizeMod);
      SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(((Block)ModBlocks.SUNA_SAND.get()).m_49966_()).setSize(finalSize, 4).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID).setBlockQueue(ability.blockTrackerComponent.getQueue());
      placer.generate(world, pos, BlockGenerators.SPHERE);
      ability.placers.add(placer);
   }

   public void changePattern(LivingEntity entity, Pattern pattern) {
      this.altModeComponent.setMode(entity, pattern);
   }

   public void changeRandomPattern(LivingEntity entity) {
      Pattern pattern = GroundSeccoAbility.Pattern.values()[entity.m_217043_().m_188503_(GroundSeccoAbility.Pattern.values().length - 1)];
      this.altModeComponent.setMode(entity, pattern);
   }

   public void setSizeModifier(float mod) {
      this.sizeMod = mod;
   }

   public static enum Pattern {
      CIRCLE(GroundSeccoAbility::setupCirclePattern),
      CROSS(GroundSeccoAbility::setupCrossPattern),
      CORNERS(GroundSeccoAbility::setupCornersPattern),
      CENTER(GroundSeccoAbility::setupCenterPattern);

      private IPatternFunction func;

      private Pattern(IPatternFunction func) {
         this.func = func;
      }

      public void create(GroundSeccoAbility ability, Level world, BlockPos arenaCenter) {
         this.func.create(ability, world, arenaCenter);
      }

      // $FF: synthetic method
      private static Pattern[] $values() {
         return new Pattern[]{CIRCLE, CROSS, CORNERS, CENTER};
      }
   }

   @FunctionalInterface
   private interface IPatternFunction {
      void create(GroundSeccoAbility var1, Level var2, BlockPos var3);
   }
}
