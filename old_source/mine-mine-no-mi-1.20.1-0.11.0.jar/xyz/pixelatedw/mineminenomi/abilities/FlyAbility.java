package xyz.pixelatedw.mineminenomi.abilities;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.IPlayerAbilities;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public abstract class FlyAbility extends PassiveAbility {
   private static final Component ELEMENTAL_FLIGHT_NAME = Component.m_237115_(ModRegistry.registerAbilityName("elemental_flight", "Elemental Flight"));
   private static final ResourceLocation ELEMENTAL_FLIGHT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/special_fly.png");
   private boolean isFlying;
   private int jumpTriggerTime;
   private boolean jumpLock;
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public FlyAbility(AbilityCore<? extends FlyAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.animationComponent});
      this.setDisplayName(ELEMENTAL_FLIGHT_NAME);
      this.setDisplayIcon(ELEMENTAL_FLIGHT_ICON);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addDuringPassiveEvent(this::duringPassiveEvent);
      this.addRemoveEvent(this::onRemoveEvent);
   }

   public void tick(LivingEntity entity) {
      super.tick(entity);
   }

   public void duringPassiveEvent(LivingEntity entity) {
      if (entity instanceof Player player) {
         boolean canFly = this.canUse(entity).isSuccess();
         if (!canFly) {
            this.disableFlight(player);
         } else {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
            if (props != null) {
               IPlayerAbilities abilities = (IPlayerAbilities)player.m_150110_();
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
                        if (!this.isFlying) {
                           this.disableFlight(player);
                        }
                     }
                  }

                  if (this.isFlying) {
                     if (entity.m_20096_()) {
                        this.disableFlight(player);
                        return;
                     }

                     player.f_19789_ = 0.0F;
                  }
               } else {
                  abilities.setCustomFlight(this.isFlying);
               }

               if (this.isFlying && !entity.m_9236_().f_46443_) {
                  float speed = this.getSpeed(player);
                  float drag = this.getDrag(player);
                  InputHandler.Common.handleFreeFlyingLogic(player, speed, speed, drag);
                  double maxDifference = (double)this.getHeightDifference(player);
                  if (!player.m_9236_().f_46443_ && maxDifference != (double)0.0F && !ServerConfig.hasYRestrictionRemoved()) {
                     boolean canGoHigher = AbilityHelper.flyingAtMaxHeight(player, maxDifference);
                     AbilityHelper.vanillaFlightThreshold(player, canGoHigher ? 256 : (int)player.m_20186_() - 1);
                  }

                  boolean isNormal = ((IDevilFruit)DevilFruitCapability.get(player).get()).getCurrentMorph().isEmpty();
                  if (this.isFlying && isNormal) {
                     this.animationComponent.start(entity, ModAnimations.SPECIAL_FLY);
                     WyHelper.spawnParticleEffect((ParticleEffect)this.getParticleEffects().get(), player, player.m_20185_(), player.m_20186_(), player.m_20189_());
                  } else {
                     this.animationComponent.stop(entity);
                  }
               }

            }
         }
      }
   }

   public void onRemoveEvent(LivingEntity entity, IAbility ability) {
      if (entity instanceof Player player) {
         if (!AbilityHelper.isInCreativeOrSpectator(entity)) {
            this.disableFlight(player);
         }
      }

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
      this.animationComponent.stop(player);
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null) {
         props.setFlyingSpeed(Vec3.f_82478_);
      }
   }

   public abstract Supplier<ParticleEffect<?>> getParticleEffects();

   public abstract int getHeightDifference(Player var1);

   public abstract float getSpeed(Player var1);

   public float getDrag(Player player) {
      return 0.65F;
   }

   public Result canUse(LivingEntity entity) {
      if (AbilityHelper.isInCreativeOrSpectator(entity)) {
         return Result.fail((Component)null);
      } else {
         return entity.m_21051_((Attribute)ModAttributes.JUMP_HEIGHT.get()).m_22135_() < (double)0.0F ? Result.fail((Component)null) : super.canUse(entity);
      }
   }

   public void saveAdditional(CompoundTag nbt) {
      nbt.m_128379_("isFlying", this.isFlying);
   }

   public void loadAdditional(CompoundTag nbt) {
      this.isFlying = nbt.m_128471_("isFlying");
   }
}
