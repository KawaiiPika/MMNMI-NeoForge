package xyz.pixelatedw.mineminenomi.abilities.mogu;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MoguraTonpoAbility extends Ability {
   private static final float COOLDOWN = 240.0F;
   private static final float RANGE = 1.6F;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<MoguraTonpoAbility>> INSTANCE = ModRegistry.registerAbility("mogura_tonpo", "Mogura Tonpo", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Digs a massive tunnel forwards while in mole form", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, MoguraTonpoAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.MOGU_HEAVY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), DealDamageComponent.getTooltip(15.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.LINE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::onStartContinuityEvent).addTickEvent(100, this::onTickContinuityEvent).addEndEvent(100, this::onEndContinuityEvent);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(20);
   private float initialY;
   private boolean hasFallProt;

   public MoguraTonpoAbility(AbilityCore<MoguraTonpoAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.damageTakenComponent, this.dealDamageComponent, this.rangeComponent});
      this.addCanUseCheck(MoguHelper::requiresHeavyPoint);
      this.addContinueUseCheck(MoguHelper::requiresHeavyPoint);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 10.0F);
   }

   private void onStartContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      this.hasFallProt = true;
      this.initialY = (float)((int)entity.m_20186_());
      Vec3 speed = entity.m_20154_().m_82541_();
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 0.1, speed.f_82481_);
   }

   private void onTickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (entity.m_6084_() && !(entity.m_20186_() < (double)this.initialY)) {
            for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.6F, 1.6F)) {
               if (this.hitTrackerComponent.canHit(target)) {
                  this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
               }
            }

            List<BlockPos> positions = new ArrayList();

            for(BlockPos location : WyHelper.getNearbyBlocks(entity, 3)) {
               if ((float)location.m_123342_() >= this.initialY) {
                  BlockState tempBlock = entity.m_9236_().m_8055_(location);
                  if (NuWorld.setBlockState((Entity)entity, location, Blocks.f_50016_.m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE)) {
                     if (entity instanceof Player) {
                        Player player = (Player)entity;
                        player.m_150109_().m_36054_(new ItemStack(tempBlock.m_60734_()));
                     }

                     positions.add(location);
                  }
               }
            }

            if (positions.size() > 0) {
               this.details.setPositions(positions);
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
            }

         }
      }
   }

   private void onEndContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (this.hasFallProt && damageSource.m_276093_(DamageTypes.f_268671_)) {
         this.hasFallProt = false;
         return 0.0F;
      } else {
         return damage;
      }
   }
}
