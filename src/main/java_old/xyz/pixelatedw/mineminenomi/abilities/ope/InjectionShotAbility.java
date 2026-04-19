package xyz.pixelatedw.mineminenomi.abilities.ope;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;

public class InjectionShotAbility extends Ability {
   private static final float COOLDOWN = 200.0F;
   private static final float DAMAGE = 40.0F;
   private static final float RANGE = 1.6F;
   public static final RegistryObject<AbilityCore<InjectionShotAbility>> INSTANCE = ModRegistry.registerAbility("injection_shot", "Injection Shot", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While holding a weapon, the user charges at the enemy, leaving them confused.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, InjectionShotAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(40.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.SLASH).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addEndEvent(this::onChargeEnd);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public InjectionShotAbility(AbilityCore<InjectionShotAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.dealDamageComponent, this.hitTrackerComponent, this.animationComponent, this.rangeComponent});
      this.addCanUseCheck(RoomAbility::hasRoomActive);
      this.addCanUseCheck(AbilityUseConditions::requiresSword);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addContinueUseCheck(RoomAbility::hasRoomActive);
      this.addContinueUseCheck(AbilityUseConditions::requiresSword);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging() && !this.continuousComponent.isContinuous()) {
         this.chargeComponent.startCharging(entity, 20.0F);
      }

   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.POINT_WEAPON);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 100, 0));
      if (entity instanceof ServerPlayer serverPlayer) {
         ModNetwork.sendTo(SPinCameraPacket.pinFixed(), serverPlayer);
      }

   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         entity.m_21195_((MobEffect)ModEffects.MOVEMENT_BLOCKED.get());
         if (entity instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)entity;
            ModNetwork.sendTo(new SUnpinCameraPacket(), serverPlayer);
         }

         this.continuousComponent.startContinuity(entity, 20.0F);
      }
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         Vec3 dirVec = entity.m_20154_().m_82542_((double)4.0F, (double)1.0F, (double)4.0F);
         AbilityHelper.setDeltaMovement(entity, dirVec.f_82479_, 0.35, dirVec.f_82481_);
         this.hitTrackerComponent.clearHits();
      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (entity.m_6084_()) {
            List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 1.6F);
            DamageSource source = this.dealDamageComponent.getDamageSource(entity);
            IDamageSourceHandler.getHandler(source).addAbilityPiercing(0.5F, this.getCore());
            targets.remove(entity);

            for(LivingEntity target : targets) {
               if (this.hitTrackerComponent.canHit(entity) && this.dealDamageComponent.hurtTarget(entity, target, 40.0F, source)) {
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 60, 0));
               }
            }
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
