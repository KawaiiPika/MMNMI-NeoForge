package xyz.pixelatedw.mineminenomi.abilities.doru;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TokudaiCandleAbility extends Ability {
   private static final float COOLDOWN = 6000.0F;
   private static final float HOLD_TIME = 1200.0F;
   private static final int FIRST_LAYER_SIZE = 15;
   private static final int SECOND_LAYER_SIZE = 10;
   private static final int THIRD_LAYER_XZ_SIZE = 5;
   private static final int THIRD_LAYER_Y_SIZE = 10;
   private static final int FOURTH_LAYER_XZ_SIZE = 13;
   private static final int FOURTH_LAYER_Y_SIZE = 10;
   private static final float RANGE = 100.0F;
   public static final IBlockGenerator CANDLE_GENERATOR = (placer, level, origin) -> {
      int x0 = origin.m_123341_();
      int y0 = origin.m_123342_();
      int z0 = origin.m_123343_();
      int actualYSize = placer.getSizeY();
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int y = 0; y < actualYSize; ++y) {
         mutpos.m_122178_(x0, y0 + y, z0);
         placer.placeNext(level, mutpos.m_7949_(), placer.getState(), placer.getFlag(), placer.getRule());
      }

      placer.placeNext(level, mutpos.m_7494_(), Blocks.f_50083_.m_49966_(), 3, placer.getRule());
   };
   public static final RegistryObject<AbilityCore<TokudaiCandleAbility>> INSTANCE = ModRegistry.registerAbility("tokudai_candle", "Tokudai Candle", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TokudaiCandleAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(6000.0F)).build("mineminenomi"));
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(100, this::onTickContinuityEvent).addEndEvent(100, this::onEndContinuityEvent);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(100, this::onTickChargeEvent).addEndEvent(100, this::onEndChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);
   private BlockPos centerPos;
   private Vec3 centerPosVec;
   private int blocksPerTick = 200;
   private SimpleBlockPlacer placer;

   public TokudaiCandleAbility(AbilityCore<TokudaiCandleAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.rangeComponent, this.blockTrackerComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
      } else if (!this.chargeComponent.isCharging()) {
         if (this.centerPos == null) {
            Vec3 startVec = entity.m_20182_().m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
            Vec3 endVec = startVec.m_82549_(entity.m_20154_().m_82490_((double)128.0F));
            BlockHitResult result = entity.m_9236_().m_45547_(new ClipContext(startVec, endVec, Block.COLLIDER, Fluid.NONE, entity));
            this.setCenterPos(result.m_82425_());
         }

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 100.0F, (TargetPredicate)null)) {
            Vec3 pushVec = target.m_20182_().m_82546_(this.centerPosVec).m_82541_().m_82542_((double)10.0F, (double)1.0F, (double)10.0F);
            AbilityHelper.setDeltaMovement(target, pushVec.f_82479_, target.m_20184_().f_82480_, pushVec.f_82481_);
         }

         this.blockTrackerComponent.initQueue(entity.m_9236_(), this.blocksPerTick);
         BlockState defaultWaxState = ((net.minecraft.world.level.block.Block)ModBlocks.WAX.get()).m_49966_();
         this.placer = (new SimpleBlockPlacer()).setBlock(defaultWaxState).setSize(15).setRule(DefaultProtectionRules.AIR_FOLIAGE).setBlockQueue(this.blockTrackerComponent.getQueue());
         this.placer.generate(entity.m_9236_(), this.centerPos, BlockGenerators.CUBE);
         this.placer.setSize(10);
         this.placer.generate(entity.m_9236_(), this.centerPos.m_6630_(15), BlockGenerators.CUBE);
         this.placer.setSize(5, 10);
         this.placer.generate(entity.m_9236_(), this.centerPos.m_6630_(25), BlockGenerators.CUBE);
         this.placer.setSize(13, 10);
         this.placer.generate(entity.m_9236_(), this.centerPos.m_6630_(41), BlockGenerators.SPHERE);
         int candleSplitDist = 9;
         this.placer.setSize(4);
         BlockPos nextCandlePos = this.centerPos.m_7918_(13, 16, 13);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(-9, 0, 0);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(-9, 0, 0);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(-9, 0, 0);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(0, 0, -9);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(0, 0, -9);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(0, 0, -9);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(9, 0, 0);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(9, 0, 0);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(9, 0, 0);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(0, 0, 9);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         nextCandlePos = nextCandlePos.m_7918_(0, 0, 9);
         this.placer.generate(entity.m_9236_(), nextCandlePos, CANDLE_GENERATOR);
         float chargeTime = (float)(this.blockTrackerComponent.getQueue().getQueueSize() / this.blocksPerTick);
         this.chargeComponent.startCharging(entity, chargeTime);
      }
   }

   private void onTickChargeEvent(LivingEntity entity, IAbility ability) {
      this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
      if (!entity.m_9236_().f_46443_ && entity.f_19797_ % 5 == 0) {
         Vec3 motion = entity.m_20182_().m_82549_(entity.m_20154_().m_82542_((double)5.0F, (double)5.0F, (double)5.0F)).m_82546_(this.centerPosVec.m_82520_((double)0.0F, (double)10.0F, (double)0.0F)).m_82541_().m_82542_((double)1.15F, (double)1.15F, (double)1.15F).m_82548_();

         for(int i = 0; i < 15; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            double offsetMotionX = WyHelper.randomDouble() / (double)5.0F;
            double offsetMotionY = WyHelper.randomDouble() / (double)5.0F;
            double offsetMotionZ = WyHelper.randomDouble() / (double)5.0F;
            SimpleParticleData particle = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
            particle.setLife(30);
            particle.setSize(3.0F);
            particle.setMotion(motion.f_82479_ + offsetMotionX, motion.f_82480_ + offsetMotionY, motion.f_82481_ + offsetMotionZ);
            WyHelper.spawnParticles(particle, (ServerLevel)entity.m_9236_(), entity.m_20185_() + offsetX, entity.m_20186_() + (double)entity.m_20192_() + offsetY, entity.m_20189_() + offsetZ);
         }
      }

   }

   private void onEndChargeEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 1200.0F);
      if (!entity.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TOKUDAI_CANDLE.get(), entity, (double)this.centerPos.m_123341_(), (double)this.centerPos.m_123342_(), (double)this.centerPos.m_123343_());
      }

   }

   private void onTickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && entity.f_19797_ % 100 == 0) {
         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 100.0F, (TargetPredicate)null)) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 0));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 100, 0));
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.TOKUDAI_CANDLE_POISON.get(), 100, 0));
         }

         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TOKUDAI_CANDLE.get(), entity, (double)this.centerPos.m_123341_(), (double)this.centerPos.m_123342_(), (double)this.centerPos.m_123343_());
      }

   }

   private void onEndContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         for(BlockPos pos : this.placer.getPlacedPositions()) {
            entity.m_9236_().m_46597_(pos, Blocks.f_50016_.m_49966_());
         }

         this.centerPos = null;
         this.cooldownComponent.startCooldown(entity, 6000.0F);
      }
   }

   public void setBlocksPerTick(int blocks) {
      this.blocksPerTick = blocks;
   }

   public void setCenterPos(BlockPos centerPos) {
      this.centerPos = centerPos;
      this.centerPosVec = Vec3.m_82512_(centerPos);
   }
}
