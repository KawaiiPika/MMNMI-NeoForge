package xyz.pixelatedw.mineminenomi.abilities;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.IPlayerAbilities;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;

public abstract class PropelledFlightAbility extends PassiveAbility {
   private static final ResourceLocation SPECIAL_FLY_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/special_fly.png");
   protected boolean isRecovering = false;
   protected boolean hasLanded = false;
   protected double stamina = (double)100.0F;
   protected float speed = 0.0F;
   private Interval staminaInterval = new Interval(20);
   private boolean isFlying;
   private int jumpTriggerTime;
   private boolean jumpLock;

   public PropelledFlightAbility(AbilityCore<? extends PropelledFlightAbility> core) {
      super(core);
      this.setDisplayIcon(SPECIAL_FLY_ICON);
      if (super.isClientSide()) {
         GaugeComponent gaugeComponent = new GaugeComponent(this, this::renderGauge);
         this.addComponents(new AbilityComponent[]{gaugeComponent});
      }

      this.addRemoveEvent(this::onRemove);
      this.pauseTickComponent.addResumeEvent(100, this::onResume).addPauseEvent(100, this::onPause);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addDuringPassiveEvent(this::onDuringPassive);
   }

   public void tick(LivingEntity entity) {
      super.tick(entity);
   }

   private void onRemove(LivingEntity entity, PassiveAbility ability) {
      if (entity instanceof Player) {
         this.disableFlight((Player)entity);
      }

   }

   private void onResume(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         if (this.canUse(entity).isSuccess()) {
            this.enableFlight(player);
         }

      }
   }

   private void onDuringPassive(LivingEntity entity) {
      if (entity instanceof Player player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         if (props != null) {
            IPlayerAbilities abilities = (IPlayerAbilities)player.m_150110_();
            if (this.isRecovering) {
               if (!this.hasLanded && !entity.m_9236_().m_46859_(entity.m_20183_().m_6625_(5))) {
                  this.hasLanded = true;
               }

               if (this.hasLanded) {
                  this.disableFlight(player);
                  return;
               }
            }

            float health = player.m_21223_();
            float maxHealth = player.m_21233_();
            if (!this.isRecovering && this.stamina > (double)0.0F && this.isFlying && health < maxHealth && this.staminaInterval.canTick()) {
               this.alterStamina((double)((maxHealth - health) / maxHealth) * (double)-5.0F);
            }

            if (AbilityHelper.isInCreativeOrSpectator(entity)) {
               if (abilities.hasCustomFlight()) {
                  this.disableFlight(player);
               }

            } else {
               if (!entity.m_9236_().f_46443_) {
                  boolean isJumping = ((ILivingEntityExtension)player).isJumping();
                  if (!isJumping) {
                     this.jumpLock = false;
                  }

                  if (this.jumpTriggerTime > 0) {
                     --this.jumpTriggerTime;
                  }

                  if (!this.jumpLock && isJumping) {
                     if (this.jumpTriggerTime == 0) {
                        this.jumpTriggerTime = 7;
                        this.jumpLock = true;
                     } else {
                        this.isFlying = !this.isFlying;
                        abilities.setCustomFlight(this.isFlying);
                        ModNetwork.sendTo(new SUpdateAbilityNBTPacket(player, this), player);
                        player.m_20242_(this.isFlying);
                        this.jumpTriggerTime = 0;
                     }
                  }

                  if (this.isFlying) {
                     boolean isMoving = this.isMoving(entity);
                     float maxSpeed = this.getMaxSpeed(player);
                     float acceleration = this.getAcceleration(player);
                     acceleration *= this.speed > 0.0F ? 1.0F - this.speed / maxSpeed : 1.0F;
                     acceleration = isMoving ? acceleration : -maxSpeed / 20.0F;
                     this.speed = Mth.m_14036_(this.speed + acceleration, acceleration > 0.0F ? maxSpeed / 5.0F : 0.0F, maxSpeed);
                     this.speed = this.getSpeed(player);
                     InputHandler.Common.handlePropelledFlyingLogic(player, this.speed / 2.0F, 0.91F);
                     double maxDifference = (double)this.getHeightDifference(player);
                     if (maxDifference != (double)0.0F && !ServerConfig.hasYRestrictionRemoved()) {
                        boolean canGoHigher = AbilityHelper.flyingAtMaxHeight(player, maxDifference);
                        AbilityHelper.vanillaFlightThreshold(player, canGoHigher ? 256 : (int)player.m_20186_() - 1);
                     }
                  }

                  player.f_19789_ = 0.0F;
               }

            }
         }
      }
   }

   private void onPause(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         this.disableFlight(player);
      }
   }

   public boolean canRenderGauge(Player entity) {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   private void renderGauge(Player player, GuiGraphics graphics, int posX, int posY, IAbility ability) {
      if (this.canRenderGauge(player)) {
         Minecraft mc = Minecraft.m_91087_();
         RenderSystem.setShaderTexture(0, ModResources.WIDGETS);
         RendererHelper.drawIcon(SPECIAL_FLY_ICON, graphics.m_280168_(), (float)posX, (float)(posY - 38), 0.0F, 32.0F, 32.0F);
         DecimalFormat staminaFormat = new DecimalFormat("#0.0");
         String strStamina = staminaFormat.format(this.stamina);
         RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, strStamina, posX + 15 - mc.f_91062_.m_92895_(strStamina) / 2, posY - 25, Color.WHITE.getRGB());
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("isRecovering", this.isRecovering);
      nbt.m_128379_("hasLanded", this.hasLanded);
      nbt.m_128347_("stamina", this.stamina);
      nbt.m_128379_("isFlying", this.isFlying);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.isRecovering = nbt.m_128471_("isRecovering");
      this.hasLanded = nbt.m_128471_("hasLanded");
      this.stamina = nbt.m_128459_("stamina");
      this.isFlying = nbt.m_128471_("isFlying");
   }

   public double getStamina() {
      return this.stamina;
   }

   public void alterStamina(double stamina) {
      this.stamina = Mth.m_14008_(this.stamina + stamina, (double)0.0F, (double)100.0F);
   }

   public boolean isMoving(LivingEntity entity) {
      boolean isMovingAboveGround = (entity.f_20902_ != 0.0F || entity.f_20900_ != 0.0F) && !entity.f_19863_ && !entity.f_19862_;
      if (!(entity instanceof Player)) {
         return isMovingAboveGround;
      } else {
         return this.isFlying && isMovingAboveGround;
      }
   }

   public float getSpeed(LivingEntity entity) {
      return this.speed;
   }

   public abstract float getMaxSpeed(LivingEntity var1);

   protected abstract float getAcceleration(LivingEntity var1);

   protected abstract int getHeightDifference(LivingEntity var1);

   protected Vec3 getMovementVector(LivingEntity entity) {
      Vec3 lookVector = entity.m_20154_();
      double x = lookVector.f_82479_ * (double)this.speed * (double)entity.f_20902_;
      double y = lookVector.f_82480_ * (double)this.speed * (double)entity.f_20902_ + Math.cos((double)entity.f_19797_ / (double)4.0F) / (double)15.0F;
      double z = lookVector.f_82481_ * (double)this.speed * (double)entity.f_20902_;
      return new Vec3(x, y, z);
   }

   public void enableFlight(Player player) {
      IPlayerAbilities abilities = (IPlayerAbilities)player.m_150110_();
      this.isFlying = true;
      abilities.setCustomFlight(true);
      ModNetwork.sendTo(new SUpdateAbilityNBTPacket(player, this), player);
      player.m_20242_(true);
   }

   public void disableFlight(Player player) {
      IPlayerAbilities abilities = (IPlayerAbilities)player.m_150110_();
      this.isFlying = false;
      abilities.setCustomFlight(false);
      ModNetwork.sendTo(new SUpdateAbilityNBTPacket(player, this), player);
      player.m_20242_(false);
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null) {
         props.setFlyingSpeed(Vec3.f_82478_);
      }
   }
}
