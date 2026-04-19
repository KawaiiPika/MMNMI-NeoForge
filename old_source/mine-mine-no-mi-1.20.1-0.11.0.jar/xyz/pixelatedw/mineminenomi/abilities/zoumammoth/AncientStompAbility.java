package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class AncientStompAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int RADIUS = 7;
   private static final int DAMAGE = 10;
   public static final RegistryObject<AbilityCore<AncientStompAbility>> INSTANCE = ModRegistry.registerAbility("ancient_stomp", "Ancient Stomp", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Stomps the ground multiple times creating shockwaves that deal damage to all nearby enemies.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AncientStompAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.MAMMOTH_GUARD)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), DealDamageComponent.getTooltip(10.0F), RangeComponent.getTooltip(7.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::triggerRepeaterEvent).addStopEvent(100, this::stopRepeaterEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private Iterator<BlockPos> targetedBlocks;

   public AncientStompAbility(AbilityCore<AncientStompAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.rangeComponent, this.dealDamageComponent});
      this.addCanUseCheck(ZouMammothHelper::requiresGuardPoint);
      this.addContinueUseCheck(ZouMammothHelper::requiresGuardPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      Predicate<BlockPos> predicate = (pos) -> entity.m_9236_().m_8055_(pos.m_7494_()).m_60795_() && (double)pos.m_123342_() > entity.m_20186_() - (double)3.0F;
      Vec3 look = entity.m_20182_().m_82549_(entity.m_20154_().m_82542_((double)7.0F, (double)1.0F, (double)7.0F));
      BlockPos ogPos = BlockPos.m_274561_(look.m_7096_(), entity.m_20186_(), look.m_7094_());
      List<BlockPos> poses = WyHelper.getNearbyBlocks(ogPos, entity.m_9236_(), 7, predicate, ImmutableList.of(Blocks.f_50016_));
      this.targetedBlocks = WyHelper.shuffle(poses).stream().limit(10L).iterator();
      this.repeaterComponent.start(entity, 12, 8);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      if (this.targetedBlocks != null && this.targetedBlocks.hasNext()) {
         BlockPos stompPos = (BlockPos)this.targetedBlocks.next();
         List<LivingEntity> list = this.rangeComponent.getTargetsInArea(entity, 7.0F);
         Iterator<LivingEntity> iter = list.iterator();
         DamageSource source = this.dealDamageComponent.getDamageSource(entity);

         while(iter.hasNext()) {
            LivingEntity target = (LivingEntity)iter.next();
            if (this.dealDamageComponent.hurtTarget(entity, target, 10.0F, source)) {
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 20, 0));
               AbilityHelper.setDeltaMovement(target, target.m_20184_().m_82520_((double)0.0F, (double)0.25F, (double)0.0F));
            }
         }

         List<BlockPos> blocks = WyHelper.getNearbyBlocks(entity.m_20183_(), entity.m_9236_(), 7, (p) -> entity.m_9236_().m_8055_(p).m_204336_(ModTags.Blocks.BLOCK_PROT_FOLIAGE), ImmutableList.of(Blocks.f_50016_));
         if (!entity.m_9236_().f_46443_) {
            for(BlockPos blockPos : blocks) {
               BlockState blockState = entity.m_9236_().m_8055_(blockPos);

               for(int i = 0; i < 150; ++i) {
                  double offsetX = WyHelper.randomDouble();
                  double offsetY = WyHelper.randomDouble();
                  double offsetZ = WyHelper.randomDouble();
                  ((ServerLevel)entity.m_9236_()).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), (double)blockPos.m_123341_() + offsetX, (double)blockPos.m_123342_() + offsetY, (double)blockPos.m_123343_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
               }
            }

            BlockState blockState = entity.m_9236_().m_8055_(stompPos);
            if (!blockState.m_60795_()) {
               for(int i = 0; i < 250; ++i) {
                  double x = WyHelper.randomDouble();
                  double z = WyHelper.randomDouble();
                  ((ServerLevel)entity.m_9236_()).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), (double)stompPos.m_123341_() + WyHelper.randomWithRange(-3, 3) + x, (double)(stompPos.m_123342_() + 1), (double)stompPos.m_123343_() + WyHelper.randomWithRange(-3, 3) + z, 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
               }
            }
         }

      } else {
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility abiltiy) {
      this.continuousComponent.stopContinuity(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
