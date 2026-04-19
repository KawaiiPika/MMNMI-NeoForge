package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.HashBlockPos;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class IceAgeAbility extends Ability {
   private static final int CHARGE_TIME = 100;
   private static final int MIN_COOLDOWN = 200;
   private static final int MAX_COOLDOWN = 300;
   private static final int ICE_RANGE_XZ = 384;
   private static final int ICE_RANGE_Y = 9;
   private static final float ENTITY_FREEZE_RANGE = 5.0F;
   public static final RegistryObject<AbilityCore<IceAgeAbility>> INSTANCE = ModRegistry.registerAbility("ice_age", "Ice Age", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Freezes a large area around the user and everyone inside of it", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, IceAgeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 300.0F), ChargeComponent.getTooltip(100.0F), RangeComponent.getTooltip(384.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.ICE).build("mineminenomi");
   });
   private static final BlockProtectionRule PROTECTION_RULE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this, (comp) -> (double)comp.getChargePercentage() > (double)0.5F)).addStartEvent(100, this::startChargeEvent).addTickEvent(100, this::duringChargeEvent).addEndEvent(100, this::stopChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final BlockTrackerComponent blockTrackerComponent = new BlockTrackerComponent(this);

   public IceAgeAbility(AbilityCore<IceAgeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.hitTrackerComponent, this.blockTrackerComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 100.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.blockTrackerComponent.initQueue(entity.m_9236_(), 1500);
         this.hitTrackerComponent.clearHits();
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.ICE_AGE_SFX.get(), SoundSource.PLAYERS, 10.0F, 1.0F);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ICE_AGE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         BlockQueue.IAfterPlaceBlock placementCallback = (posx, oldState, newState) -> {
            if (posx.hashCode() % 10 == 0) {
               for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, posx, 5.0F)) {
                  if (this.hitTrackerComponent.canHit(target)) {
                     target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.FROZEN.get(), 100, 0));
                  }
               }

            }
         };
         double maxDistance = (double)3456.0F;
         BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

         for(double y = (double)-9.0F; y < (double)9.0F; ++y) {
            for(double x = (double)-384.0F; x < (double)384.0F; ++x) {
               for(double z = (double)-384.0F; z < (double)384.0F; ++z) {
                  double distance = x * x + z * z + y * y;
                  if (!(distance >= (double)3456.0F) && !(this.random.nextDouble() < distance / (double)3456.0F)) {
                     double posX = entity.m_20185_() + x;
                     double posY = entity.m_20186_() + y;
                     double posZ = entity.m_20189_() + z;
                     mutpos.m_122169_(posX, posY, posZ);
                     HashBlockPos pos = new HashBlockPos(mutpos.m_7949_(), (int)(x * x + y * y + z * z));
                     this.blockTrackerComponent.addBlockToQueue(pos, Blocks.f_50568_.m_49966_(), 3, PROTECTION_RULE, placementCallback);
                  }
               }
            }
         }

      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1, false, false));
      this.blockTrackerComponent.tickBlockQueue(entity.m_9236_());
   }

   private void stopChargeEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 200.0F + this.chargeComponent.getChargeTime());
   }

   static {
      PROTECTION_RULE = (new BlockProtectionRule.Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID})).addReplaceRules((world, pos, state) -> {
         if (state.m_60734_().equals(Blocks.f_50125_) && (Integer)state.m_61143_(SnowLayerBlock.f_56581_) > 5) {
            world.m_7731_(pos, Blocks.f_50568_.m_49966_(), 3);
         }

         return true;
      }).build();
   }
}
