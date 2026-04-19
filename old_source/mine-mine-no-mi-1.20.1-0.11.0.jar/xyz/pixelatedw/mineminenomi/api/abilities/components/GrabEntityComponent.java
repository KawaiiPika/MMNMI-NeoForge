package xyz.pixelatedw.mineminenomi.api.abilities.components;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;

public class GrabEntityComponent extends AbilityComponent<IAbility> {
   private LivingEntity grabbedEntity;
   private boolean handleTargetCamera;
   private boolean handleTargetPosition;
   private boolean handleTargetPulling;
   private float heldDistance;
   private float startHealth;
   private boolean cameraPinned;
   private double previousDistance;
   private final PriorityEventPool<IOnGrabEvent> grabEvents;
   private final PriorityEventPool<IOnPullStartEvent> pullStartEvents;
   private final PriorityEventPool<IOnPullTickEvent> pullTickEvents;
   private final PriorityEventPool<IOnPullEndEvent> pullEndEvents;
   private final PriorityEventPool<IOnReleaseEvent> releaseEvents;
   private GrabState state;

   public GrabEntityComponent(IAbility ability, boolean handleTargetCamera, boolean handleTargetPosition, float heldDistance) {
      this(ability, handleTargetCamera, handleTargetPosition, false, heldDistance);
   }

   public GrabEntityComponent(IAbility ability, boolean handleTargetCamera, boolean handleTargetPosition, boolean handleTargetPulling, float heldDistance) {
      super((AbilityComponentKey)ModAbilityComponents.GRAB.get(), ability);
      this.grabbedEntity = null;
      this.handleTargetCamera = false;
      this.handleTargetPosition = false;
      this.handleTargetPulling = false;
      this.heldDistance = 1.0F;
      this.grabEvents = new PriorityEventPool<IOnGrabEvent>();
      this.pullStartEvents = new PriorityEventPool<IOnPullStartEvent>();
      this.pullTickEvents = new PriorityEventPool<IOnPullTickEvent>();
      this.pullEndEvents = new PriorityEventPool<IOnPullEndEvent>();
      this.releaseEvents = new PriorityEventPool<IOnReleaseEvent>();
      this.state = GrabEntityComponent.GrabState.IDLE;
      this.handleTargetCamera = handleTargetCamera;
      this.handleTargetPosition = handleTargetPosition;
      this.handleTargetPulling = handleTargetPulling;
      this.heldDistance = heldDistance;
   }

   public GrabEntityComponent addGrabEvent(IOnGrabEvent event) {
      this.grabEvents.addEvent(100, event);
      return this;
   }

   public GrabEntityComponent addGrabEvent(int priority, IOnGrabEvent event) {
      this.grabEvents.addEvent(priority, event);
      return this;
   }

   public GrabEntityComponent addPullStartEvent(IOnPullStartEvent event) {
      this.pullStartEvents.addEvent(100, event);
      return this;
   }

   public GrabEntityComponent addPullStartEvent(int priority, IOnPullStartEvent event) {
      this.pullStartEvents.addEvent(priority, event);
      return this;
   }

   public GrabEntityComponent addPullTickEvent(IOnPullTickEvent event) {
      this.pullTickEvents.addEvent(100, event);
      return this;
   }

   public GrabEntityComponent addPullTickEvent(int priority, IOnPullTickEvent event) {
      this.pullTickEvents.addEvent(priority, event);
      return this;
   }

   public GrabEntityComponent addPullEndEvent(IOnPullEndEvent event) {
      this.pullEndEvents.addEvent(100, event);
      return this;
   }

   public GrabEntityComponent addPullEndEvent(int priority, IOnPullEndEvent event) {
      this.pullEndEvents.addEvent(priority, event);
      return this;
   }

   public GrabEntityComponent addReleaseEvent(IOnReleaseEvent event) {
      this.releaseEvents.addEvent(100, event);
      return this;
   }

   public GrabEntityComponent addReleaseEvent(int priority, IOnReleaseEvent event) {
      this.releaseEvents.addEvent(priority, event);
      return this;
   }

   public void doTick(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         if (this.canContinueGrab(entity)) {
            if (this.state == GrabEntityComponent.GrabState.PULLING && this.handleTargetPulling) {
               this.grabbedEntity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 2, 3));
               Vec3 difference = entity.m_20299_(1.0F).m_82520_((double)0.0F, (double)-1.0F, (double)0.0F).m_82546_(this.grabbedEntity.m_20182_());
               double distance = Math.abs(entity.m_20299_(1.0F).m_82554_(this.grabbedEntity.m_20182_()));
               if (distance > this.previousDistance + (double)2.0F) {
                  this.release(entity);
                  return;
               }

               this.previousDistance = distance;
               double length = (double)Mth.m_14116_((float)(difference.f_82479_ * difference.f_82479_ + difference.f_82481_ * difference.f_82481_));
               double safeDifferenceY = difference.f_82480_ != (double)0.0F ? (double)1.0F / Math.abs(difference.f_82480_) : (double)0.0F;
               difference = difference.m_82542_((double)1.0F / length, safeDifferenceY, (double)1.0F / length).m_82490_(0.1);
               AbilityHelper.setDeltaMovement(this.grabbedEntity, this.grabbedEntity.m_20184_().m_82549_(difference));
               float reach = (float)entity.m_21133_((Attribute)ForgeMod.ENTITY_REACH.get()) + 3.0F;
               if (this.isTargetInRange(entity, reach, entity.m_20205_() / 2.0F + 0.1F)) {
                  this.stopPulling(entity);
               } else {
                  this.pullTickEvents.dispatch((event) -> event.pullTick(entity, super.getAbility()));
               }
            }

            if (this.state == GrabEntityComponent.GrabState.GRABBED) {
               if (this.handleTargetCamera && !this.cameraPinned) {
                  LivingEntity pos = this.grabbedEntity;
                  if (pos instanceof ServerPlayer) {
                     ServerPlayer grabbedPlayer = (ServerPlayer)pos;
                     ModNetwork.sendTo(SPinCameraPacket.pinFixed(), grabbedPlayer);
                     this.cameraPinned = true;
                  }
               }

               if (this.handleTargetPosition) {
                  this.grabbedEntity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 2, 3));
                  Vec3 lookVec = entity.m_20154_().m_82541_();
                  Vec3 pos = new Vec3(lookVec.f_82479_ * (double)this.heldDistance, (double)entity.m_20192_() - (double)1.0F + lookVec.f_82480_ * (double)this.heldDistance, lookVec.f_82481_ * (double)this.heldDistance);
                  AbilityHelper.setDeltaMovement(this.grabbedEntity, entity.m_20182_().m_82549_(pos).m_82546_(this.grabbedEntity.m_20182_()), true);
                  this.grabbedEntity.f_19789_ = 0.0F;
               }
            }
         } else if (this.grabbedEntity != null) {
            if (this.state == GrabEntityComponent.GrabState.PULLING) {
               this.stopPulling(entity);
            }

            this.release(entity);
         }

      }
   }

   public void triggerPulling(LivingEntity entity) {
      if (this.state == GrabEntityComponent.GrabState.PULLING) {
         this.stopPulling(entity);
      } else {
         this.startPulling(entity);
      }

   }

   public void startPulling(LivingEntity entity) {
      this.state = GrabEntityComponent.GrabState.PULLING;
      this.pullStartEvents.dispatch((event) -> event.pullStart(entity, super.getAbility()));
   }

   public void stopPulling(LivingEntity entity) {
      this.state = GrabEntityComponent.GrabState.GRABBED;
      this.pullEndEvents.dispatch((event) -> event.pullEnd(entity, super.getAbility()));
   }

   public void throwTarget(LivingEntity entity, double horizontalPower, double verticalPower) {
      this.state = GrabEntityComponent.GrabState.THROWN;
      if (this.canContinueGrab(entity)) {
         this.grabbedEntity.m_21195_((MobEffect)ModEffects.GRABBED.get());
         Vec3 diff = entity.m_20182_().m_82546_(this.grabbedEntity.m_20182_()).m_82541_().m_82542_(-horizontalPower, -verticalPower, -horizontalPower);
         AbilityHelper.setDeltaMovement(this.grabbedEntity, diff);
      }

   }

   public void release(LivingEntity entity) {
      this.state = GrabEntityComponent.GrabState.IDLE;
      if (this.grabbedEntity != null && this.grabbedEntity.m_6084_()) {
         this.releaseEvents.dispatch((event) -> event.release(entity, this.grabbedEntity, this.getAbility()));
         LivingEntity var3 = this.grabbedEntity;
         if (var3 instanceof ServerPlayer) {
            ServerPlayer grabbedPlayer = (ServerPlayer)var3;
            ModNetwork.sendTo(new SUnpinCameraPacket(), grabbedPlayer);
         }
      }

      this.grabbedEntity = null;
   }

   public boolean grabManually(LivingEntity entity, LivingEntity target) {
      this.ensureIsRegistered();
      if (target != null && target.m_6084_() && !CombatHelper.isTargetBlocking(entity, target)) {
         boolean flag = !this.grabEvents.dispatchCancelable((event) -> !event.grab(entity, target, this.getAbility()));
         if (flag) {
            this.grabbedEntity = target;
            this.previousDistance = Math.abs(entity.m_20299_(1.0F).m_82554_(this.grabbedEntity.m_20182_()));
            this.cameraPinned = false;
            this.startHealth = entity.m_21223_();
            this.state = GrabEntityComponent.GrabState.GRABBED;
            return true;
         }
      }

      return false;
   }

   public boolean isTargetInRange(LivingEntity entity) {
      return this.isTargetInRange(entity, 3.0F, 0.4F);
   }

   public boolean isTargetInRange(LivingEntity entity, float distance, float size) {
      float reach = (float)Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(entity, (double)distance));
      boolean targetInRange = TargetHelper.getEntitiesInArea(entity.m_9236_(), entity, entity.m_20183_(), (double)reach, (double)entity.m_20206_(), (double)size, (TargetPredicate)null, LivingEntity.class).stream().anyMatch((target) -> target.equals(this.grabbedEntity));
      return targetInRange;
   }

   public boolean grabNearest(LivingEntity entity) {
      double reach = Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(entity, (double)3.0F));
      return this.grabNearest(entity, (float)reach, 0.4F, true);
   }

   public boolean grabNearest(LivingEntity entity, boolean sendFailMessage) {
      double reach = Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(entity, (double)3.0F));
      return this.grabNearest(entity, (float)reach, 0.4F, sendFailMessage);
   }

   public boolean grabNearest(LivingEntity entity, float distance, float size) {
      return this.grabNearest(entity, distance, size, true);
   }

   public boolean grabNearest(LivingEntity entity, float distance, float size, boolean sendFailMessage) {
      this.ensureIsRegistered();
      LivingEntity target = CombatHelper.canGrab(entity, distance, size, sendFailMessage);
      boolean flag = !this.grabEvents.dispatchCancelable((event) -> !event.grab(entity, target, this.getAbility()));
      if (flag && target != null && target.m_6084_()) {
         this.grabbedEntity = target;
         this.previousDistance = Math.abs(entity.m_20299_(1.0F).m_82554_(this.grabbedEntity.m_20182_()));
         this.cameraPinned = false;
         this.startHealth = entity.m_21223_();
         this.state = GrabEntityComponent.GrabState.GRABBED;
         return true;
      } else {
         return false;
      }
   }

   public boolean canContinueGrab(LivingEntity entity) {
      if (this.grabbedEntity != null && this.grabbedEntity.m_6084_()) {
         if (CombatHelper.isDodging(this.grabbedEntity)) {
            return false;
         } else if (CombatHelper.isLogiaBlocking(this.grabbedEntity)) {
            return false;
         } else {
            return !(entity.m_21223_() < this.startHealth - 10.0F);
         }
      } else {
         return false;
      }
   }

   public @Nullable LivingEntity getGrabbedEntity() {
      return this.grabbedEntity;
   }

   public boolean hasGrabbedEntity() {
      return this.grabbedEntity != null && this.grabbedEntity.m_6084_();
   }

   public static boolean hasGrabbed(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.getLazy(entity).orElse((Object)null);
      if (props == null) {
         return false;
      } else {
         for(IAbility abl : props.getEquippedAndPassiveAbilities()) {
            if (abl.hasComponent((AbilityComponentKey)ModAbilityComponents.GRAB.get()) && !abl.equals(ability)) {
               boolean isOnCooldown = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown()).orElse(false);
               boolean isDisabled = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).map((comp) -> comp.isDisabled()).orElse(false);
               if (!isOnCooldown && !isDisabled && ((GrabEntityComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.GRAB.get()).get()).hasGrabbedEntity()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public GrabState getState() {
      return this.state;
   }

   public void setState(GrabState state) {
      this.state = state;
   }

   public static enum GrabState {
      IDLE,
      PULLING,
      GRABBED,
      THROWN;

      // $FF: synthetic method
      private static GrabState[] $values() {
         return new GrabState[]{IDLE, PULLING, GRABBED, THROWN};
      }
   }

   @FunctionalInterface
   public interface IOnGrabEvent {
      boolean grab(LivingEntity var1, LivingEntity var2, IAbility var3);
   }

   @FunctionalInterface
   public interface IOnPullEndEvent {
      void pullEnd(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IOnPullStartEvent {
      void pullStart(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IOnPullTickEvent {
      void pullTick(LivingEntity var1, IAbility var2);
   }

   @FunctionalInterface
   public interface IOnReleaseEvent {
      void release(LivingEntity var1, LivingEntity var2, IAbility var3);
   }
}
