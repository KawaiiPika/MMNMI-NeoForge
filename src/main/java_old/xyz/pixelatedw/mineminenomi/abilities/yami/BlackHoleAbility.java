package xyz.pixelatedw.mineminenomi.abilities.yami;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackHoleAbility extends Ability {
   private static final int CHARGE_TIME = 100;
   private static final int MAX_COOLDOWN = 400;
   private static final float MIN_COOLDOWN = 200.0F;
   private static final int RANGE_XZ = 200;
   private static final int RANGE_MIN_Y = -6;
   private static final int RANGE_MAX_Y = 3;
   private static final int RANGE_Y = Math.abs(-6) + 3;
   private static final int ENTITY_STUN_RANGE = 3;
   private static final BlockProtectionRule DARKNESS_REPLACEMENT_RULE;
   public static final RegistryObject<AbilityCore<BlackHoleAbility>> INSTANCE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);
   private List<LivingEntity> targets = new ArrayList();
   private int amountToFix = 0;
   private double timeToFix = (double)0.0F;
   private State state;
   private AbsorbedBlocksAbility absorbAbility;

   public BlackHoleAbility(AbilityCore<BlackHoleAbility> core) {
      super(core);
      this.state = BlackHoleAbility.State.ABSORBING;
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.animationComponent, this.rangeComponent, this.blockTrackerComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         this.absorbAbility = (AbsorbedBlocksAbility)AbilityCapability.get(entity).map((props) -> (AbsorbedBlocksAbility)props.getPassiveAbility((AbilityCore)AbsorbedBlocksAbility.INSTANCE.get())).orElse((Object)null);
         if (this.state == BlackHoleAbility.State.ABSORBING) {
            if (!this.chargeComponent.isCharging()) {
               this.chargeComponent.startCharging(entity, 100.0F);
            } else if (this.chargeComponent.getChargePercentage() >= 0.2F) {
               this.chargeComponent.stopCharging(entity);
            }
         } else if (this.state == BlackHoleAbility.State.RELEASING && this.continuousComponent.isContinuous()) {
            this.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         this.blockTrackerComponent.initQueue(level, 500);
         if (this.state == BlackHoleAbility.State.ABSORBING) {
            BlockQueue.IAfterPlaceBlock placementCallback = (posx, oldState, newState) -> {
               this.absorbAbility.addAbsorbedBlock(oldState, posx);
               if (!level.m_8055_(posx.m_7494_()).m_60795_()) {
                  WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLACK_HOLE.get(), entity, (double)posx.m_123341_(), (double)posx.m_123342_() + (double)0.5F, (double)posx.m_123343_());
               }

               if (posx.hashCode() % 10 == 0) {
                  for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, posx, 3.0F)) {
                     target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1));
                     WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLACK_HOLE.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
                  }

               }
            };
            double maxDistance = (double)(200 * RANGE_Y);
            BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

            for(int y = -6; y < 3; ++y) {
               for(int x = -200; x < 200; ++x) {
                  for(int z = -200; z < 200; ++z) {
                     double distance = (double)(x * x + z * z + y * y);
                     if (!(distance >= maxDistance) && !(this.random.nextDouble() < distance / maxDistance)) {
                        double posX = entity.m_20185_() + (double)x;
                        double posY = entity.m_20186_() + (double)y;
                        double posZ = entity.m_20189_() + (double)z;
                        mutpos.m_122169_(posX, posY, posZ);
                        HashBlockPos pos = new HashBlockPos(mutpos.m_7949_(), x * x + y * y + z * z);
                        this.blockTrackerComponent.addBlockToQueue(pos, ((Block)ModBlocks.DARKNESS.get()).m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE, placementCallback);
                     }
                  }
               }
            }
         }

      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.state == BlackHoleAbility.State.ABSORBING) {
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1, false, false));
            this.blockTrackerComponent.tickBlockQueue(level);
         } else if (this.state == BlackHoleAbility.State.RELEASING) {
            List<AbsorbedBlocksAbility.BlockData> uncompressedBlocks = this.absorbAbility.getUncompressedBlocks();
            double batchSize = Math.ceil((double)this.amountToFix / this.timeToFix);
            int index = uncompressedBlocks.size();
            int i = 0;

            while(true) {
               --index;
               if (index < 0 || !(batchSize-- > (double)0.0F)) {
                  if (uncompressedBlocks.isEmpty()) {
                     this.chargeComponent.stopCharging(entity);
                  }
                  break;
               }

               AbsorbedBlocksAbility.BlockData blockData = (AbsorbedBlocksAbility.BlockData)uncompressedBlocks.get(index);
               BlockPos blockPos = blockData.getBlockPos();
               BlockState blockState = blockData.getBlockState();
               if (NuWorld.setBlockState((Entity)entity, blockPos, blockState, 3, DARKNESS_REPLACEMENT_RULE)) {
                  if (!level.m_8055_(blockPos.m_7494_()).m_60795_()) {
                     WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLACK_HOLE.get(), entity, (double)blockPos.m_123341_(), (double)((float)blockPos.m_123342_() + 0.5F), (double)blockPos.m_123343_());
                  }

                  this.absorbAbility.removeAbsorbedBlock(blockData);
               }

               ++i;
            }
         }

      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.state == BlackHoleAbility.State.ABSORBING) {
            this.targets.clear();
            this.timeToFix = (double)this.chargeComponent.getChargeTime();
            List<AbsorbedBlocksAbility.BlockData> list = (List)AbilityCapability.get(entity).map((props) -> (AbsorbedBlocksAbility)props.getPassiveAbility((AbilityCore)AbsorbedBlocksAbility.INSTANCE.get())).map(AbsorbedBlocksAbility::getUncompressedBlocks).orElse(List.of());
            this.amountToFix = list.size();
            this.state = BlackHoleAbility.State.RELEASING;
            this.continuousComponent.startContinuity(entity, -1.0F);
         } else if (this.state == BlackHoleAbility.State.RELEASING) {
            this.state = BlackHoleAbility.State.ABSORBING;
            super.cooldownComponent.startCooldown(entity, (float)((double)400.0F * Math.max((double)0.5F, (double)this.chargeComponent.getChargePercentage())));
         }

      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         List<AbsorbedBlocksAbility.BlockData> list = (List)AbilityCapability.get(entity).map((props) -> (AbsorbedBlocksAbility)props.getPassiveAbility((AbilityCore)AbsorbedBlocksAbility.INSTANCE.get())).map(AbsorbedBlocksAbility::getUncompressedBlocks).orElse(List.of());
         if (list.isEmpty()) {
            this.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         this.chargeComponent.startCharging(entity, (float)((int)this.timeToFix));
      }
   }

   static {
      DARKNESS_REPLACEMENT_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR})).addApprovedBlocks(ModBlocks.DARKNESS).build();
      INSTANCE = ModRegistry.registerAbility("black_hole", "Black Hole", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user spreads darkness over the target area, which engulfs and suffocates anyone and anything inside of it.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BlackHoleAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 400.0F), ChargeComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(), RangeComponent.getTooltip(200.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
      });
   }

   private static enum State {
      ABSORBING,
      RELEASING;

      // $FF: synthetic method
      private static State[] $values() {
         return new State[]{ABSORBING, RELEASING};
      }
   }
}
