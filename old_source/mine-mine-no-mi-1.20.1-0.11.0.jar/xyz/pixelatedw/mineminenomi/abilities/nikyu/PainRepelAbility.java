package xyz.pixelatedw.mineminenomi.abilities.nikyu;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.PainEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;

public class PainRepelAbility extends Ability {
   private static final float COOLDOWN = 1200.0F;
   public static final RegistryObject<AbilityCore<PainRepelAbility>> INSTANCE = ModRegistry.registerAbility("pain_repel", "Pain Repel", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Extracts all the damage their target has suffered condensing it into a small paw-shaped ball.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PainRepelAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1200.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceType(SourceType.FRIENDLY).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(100, this::tryHitEvent).addOnHitEvent(100, this::onHitEvent);
   private final SwingTriggerComponent swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(0, this::onSwing);
   private final ExplosionComponent explosionComponent = new ExplosionComponent(this);
   private Interval painAddInterval = new Interval(10);
   private LivingEntity target;
   private State state;
   private float pain;
   private float initialTargetHealthDiff;
   private boolean pinnedCamera;

   public PainRepelAbility(AbilityCore<PainRepelAbility> core) {
      super(core);
      this.state = PainRepelAbility.State.IDLE;
      this.pain = 0.0F;
      this.initialTargetHealthDiff = 0.0F;
      this.pinnedCamera = false;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTriggerComponent, this.swingTriggerComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (target.m_21223_() >= target.m_21233_()) {
         return HitTriggerComponent.HitResult.PASS;
      } else if (CombatHelper.isTargetBlocking(entity, target)) {
         return HitTriggerComponent.HitResult.PASS;
      } else {
         return this.continuousComponent.isContinuous() && entity.m_21205_().m_41619_() && this.state == PainRepelAbility.State.IDLE ? HitTriggerComponent.HitResult.HIT : HitTriggerComponent.HitResult.PASS;
      }
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      this.painAddInterval.restartIntervalToZero();
      this.state = PainRepelAbility.State.HEALING;
      this.target = target;
      this.initialTargetHealthDiff = target.m_21233_() - target.m_21223_();
      return true;
   }

   private void onSwing(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.continuousComponent.isContinuous() && entity.m_21205_().m_41619_() && this.state == PainRepelAbility.State.READY) {
            PainEntity proj = new PainEntity(entity.m_9236_(), entity, this);
            proj.setDamage(this.pain);
            proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 2.0F, 1.0F);
            entity.m_9236_().m_7967_(proj);
            this.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (this.state != PainRepelAbility.State.IDLE) {
         if (this.target != null && this.state == PainRepelAbility.State.HEALING) {
            if (!this.pinnedCamera) {
               LivingEntity var4 = this.target;
               if (var4 instanceof ServerPlayer) {
                  ServerPlayer playerTarget = (ServerPlayer)var4;
                  ModNetwork.sendTo(SPinCameraPacket.pinFixed(), playerTarget);
                  this.pinnedCamera = true;
               }
            }

            this.target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
            if (this.painAddInterval.canTick()) {
               float extraPain = this.target.m_21233_() / 10.0F;
               this.pain += extraPain;
               this.target.m_5634_(extraPain);
               entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.PAD_HO_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            }

            float diff = this.target.m_21233_() - this.target.m_21223_();
            if ((diff <= 0.0F || this.pain >= this.initialTargetHealthDiff) && !entity.f_20911_) {
               this.state = PainRepelAbility.State.READY;
               LivingEntity var5 = this.target;
               if (var5 instanceof ServerPlayer) {
                  ServerPlayer playerTarget = (ServerPlayer)var5;
                  ModNetwork.sendTo(new SUnpinCameraPacket(), playerTarget);
               }
            }
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.target = null;
      this.state = PainRepelAbility.State.IDLE;
      this.pain = 0.0F;
      this.initialTargetHealthDiff = 0.0F;
      this.pinnedCamera = false;
      super.cooldownComponent.startCooldown(entity, 1200.0F);
   }

   private static enum State {
      IDLE,
      HEALING,
      READY;

      // $FF: synthetic method
      private static State[] $values() {
         return new State[]{IDLE, HEALING, READY};
      }
   }
}
