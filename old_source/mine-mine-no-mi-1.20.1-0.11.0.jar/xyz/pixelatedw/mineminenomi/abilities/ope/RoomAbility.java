package xyz.pixelatedw.mineminenomi.abilities.ope;

import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RoomAbility extends Ability {
   public static final int MIN_ROOM_SIZE = 8;
   public static final int MAX_ROOM_SIZE = 45;
   public static final int MAX_THRESHOLD = 2;
   private static final int CHARGE_TIME = 20;
   private static final float MIN_COOLDOWN = 10.0F;
   private static final float MAX_COOLDOWN = 50.0F;
   public static final RegistryObject<AbilityCore<RoomAbility>> INSTANCE = ModRegistry.registerAbility("room", "ROOM", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a spherical space around the user in which they can manipulate anything or use other skills", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RoomAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(10.0F, 50.0F), ChargeComponent.getTooltip(20.0F)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private SphereEntity roomEntity;
   private float roomSize = 0.0F;
   private BlockPos centerPos;
   private boolean isShrinking = false;
   private Interval checkPositionInterval = new Interval(20);
   private Interval playSoundInterval = new Interval(18);

   public RoomAbility(AbilityCore<RoomAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.isCharging()) {
         this.chargeComponent.stopCharging(entity);
      } else {
         if (this.continuousComponent.isContinuous()) {
            this.isShrinking = true;
            this.chargeComponent.startCharging(entity, 10.0F);
            return;
         }

         this.checkPositionInterval.restartIntervalToZero();
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.ROOM_CREATE_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
         this.isShrinking = false;
         this.setupSphere(entity);
         float entityHpDebuff = 2.0F - entity.m_21223_() / entity.m_21233_();
         float chargeTime = 20.0F * entityHpDebuff;
         this.chargeComponent.startCharging(entity, chargeTime);
      }

   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      this.chargeTickSphere(entity);
      if (this.playSoundInterval.canTick()) {
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.ROOM_CHARGE_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
      }

   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.endChargeSphere(entity);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.ROOM_EXPAND_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
         this.continuousComponent.startContinuity(entity, -1.0F);
      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.continuityTickSphere(entity)) {
            ;
         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.continuityEndSphere(entity);
      this.centerPos = null;
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.ROOM_CLOSE_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
      float entityHpDebuff = 2.0F - entity.m_21223_() / entity.m_21233_();
      float roomSizeDebuff = this.roomSize / 45.0F;
      float cooldown = 10.0F * entityHpDebuff * roomSizeDebuff;
      cooldown = Mth.m_14036_(cooldown, 10.0F, 50.0F);
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private void setupSphere(LivingEntity entity) {
      this.roomEntity = new SphereEntity(entity.m_9236_(), entity);
      this.roomEntity.setColor(new Color(0, 255, 255, 50));
      this.roomEntity.setRadius(0.0F);
      this.roomEntity.setDetailLevel(32);
      this.roomEntity.setAnimationSpeed(1);
      entity.m_9236_().m_7967_(this.roomEntity);
      this.centerPos = this.roomEntity.m_20183_();
   }

   private void chargeTickSphere(LivingEntity entity) {
      if (this.roomEntity != null) {
         float radius = 0.0F;
         if (this.isShrinking) {
            radius = this.roomSize * (1.0F - this.chargeComponent.getChargePercentage()) / 45.0F;
            radius = EasingFunctionHelper.easeOutCubic(radius) * 45.0F;
         } else {
            radius = 45.0F * this.chargeComponent.getChargePercentage() / 45.0F;
            radius = EasingFunctionHelper.easeInCubic(radius) * 45.0F;
         }

         this.roomEntity.setRadius(radius);
         this.roomSize = (float)((int)radius);
      }

   }

   private void endChargeSphere(LivingEntity entity) {
      if (!entity.m_9236_().f_46443_) {
         if (this.isShrinking) {
            if (this.roomEntity != null) {
               this.roomEntity.m_146870_();
            }

            this.roomEntity = null;
         } else {
            float radius = Mth.m_14036_(45.0F * this.chargeComponent.getChargePercentage(), 8.0F, 45.0F);
            this.roomEntity.setRadius(radius);
            this.roomSize = (float)((int)radius);
         }

      }
   }

   private boolean continuityTickSphere(LivingEntity entity) {
      if (this.checkPositionInterval.canTick() && !this.isEntityInRoom(entity)) {
         this.continuousComponent.stopContinuity(entity);
         return false;
      } else if (this.roomEntity != null && this.roomEntity.m_6084_()) {
         return true;
      } else {
         this.continuousComponent.stopContinuity(entity);
         return false;
      }
   }

   private void continuityEndSphere(LivingEntity entity) {
      if (this.roomEntity != null) {
         this.roomEntity.m_146870_();
      }

      this.roomEntity = null;
   }

   public float getROOMSize() {
      return this.roomSize;
   }

   public BlockPos getCenterBlock() {
      return this.centerPos;
   }

   public boolean isEntityInRoom(Entity entity) {
      if (this.isPositionInRoom(entity.m_20183_())) {
         return true;
      } else if (this.roomEntity == null) {
         return false;
      } else {
         return entity.m_20280_(this.roomEntity) <= (double)(this.roomSize * this.roomSize);
      }
   }

   public boolean isPositionInRoom(BlockPos pos) {
      return this.centerPos == null ? false : pos.m_123314_(this.centerPos, (double)this.roomSize);
   }

   public static Result hasRoomActive(LivingEntity entity, IAbility ability) {
      RoomAbility roomAbility = (RoomAbility)AbilityCapability.get(entity).map((props) -> (RoomAbility)props.getEquippedAbility((AbilityCore)INSTANCE.get())).orElse((Object)null);
      return roomAbility != null && roomAbility.isEntityInRoom(entity) ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_ONLY_IN_ROOM);
   }
}
