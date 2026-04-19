package xyz.pixelatedw.mineminenomi.abilities.goro;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.ElThorProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.RaigoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RaigoAbility extends Ability {
   private static final ResourceLocation DEFAULT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/raigo.png");
   private static final ResourceLocation ALT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/alts/raigo.png");
   private static final Component DEATHPIEA_MAMARAGAN_NAME;
   private static final ResourceLocation DEATHPIEA_MAMARAGAN_ICON;
   private static final ResourceLocation DEATHPIEA_RAIGO_ICON;
   private static final int RAIGO_BASE_COOLDOWN = 1700;
   private static final int RAIGO_THUNDERSTORM_COOLDOWN = 700;
   private static final int RAIGO_BASE_CHARGE_TIME = 240;
   private static final int RAIGO_THUNDERSTORM_CHARGE_TIME = 100;
   private static final int MAMARAGAN_COOLDOWN = 1400;
   private static final int MAMARAGAN_HOLD_TIME = 1200;
   public static final RegistryObject<AbilityCore<RaigoAbility>> INSTANCE;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AltModeComponent<Mode> altModeComponent;
   private final Interval spawnLightningInterval;
   private RaigoProjectile raigoProjectile;
   private boolean isThundering;

   public RaigoAbility(AbilityCore<RaigoAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, RaigoAbility.Mode.RAIGO)).addChangeModeEvent(this::onAltModeChange);
      this.spawnLightningInterval = new Interval(10);
      this.isThundering = false;
      this.setDisplayIcon(DEFAULT_ICON);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.altModeComponent, this.projectileComponent});
      this.addUseEvent(this::onUseEvent);
      this.addEquipEvent(this::equipEvent);
   }

   public void equipEvent(LivingEntity entity, IAbility ability) {
      this.setDisplayIcon(DEFAULT_ICON);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      Result hasThunderstorm = this.canUseEvent(entity, ability);
      if (this.altModeComponent.getCurrentMode() == RaigoAbility.Mode.MAMARAGAN) {
         if (!this.continuousComponent.isContinuous() && hasThunderstorm.isFail()) {
            if (hasThunderstorm.getMessage() != null) {
               WyHelper.sendMessage(entity, hasThunderstorm.getMessage());
            }

            return;
         }

         this.spawnLightningInterval.restartIntervalToZero();
         this.continuousComponent.triggerContinuity(entity, 1200.0F);
      } else if (this.altModeComponent.getCurrentMode() == RaigoAbility.Mode.RAIGO) {
         if (this.continuousComponent.isContinuous()) {
            return;
         }

         if (!this.chargeComponent.isCharging()) {
            this.isThundering = hasThunderstorm.isSuccess();
            this.spawnLightningInterval.restartIntervalToZero();
            this.chargeComponent.startCharging(entity, this.isThundering ? 240.0F : 100.0F);
         } else if ((double)this.chargeComponent.getChargeTime() >= (double)20.0F) {
            this.chargeComponent.stopCharging(entity);
         }
      }

   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         HitResult mop = WyHelper.rayTraceBlocksAndEntities(entity);
         double x = mop.m_82450_().f_82479_;
         double y = mop.m_82450_().f_82480_;
         double z = mop.m_82450_().f_82481_;
         this.raigoProjectile = new RaigoProjectile(entity.m_9236_(), entity, this);
         this.raigoProjectile.m_7678_(x, (double)192.0F, z, 0.0F, 0.0F);
         entity.m_9236_().m_7967_(this.raigoProjectile);
      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.raigoProjectile != null && this.raigoProjectile.m_6084_() && !super.canUse(entity).isFail()) {
            this.raigoProjectile.setLife(this.raigoProjectile.getMaxLife());
            this.raigoProjectile.increaseSize(0.04F);
         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.continuousComponent.startContinuity(entity, WyHelper.secondsToTicks(10.0F));
      }
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == RaigoAbility.Mode.RAIGO) {
            if (this.raigoProjectile == null || !this.raigoProjectile.m_6084_()) {
               this.continuousComponent.stopContinuity(entity);
               return;
            }

            this.raigoProjectile.setLife(this.raigoProjectile.getMaxLife());
            AbilityHelper.setDeltaMovement(this.raigoProjectile, (double)0.0F, (double)-1.75F, (double)0.0F);
         } else if (this.altModeComponent.getCurrentMode() == RaigoAbility.Mode.MAMARAGAN && this.spawnLightningInterval.canTick()) {
            if (this.canUse(entity).isFail()) {
               this.continuousComponent.stopContinuity(entity);
               return;
            }

            double posX = WyHelper.randomWithRange(-200, 200);
            double posZ = WyHelper.randomWithRange(-200, 200);
            float travelLength = 208.0F;
            ElThorProjectile bolt = new ElThorProjectile(entity.m_9236_(), entity, entity.m_20185_() + posX, entity.m_20186_(), entity.m_20189_() + posZ, 192, travelLength, 0.46F, ability);
            entity.m_9236_().m_7967_(bolt);
            entity.m_9236_().m_5594_((Player)null, bolt.m_20183_(), (SoundEvent)ModSounds.EL_THOR_SFX.get(), SoundSource.PLAYERS, 30.0F, 1.0F);
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == RaigoAbility.Mode.RAIGO) {
            if (this.raigoProjectile != null && this.raigoProjectile.m_6084_() && this.raigoProjectile.getLife() < this.raigoProjectile.getMaxLife()) {
               Direction dir = Direction.m_122366_(this.raigoProjectile.m_20185_(), this.raigoProjectile.m_20186_(), this.raigoProjectile.m_20189_());
               this.raigoProjectile.triggerBlockHitEvents(new BlockHitResult(this.raigoProjectile.m_20182_(), dir, this.raigoProjectile.m_20183_(), false));
               this.raigoProjectile.m_146870_();
            }

            this.raigoProjectile = null;
            super.cooldownComponent.startCooldown(entity, this.isThundering ? 700.0F : 1700.0F);
         } else if (this.altModeComponent.getCurrentMode() == RaigoAbility.Mode.MAMARAGAN) {
            super.cooldownComponent.startCooldown(entity, 1400.0F);
         }

      }
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == RaigoAbility.Mode.MAMARAGAN) {
         super.setDisplayName(DEATHPIEA_MAMARAGAN_NAME);
         super.setDisplayIcon(DEATHPIEA_MAMARAGAN_ICON);
      } else if (mode == RaigoAbility.Mode.RAIGO) {
         super.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         super.setDisplayIcon(DEATHPIEA_RAIGO_ICON);
      }

      return true;
   }

   private Result canUseEvent(LivingEntity entity, IAbility ability) {
      return entity.m_9236_().m_46470_() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_THUNDERSTORM);
   }

   private RaigoProjectile createProjectile(LivingEntity entity) {
      RaigoProjectile proj = new RaigoProjectile(entity.m_9236_(), entity, this);
      return proj;
   }

   static {
      DEATHPIEA_MAMARAGAN_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "mamaragan", "Mamaragan"));
      DEATHPIEA_MAMARAGAN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/mamaragan.png");
      DEATHPIEA_RAIGO_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/raigo.png");
      INSTANCE = ModRegistry.registerAbility("raigo", "Raigo", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user shapes a vast quantity of prior-made thunderclouds into one giant, dark sphere charged with inordinate electricity.", (Object)null), ImmutablePair.of("The user causes the nearby thunderclouds to shower everything below them with dozens of massive lightning bolts that create fire wherever they strike", (Object)null), ImmutablePair.of("Can only be used during a thunderstorm.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, RaigoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine((e, a) -> Component.m_237113_(name).m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[0], AbilityDescriptionLine.NEW_LINE, (e, a) -> ModI18nAbilities.DESCRIPTION_DURING_THUNDERSTORM.m_6881_(), CooldownComponent.getTooltip(700.0F), ChargeComponent.getTooltip(100.0F), (e, a) -> ModI18nAbilities.DESCRIPTION_WITHOUT_THUNDERSTORM.m_6881_(), CooldownComponent.getTooltip(1700.0F), ChargeComponent.getTooltip(240.0F), AbilityDescriptionLine.NEW_LINE).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, (e, a) -> DEATHPIEA_MAMARAGAN_NAME.m_6881_().m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN)), (e, a) -> desc[1], (e, a) -> desc[2], AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1400.0F), ContinuousComponent.getTooltip(1200.0F)).setSourceElement(SourceElement.LIGHTNING).setIcon(DEFAULT_ICON).build("mineminenomi");
      });
   }

   public static enum Mode {
      MAMARAGAN,
      RAIGO;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{MAMARAGAN, RAIGO};
      }
   }
}
