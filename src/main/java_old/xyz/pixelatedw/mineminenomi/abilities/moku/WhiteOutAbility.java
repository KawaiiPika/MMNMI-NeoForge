package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CombatHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteOutProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;

public class WhiteOutAbility extends Ability {
   private static final int COOLDOWN = 240;
   private static final int HOLD_TIME = 60;
   public static final RegistryObject<AbilityCore<WhiteOutAbility>> INSTANCE = ModRegistry.registerAbility("white_out", "White Out", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Fires both fists at an enemy and lifts them up, moving them around according to the user's movements", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, WhiteOutAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ContinuousComponent.getTooltip(60.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SMOKE).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onContinuityStart).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private LivingEntity grabbedEntity = null;
   private boolean pinnedCamera = false;
   private WhiteOutProjectile proj = null;

   public WhiteOutAbility(AbilityCore<WhiteOutAbility> core) {
      super(core);
      super.addComponents(this.continuousComponent, this.projectileComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 60.0F);
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.proj = (WhiteOutProjectile)this.projectileComponent.getNewProjectile(entity);
      this.proj.addEntityHitEvent(90, (hit) -> {
         Entity patt3659$temp = hit.m_82443_();
         if (patt3659$temp instanceof LivingEntity target) {
            this.grabbedEntity = target;
         }

      });
      entity.m_9236_().m_7967_(this.proj);
      this.proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 3.0F, 0.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if ((this.proj == null || !this.proj.m_6084_()) && this.grabbedEntity == null) {
            this.continuousComponent.stopContinuity(entity);
         } else {
            if (this.grabbedEntity == null || this.grabbedEntity.m_6084_() && !CombatHelper.isGuardBlocking(this.grabbedEntity)) {
               if (this.grabbedEntity != null) {
                  this.grabbedEntity.m_146922_(this.grabbedEntity.f_19859_);
                  this.grabbedEntity.m_146926_(this.grabbedEntity.f_19860_);
                  this.grabbedEntity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
                  this.grabbedEntity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SMOKE.get(), 60, 0));
                  double distance = (double)7.0F;
                  Vec3 lookVec = entity.m_20154_();
                  Vec3 pos = new Vec3(lookVec.f_82479_ * distance, (double)entity.m_20192_() / (double)2.0F + lookVec.f_82480_ * distance, lookVec.f_82481_ * distance);
                  AbilityHelper.setDeltaMovement(this.grabbedEntity, entity.m_20182_().m_82549_(pos).m_82546_(this.grabbedEntity.m_20182_()));
                  if (!this.pinnedCamera) {
                     LivingEntity var8 = this.grabbedEntity;
                     if (var8 instanceof ServerPlayer) {
                        ServerPlayer playerTarget = (ServerPlayer)var8;
                        ModNetwork.sendTo(SPinCameraPacket.pinFixed(), playerTarget);
                        this.pinnedCamera = true;
                     }
                  }

                  this.grabbedEntity.f_19789_ = 0.0F;
               }
            } else {
               this.continuousComponent.stopContinuity(entity);
            }

         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.proj = null;
      if (entity instanceof ServerPlayer playerTarget) {
         ModNetwork.sendTo(new SUnpinCameraPacket(), playerTarget);
      }

      this.grabbedEntity = null;
      super.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private WhiteOutProjectile createProjectile(LivingEntity entity) {
      WhiteOutProjectile proj = new WhiteOutProjectile(entity.m_9236_(), entity, this);
      return proj;
   }
}
