package xyz.pixelatedw.mineminenomi.abilities.ryusoken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RyuNoIbukiAbility extends Ability {
   private static final int EXPLOSION_RADIUS = 15;
   private static final int EXPLOSION_DEPTH = 3;
   private static final int COOLDOWN = 500;
   private static final int CHARGE_TIME = 30;
   private static final Predicate<LivingEntity> AREA_CHECK = (target) -> target != null && target.m_6084_() && target.m_9236_().m_45547_(new ClipContext(target.m_20182_().m_82492_((double)0.0F, (double)3.0F, (double)0.0F), target.m_20182_().m_82520_((double)0.0F, (double)3.0F, (double)0.0F), Block.COLLIDER, Fluid.ANY, target)).m_6662_().equals(Type.BLOCK);
   public static final RegistryObject<AbilityCore<RyuNoIbukiAbility>> INSTANCE = ModRegistry.registerAbility("ryu_no_ibuki", "Ryu no Ibuki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user puts their clenched fists into the ground, creating an immense impact and centered all-directional fissure.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, RyuNoIbukiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(500.0F), ChargeComponent.getTooltip(30.0F), RangeComponent.getTooltip(15.0F, RangeComponent.RangeType.AOE)).setUnlockCheck(RyuNoIbukiAbility::canUnlock).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final List<BlockPos> affectedBlocks = new ArrayList();
   private final BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(5);
   private final Interval particleInterval = new Interval(5);

   public RyuNoIbukiAbility(AbilityCore<RyuNoIbukiAbility> core) {
      super(core);
      super.addComponents(this.chargeComponent, this.animationComponent, this.rangeComponent, this.dealDamageComponent);
      super.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      super.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 30.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      int radiusXZ = 7;
      List<BlockPos> allPositions = (List)WyHelper.getNearbyBlocks(entity.m_20183_(), entity.m_9236_(), radiusXZ, 3, radiusXZ, (state) -> !state.m_60734_().equals(Blocks.f_50016_)).stream().filter((pos) -> DefaultProtectionRules.CORE_FOLIAGE_ORE.check(entity.m_9236_(), pos, entity.m_9236_().m_8055_(pos))).collect(Collectors.toList());
      Collections.shuffle(allPositions, this.random);

      for(int i = 0; i < allPositions.size(); ++i) {
         if (i % 2 == 1) {
            this.affectedBlocks.add((BlockPos)allPositions.get(i));
         }
      }

      List var10000 = this.affectedBlocks;
      BreakingBlocksParticleEffect.Details var10001 = this.details;
      Objects.requireNonNull(var10001);
      var10000.forEach(var10001::addPosition);
      this.animationComponent.start(entity, ModAnimations.RYU_NO_IBUKI, 30);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.particleInterval.canTick()) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), this.details);
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 15.0F);
      targets.stream().filter(AREA_CHECK).forEach((target) -> target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 5, 0, false, false)));
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 15.0F);
         targets.stream().filter(AREA_CHECK).forEach((target) -> target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 80, 0, false, false)));
         Optional<ProtectedArea> opt = ProtectedAreasData.get((ServerLevel)entity.m_9236_()).getProtectedArea((int)entity.m_20185_(), (int)entity.m_20186_(), (int)entity.m_20189_());
         if (opt.isEmpty() || ((ProtectedArea)opt.get()).canDestroyBlocks()) {
            for(BlockPos pos : this.affectedBlocks) {
               FallingBlockEntity block = FallingBlockEntity.m_201971_(entity.m_9236_(), pos, entity.m_9236_().m_8055_(pos));
               Vec3 dirVec = entity.m_20182_().m_82546_(block.m_20182_()).m_82541_();
               AbilityHelper.setDeltaMovement(block, dirVec.f_82479_ * WyHelper.randomDouble() * (double)2.0F, dirVec.f_82480_ + (double)0.5F, dirVec.f_82481_ * WyHelper.randomDouble() * (double)2.0F);
               block.f_31943_ = false;
               block.f_31942_ = 1;
               entity.m_9236_().m_7967_(block);
               NuWorld.setBlockState((Entity)entity, pos, Blocks.f_50016_.m_49966_(), 3, (BlockProtectionRule)null);
            }
         }

         this.affectedBlocks.clear();
         this.details.clearPositions();
         this.particleInterval.restartIntervalToZero();
         this.animationComponent.stop(entity);
         super.cooldownComponent.startCooldown(entity, 500.0F);
      }
   }

   private static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         return props != null && questProps != null ? false : false;
      } else {
         return false;
      }
   }
}
