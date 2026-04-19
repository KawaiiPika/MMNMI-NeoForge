package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShimaYurashiAbility extends Ability {
   private static final int EXPLOSION_RADIUS = 35;
   private static final int EXPLOSION_DEPTH = 10;
   private static final int COOLDOWN = 1200;
   private static final int CHARGE_TIME = 100;
   private static final int MIN_DAMAGE = 66;
   private static final int MAX_DAMAGE = 80;
   public static final RegistryObject<AbilityCore<ShimaYurashiAbility>> INSTANCE = ModRegistry.registerAbility("shima_yurashi", "Shima Yurashi", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user grabs the air around them and pulls it downwards after which the nearby land and entities are sent flying.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ShimaYurashiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1200.0F), ChargeComponent.getTooltip(100.0F), RangeComponent.getTooltip(35.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(66.0F, 66.0F)).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(40);

   public ShimaYurashiAbility(AbilityCore<ShimaYurashiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.rangeComponent, this.dealDamageComponent, this.blockTrackerComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresOnGround);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 100.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      List<BlockPos> positions = this.blockTrackerComponent.getNearbyBlockPositions(entity.m_20183_(), entity.m_9236_(), 17, 5, ModTags.Blocks.BLOCK_PROT_CORE);
      Collections.shuffle(positions);
      this.details.clearPositions();
      this.blockTrackerComponent.clearPositions();
      Optional<ProtectedArea> area = ProtectedAreasData.get((ServerLevel)entity.m_9236_()).getProtectedArea((int)entity.m_20185_(), (int)entity.m_20186_(), (int)entity.m_20189_());
      if (area.isEmpty() || ((ProtectedArea)area.get()).canDestroyBlocks()) {
         positions.stream().limit(600L).forEach((pos) -> {
            if (entity.m_9236_().m_45527_(pos)) {
               this.details.addPosition(pos);
            }

            if (pos.m_123331_(entity.m_20183_()) > (double)9.0F) {
               this.blockTrackerComponent.addBlockPos(pos);
            }

         });
      }

      this.animationComponent.start(entity, ModAnimations.KAISHIN, 100);
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.getChargeTime() % 5.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GREAT_STOMP.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 10, 0, false, false));
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 35.0F);
      targets.stream().filter((target) -> target != null && target.m_6084_() && target.m_9236_().m_45547_(new ClipContext(target.m_20182_(), target.m_20182_().m_82520_((double)0.0F, (double)-10.0F, (double)0.0F), Block.COLLIDER, Fluid.ANY, target)).m_6662_().equals(Type.BLOCK)).forEach((target) -> target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 10, 0, false, false)));
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 35.0F);
         DamageSource source = this.dealDamageComponent.getDamageSource(entity);
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(source);
         handler.bypassLogia();
         handler.addAbilityPiercing(0.5F, this.getCore());
         handler.setUnavoidable();
         targets.stream().filter((target) -> target != null && target.m_6084_() && entity.m_142582_(target)).forEach((target) -> {
            double distance = Math.min((double)66.0F, Math.sqrt(target.m_20280_(entity)));
            double damage = (double)80.0F - distance / (double)2.0F;
            if (this.dealDamageComponent.hurtTarget(entity, target, (float)damage, source) && target.m_20096_()) {
               Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_().m_82542_((double)25.0F, (double)1.0F, (double)25.0F);
               AbilityHelper.setDeltaMovement(target, -dirVec.f_82479_, (double)3.0F, -dirVec.f_82481_);
            }

         });
         EntityHitResult trace = WyHelper.rayTraceEntities(entity, (double)1.5F);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.AIR_CRACKS.get(), entity, trace.m_82450_().m_7096_(), entity.m_20188_(), trace.m_82450_().m_7094_());

         for(BlockPos pos : this.blockTrackerComponent.getPositions()) {
            FallingBlockEntity block = FallingBlockEntity.m_201971_(entity.m_9236_(), pos, entity.m_9236_().m_8055_(pos));
            block.f_31943_ = false;
            block.f_31942_ = 1;
            Vec3 dirVec = entity.m_20182_().m_82546_(block.m_20182_()).m_82541_().m_82542_((double)2.0F, (double)2.0F, (double)2.0F);
            AbilityHelper.setDeltaMovement(block, -dirVec.f_82479_, (double)1.5F + dirVec.f_82480_, -dirVec.f_82481_);
         }

         SimpleBlockPlacer placer = (new SimpleBlockPlacer()).setBlock(Blocks.f_50016_.m_49966_()).setSize(35, 10).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID);
         placer.generate(entity.m_9236_(), entity.m_20183_(), BlockGenerators.SPHERE_WITH_PLATFORM);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GURA_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, 1200.0F);
      }
   }
}
