package xyz.pixelatedw.mineminenomi.abilities.mera;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joml.Vector3d;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.DaiEnkaiEnteiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.DaiEnkaiOnibiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.mera.HibashiraParticleEffect;

public class DaiEnkaiAbility extends Ability {
   private static final Component DAI_ENKAI_ONIBI_NAME = Component.m_237115_(ModRegistry.registerAbilityName("dai_enkai_onibi", "Dai Enkai: Onibi"));
   private static final ResourceLocation DAI_ENKAI_ENTEI_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/dai_enkai_entei.png");
   private static final ResourceLocation DAI_ENKAI_ONIBI_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/dai_enkai_onibi.png");
   private static final float COOLDOWN = 500.0F;
   public static final float ENTEI_CHARGE_TIME = 160.0F;
   private static final float ENTEI_ON_HOLD_TIME = 160.0F;
   private static final float ONIBI_CHARGE_TIME = 100.0F;
   private static final double PILLAR_SIZE = (double)25.0F;
   private static final float ENTEI_MIN_DAMAGE = 24.0F;
   private static final float ENTEI_MAX_DAMAGE = 104.0F;
   private static final float ONIBI_PROJ_DAMAGE = 75.0F;
   private static final float ONIBI_PILLAR_DAMAGE = 5.0F;
   private Vec3 onibiStartPos;
   private static final AbilityDescriptionLine.IDescriptionLine ONIBI_DAMAGE_TOOLTIP = (entity, ability) -> {
      NuProjectileEntity proj = (NuProjectileEntity)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map((comp) -> comp.getCachedProjectile(entity)).orElse((Object)null);
      if (proj != null && proj.getDamage() > 0.0F) {
         float bonus = (Float)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map(ProjectileComponent::getDamageBonusManager).map((manager) -> manager.applyBonus(proj.getDamage()) - proj.getDamage()).orElse(0.0F);
         AbilityStat.AbilityStatType bonusType = bonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (bonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
         AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE, 75.0F)).withBonus(bonus, bonusType).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_X3);
         return statBuilder.build().getStatDescription(2);
      } else {
         return null;
      }
   };
   private static final AbilityDescriptionLine.IDescriptionLine ENTEI_DAMAGE_TOOLTIP = (entity, ability) -> {
      NuProjectileEntity proj = (NuProjectileEntity)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map((comp) -> comp.getCachedProjectile(entity)).orElse((Object)null);
      if (proj != null && proj.getDamage() > 0.0F) {
         float bonus = (Float)ability.getComponent((AbilityComponentKey)ModAbilityComponents.PROJECTILE.get()).map(ProjectileComponent::getDamageBonusManager).map((manager) -> manager.applyBonus(proj.getDamage()) - proj.getDamage()).orElse(0.0F);
         AbilityStat.AbilityStatType bonusType = bonus > 0.0F ? AbilityStat.AbilityStatType.BUFF : (bonus < 0.0F ? AbilityStat.AbilityStatType.DEBUFF : AbilityStat.AbilityStatType.NEUTRAL);
         AbilityStat.Builder statBuilder = (new AbilityStat.Builder(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE, 24.0F, 104.0F)).withBonus(bonus, bonusType);
         return statBuilder.build().getStatDescription(2);
      } else {
         return null;
      }
   };
   public static final RegistryObject<AbilityCore<DaiEnkaiAbility>> INSTANCE = ModRegistry.registerAbility("dai_enkai_entei", "Dai Enkai: Entei", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Amasses the user's flames into a gigantic fireball that the user hurls at the opponent", (Object)null), ImmutablePair.of("Creates a giant fire vortex around the user, trapping enemies within it and creating three fire dragons that will be shot towards the enemy at the end.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DaiEnkaiAbility::new)).addAdvancedDescriptionLine((e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[0], CooldownComponent.getTooltip(500.0F), ChargeComponent.getTooltip(160.0F), ContinuousComponent.getTooltip(160.0F), (e, a) -> Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString() + "§r"), ENTEI_DAMAGE_TOOLTIP).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> DAI_ENKAI_ONIBI_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[1], CooldownComponent.getTooltip(500.0F), ChargeComponent.getTooltip(100.0F), (e, a) -> Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString() + "§r"), ONIBI_DAMAGE_TOOLTIP).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.FIRE).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent;
   private final ContinuousComponent continuousComponent;
   private final SwingTriggerComponent swingTriggerComponent;
   private final AltModeComponent<Mode> altModeComponent;
   private final AnimationComponent animationComponent;
   private final HitTrackerComponent hitTrackerComponent;
   private final DealDamageComponent dealDamageComponent;
   private final ProjectileComponent projectileComponent;
   private final ExplosionComponent explosionComponent;
   private final Interval particleInterval;
   private final Interval clearHitsInterval;
   private DaiEnkaiEnteiProjectile enteiProjectile;
   private DaiEnkaiOnibiProjectile[] onibiProjectiles;

   public DaiEnkaiAbility(AbilityCore<DaiEnkaiAbility> core) {
      super(core);
      this.onibiStartPos = Vec3.f_82478_;
      this.chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
      this.continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
      this.swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(this::onSwing);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, DaiEnkaiAbility.Mode.ENTEI)).addChangeModeEvent(this::onAltModeChange);
      this.animationComponent = new AnimationComponent(this);
      this.hitTrackerComponent = new HitTrackerComponent(this);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.explosionComponent = new ExplosionComponent(this, 42875);
      this.particleInterval = new Interval(2);
      this.clearHitsInterval = new Interval(20);
      this.onibiProjectiles = new DaiEnkaiOnibiProjectile[3];
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.chargeComponent, this.swingTriggerComponent, this.altModeComponent, this.animationComponent, this.projectileComponent, this.hitTrackerComponent, this.dealDamageComponent, this.explosionComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ENTEI) {
            if (!this.chargeComponent.isCharging() && !this.continuousComponent.isContinuous()) {
               this.chargeComponent.startCharging(entity, 160.0F);
            } else if (this.enteiProjectile != null && this.enteiProjectile.m_6084_()) {
               BlockHitResult result = new BlockHitResult(this.enteiProjectile.m_20182_(), this.enteiProjectile.m_6350_(), this.enteiProjectile.m_20183_(), false);
               this.enteiProjectile.m_8060_(result);
               this.enteiProjectile.m_142687_(RemovalReason.DISCARDED);
            }
         } else if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ONIBI) {
            if (!entity.m_20096_()) {
               WyHelper.sendMessage(entity, ModI18nAbilities.MESSAGE_ONLY_IN_GROUND);
               return;
            }

            this.chargeComponent.startCharging(entity, 100.0F);
         }

      }
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.particleInterval.restartIntervalToZero();
      if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ENTEI) {
         this.animationComponent.start(entity, ModAnimations.RAISE_RIGHT_ARM);
         this.enteiProjectile = (DaiEnkaiEnteiProjectile)this.projectileComponent.getNewProjectile(entity);
         this.enteiProjectile.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)entity.m_20192_() + (double)7.5F, entity.m_20189_());
         entity.m_9236_().m_7967_(this.enteiProjectile);
      } else if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ONIBI) {
         this.onibiStartPos = entity.m_20182_();
         this.animationComponent.start(entity, ModAnimations.RAISE_ARMS);
         float f1 = -entity.m_146908_() * ((float)Math.PI / 180F);
         float f2 = Mth.m_14089_(f1);
         float f3 = Mth.m_14031_(f1);
         Vector3d throwerLook = new Vector3d((double)f3, (double)0.0F, (double)f2);
         double offsetX = throwerLook.x * (double)30.0F;
         double offsetY = (double)20.0F;
         double offsetZ = throwerLook.z * (double)30.0F;
         this.onibiProjectiles = new DaiEnkaiOnibiProjectile[]{(DaiEnkaiOnibiProjectile)this.projectileComponent.getNewProjectile(entity), (DaiEnkaiOnibiProjectile)this.projectileComponent.getNewProjectile(entity), (DaiEnkaiOnibiProjectile)this.projectileComponent.getNewProjectile(entity)};
         this.onibiProjectiles[0].m_6034_(entity.m_20185_() + offsetZ, entity.m_20186_() + offsetY, entity.m_20189_() - offsetX);
         this.onibiProjectiles[1].m_6034_(entity.m_20185_() - offsetX, entity.m_20186_() + offsetY, entity.m_20189_() - offsetZ);
         this.onibiProjectiles[2].m_6034_(entity.m_20185_() - offsetZ, entity.m_20186_() + offsetY, entity.m_20189_() + offsetX);

         for(DaiEnkaiOnibiProjectile onibiProjectile : this.onibiProjectiles) {
            entity.m_9236_().m_7967_(onibiProjectile);
         }
      }

   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ENTEI) {
         if (this.enteiProjectile == null || !this.enteiProjectile.m_6084_()) {
            this.chargeComponent.stopCharging(entity);
            return;
         }

         this.enteiProjectile.setLife(this.enteiProjectile.getMaxLife());
         this.enteiProjectile.increaseSize();
         this.enteiProjectile.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)entity.m_20192_() + (double)7.5F, entity.m_20189_());
         if (!entity.m_9236_().f_46443_ && this.particleInterval.canTick()) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DAI_ENKAI_2.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }
      } else if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ONIBI) {
         double yourDistance = (double)37.5F;
         Vec3 lookAngle = entity.m_20154_();
         Vec3 targetPoint = entity.m_20299_(1.0F).m_82520_(lookAngle.f_82479_ * yourDistance, lookAngle.f_82480_ * yourDistance, lookAngle.f_82481_ * yourDistance);

         for(DaiEnkaiOnibiProjectile onibiProjectile : this.onibiProjectiles) {
            if (onibiProjectile != null && onibiProjectile.m_6084_()) {
               onibiProjectile.setLife(onibiProjectile.getMaxLife());
               Vec3 projectilePos = onibiProjectile.m_20182_();
               double totalDeltaX = targetPoint.f_82479_ - projectilePos.f_82479_;
               double totalDeltaY = targetPoint.f_82480_ - projectilePos.f_82480_;
               double totalDeltaZ = targetPoint.f_82481_ - projectilePos.f_82481_;
               double distanceXZ = Math.sqrt(totalDeltaX * totalDeltaX + totalDeltaZ * totalDeltaZ);
               double pitch = -Math.atan2(totalDeltaY, distanceXZ);
               double yaw = Math.atan2(-totalDeltaX, totalDeltaZ);
               pitch = Math.toDegrees(pitch);
               yaw = Math.toDegrees(yaw);
               onibiProjectile.m_146922_((float)(-yaw));
               onibiProjectile.m_146926_((float)(-pitch));
            }
         }

         if (!entity.m_9236_().f_46443_ && this.particleInterval.canTick()) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HIBASHIRA.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), new HibashiraParticleEffect.Details(10.0F, 4.0F, 4.0F));
         }

         double perimeter = (double)41.5F;

         for(LivingEntity target : TargetHelper.getEntitiesAroundCircle(entity, entity.m_20182_(), perimeter, (double)4.0F, TargetPredicate.DEFAULT_AREA_CHECK, LivingEntity.class)) {
            if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 5.0F)) {
               target.m_20254_(4);
            }

            Vec3 rejectionVec = this.onibiStartPos.m_82546_(target.m_20182_()).m_82541_();
            AbilityHelper.setDeltaMovement(target, rejectionVec);
         }

         if (this.clearHitsInterval.canTick()) {
            this.hitTrackerComponent.clearHits();
         }

         AbilityHelper.slowEntityFall(entity);
         entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
      }

   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ENTEI) {
         this.continuousComponent.startContinuity(entity, 160.0F);
      } else if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ONIBI) {
         for(DaiEnkaiOnibiProjectile onibiProjectile : this.onibiProjectiles) {
            if (onibiProjectile != null && onibiProjectile.m_6084_()) {
               onibiProjectile.m_37251_(entity, -onibiProjectile.m_146909_(), -onibiProjectile.m_146908_(), 0.0F, 3.0F, 1.0F);
            }
         }

         this.animationComponent.stop(entity);
         super.cooldownComponent.startCooldown(entity, 500.0F);
      }

   }

   private void onSwing(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.enteiProjectile.m_37251_(entity, entity.m_146909_() + 10.0F, entity.m_146908_(), 0.0F, 3.0F, 1.0F);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.MERA_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
         if (!entity.m_9236_().f_46443_) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DAI_ENKAI_1.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (this.enteiProjectile != null && this.enteiProjectile.m_6084_()) {
         this.enteiProjectile.setLife(this.enteiProjectile.getMaxLife());
         this.enteiProjectile.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)entity.m_20192_() + (double)7.5F, entity.m_20189_());
      } else {
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.enteiProjectile != null && this.enteiProjectile.m_6084_() && this.enteiProjectile.getLife() < this.enteiProjectile.getMaxLife()) {
            BlockHitResult result = new BlockHitResult(this.enteiProjectile.m_20182_(), this.enteiProjectile.m_6350_(), this.enteiProjectile.m_20183_(), false);
            this.enteiProjectile.m_6532_(result);
            this.enteiProjectile.m_142687_(RemovalReason.DISCARDED);
         }

         this.enteiProjectile = null;
         this.animationComponent.stop(entity);
         super.cooldownComponent.startCooldown(entity, 500.0F);
      }
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == DaiEnkaiAbility.Mode.ENTEI) {
         super.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         super.setDisplayIcon(DAI_ENKAI_ENTEI_ICON);
      } else if (mode == DaiEnkaiAbility.Mode.ONIBI) {
         super.setDisplayName(DAI_ENKAI_ONIBI_NAME);
         super.setDisplayIcon(DAI_ENKAI_ONIBI_ICON);
      }

      return true;
   }

   private NuProjectileEntity createProjectile(LivingEntity entity) {
      if (this.altModeComponent.getCurrentMode() == DaiEnkaiAbility.Mode.ONIBI) {
         DaiEnkaiOnibiProjectile proj = new DaiEnkaiOnibiProjectile(entity.m_9236_(), entity, this);
         return proj;
      } else {
         DaiEnkaiEnteiProjectile proj = new DaiEnkaiEnteiProjectile(entity.m_9236_(), entity, this);
         return proj;
      }
   }

   public static enum Mode {
      ENTEI,
      ONIBI;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{ENTEI, ONIBI};
      }
   }
}
