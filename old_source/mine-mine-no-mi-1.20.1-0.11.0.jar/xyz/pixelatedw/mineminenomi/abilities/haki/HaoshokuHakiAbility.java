package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.api.events.ability.HakiKnockoutEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.haki.HaoshokuHakiParticleEffect;

public class HaoshokuHakiAbility extends Ability {
   private static final ResourceLocation[] ICONS = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/haoshoku_haki_0.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/haoshoku_haki_1.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/haoshoku_haki_2.png")};
   public static final RegistryObject<AbilityCore<HaoshokuHakiAbility>> INSTANCE = ModRegistry.registerAbility("haoshoku_haki", "Haoshoku Haki", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A burst of the unique Conqueror's haki, that knocks out enemies that are weaker than the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, HaoshokuHakiAbility::new)).setIcon(ICONS[0]).addDescriptionLine(desc).setUnlockCheck(HaoshokuHakiAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private HaoshokuHakiParticleEffect.Details particleDetails = new HaoshokuHakiParticleEffect.Details();
   private LightningDischargeEntity discharge;
   private Color color = new Color(16711680);
   private int burstSize = 0;
   private int radius = 0;
   private int unconsciousTimer = 0;
   private int haoMastery = 0;
   private final Interval effectInterval = new Interval(20);

   public HaoshokuHakiAbility(AbilityCore<HaoshokuHakiAbility> core) {
      super(core);
      this.setOGCD();
      this.addComponents(new AbilityComponent[]{this.continuousComponent});
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.HAKI_RELEASE_SFX.get(), SoundSource.PLAYERS, 4.0F, 1.0F);
      float totalHakiExp = (Float)HakiCapability.get(entity).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
      float haoLevel = totalHakiExp / 100.0F;
      if (haoLevel <= 1.0F) {
         this.radius = 10;
         this.unconsciousTimer = 20;
         this.burstSize = 3;
         this.haoMastery = 0;
      } else if (haoLevel > 1.0F && haoLevel <= 1.75F) {
         this.radius = 25;
         this.unconsciousTimer = 40;
         this.burstSize = 5;
         this.haoMastery = 1;
      } else if (haoLevel > 1.75F) {
         this.radius = 40;
         this.unconsciousTimer = 50;
         this.burstSize = 10;
         this.haoMastery = 2;
      }

      this.color = new Color(HakiHelper.getHaoshokuColour(entity));
      this.discharge = new LightningDischargeEntity(entity, entity.m_20185_(), entity.m_20186_() + (double)1.5F, entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
      this.discharge.setAliveTicks(-1);
      this.discharge.setUpdateRate(8);
      this.discharge.setLightningLength((float)(this.radius * 2));
      this.discharge.setColor(new Color(0, 0, 0, 100));
      this.discharge.setOutlineColor(this.color);
      this.discharge.setDetails(16);
      int density = this.haoMastery == 2 ? 32 : 16;
      this.discharge.setDensity(density);
      this.discharge.setSize(1.0F);
      this.discharge.setSkipSegments(1);
      if (this.haoMastery == 0) {
         this.discharge.setSplit();
      }

      entity.m_9236_().m_7967_(this.discharge);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         int continueTime = (int)this.continuousComponent.getContinueTime();
         IHakiData hakiProps = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
         if (hakiProps != null) {
            if (this.effectInterval.canTick()) {
               for(MobEffectInstance instance : entity.m_21220_()) {
                  MobEffect var8 = instance.m_19544_();
                  if (var8 instanceof IWeakenedByHaoshokuEffect) {
                     IWeakenedByHaoshokuEffect weakEffect = (IWeakenedByHaoshokuEffect)var8;
                     ((IMobEffectInstanceMixin)instance).setDuration(instance.m_19557_() - weakEffect.haoshokuReductionTicks());
                     WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 100, instance);
                  }
               }
            }

            if (continueTime % 5 == 0) {
               this.particleDetails.setSize(this.burstSize);
               this.particleDetails.setColor(this.color.getRGB());
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HAOSHOKU_HAKI.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), this.particleDetails);
               this.discharge.m_6034_(entity.m_20185_(), entity.m_20186_() + (double)1.0F, entity.m_20189_());
            }

            if (continueTime % 10 == 0) {
               entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.HAKI_RELEASE_SFX.get(), SoundSource.PLAYERS, 3.0F, 0.5F + entity.m_217043_().m_188501_());
            }

            if (continueTime % 20 == 0) {
               hakiProps.alterHakiOveruse(200);
               this.knockoutNearbyEnemies(entity);
               boolean isOnMaxOveruse = !HakiHelper.canEnableHaki(entity);
               if (isOnMaxOveruse) {
                  this.continuousComponent.stopContinuity(entity);
                  return;
               }
            }

            if (continueTime % 100 == 0) {
               if (this.radius == 25) {
                  this.destroyNearbyBlocks(entity, 1.0F, 3);
               } else if (this.radius > 25) {
                  this.destroyNearbyBlocks(entity, 2.0F, 5);
               }
            }

         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      float cooldown = this.continuousComponent.getContinueTime() / 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
      if (this.discharge != null) {
         this.discharge.setAliveTicks(30);
      }

   }

   private void knockoutNearbyEnemies(LivingEntity user) {
      List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(user.m_20182_(), user.m_9236_(), (double)this.radius, ModEntityPredicates.getEnemyFactions(user));
      float totalHakiExp = (Float)HakiCapability.get(user).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
      float haoLevel = totalHakiExp / 100.0F;

      for(LivingEntity target : targets) {
         float totalHaki = (Float)HakiCapability.get(target).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
         double targetHaoLevel = (double)(totalHaki / 100.0F);
         if (!(targetHaoLevel + (double)0.5F >= (double)haoLevel)) {
            if (this.haoMastery > 0) {
               HakiKnockoutEvent.Post postEvent = null;
               if (target.m_21023_((MobEffect)ModEffects.UNCONSCIOUS.get())) {
                  MobEffectInstance instance1 = target.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
                  int timer = instance1.m_19557_() + this.unconsciousTimer;
                  HakiKnockoutEvent event = new HakiKnockoutEvent.Pre(target, user, true, timer);
                  if (MinecraftForge.EVENT_BUS.post(event)) {
                     return;
                  }

                  EffectsHelper.updateEffectDuration(target, instance1, timer);
                  postEvent = new HakiKnockoutEvent.Post(target, user, true, timer);
               } else {
                  MobEffectInstance instance = new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), this.unconsciousTimer, 1, false, false);
                  HakiKnockoutEvent event = new HakiKnockoutEvent.Pre(target, user, false, this.unconsciousTimer);
                  if (MinecraftForge.EVENT_BUS.post(event)) {
                     return;
                  }

                  target.m_7292_(instance);
                  postEvent = new HakiKnockoutEvent.Post(target, user, false, this.unconsciousTimer);
               }

               MinecraftForge.EVENT_BUS.post(postEvent);
            } else {
               target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
            }
         }
      }

   }

   private void destroyNearbyBlocks(LivingEntity entity, float density, int range) {
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      float x0 = (float)entity.m_20185_();
      float z0 = (float)entity.m_20189_();
      int i = 0;

      for(int rho = (int)((float)(-range) * density); (double)rho <= Math.PI * (double)density; ++rho) {
         for(int phi = 0; (float)phi <= (float)range * density; ++phi) {
            ++i;
            if (i % 5 == 0) {
               float phi1 = (float)phi / density;
               float rho1 = (float)rho / density;

               for(int r = 0; r <= 20; r += 5) {
                  float x = (float)((double)x0 + (double)r * Math.cos((double)phi1) * Math.cos((double)rho1) + WyHelper.randomWithRange(-3, 3));
                  float y = (float)(entity.m_20186_() - (double)2.0F + (double)r * Math.sin((double)phi1));
                  float z = (float)((double)z0 + (double)r * Math.cos((double)phi1) * Math.sin((double)rho1) + WyHelper.randomWithRange(-3, 3));
                  mutpos.m_122169_((double)x, (double)y, (double)z);
                  BlockState state = entity.m_9236_().m_8055_(mutpos);
                  if (!state.m_60795_() && !mutpos.m_123314_(entity.m_20183_(), (double)3.0F) && NuWorld.setBlockState((Entity)entity, mutpos, Blocks.f_50016_.m_49966_(), 3, DefaultProtectionRules.CORE_FOLIAGE_ORE)) {
                     entity.m_9236_().m_46796_(2001, mutpos, Block.m_49956_(state));
                  }
               }
            }
         }
      }

   }

   private static boolean canUnlock(LivingEntity user) {
      if (ServerConfig.getHaoshokuUnlockLogic() == ServerConfig.HaoshokuUnlockLogic.NONE) {
         return false;
      } else {
         if (user instanceof Player) {
            Player player = (Player)user;
            if (ServerConfig.isHaoshokuUnlockLogicChanceBased()) {
               boolean flag = HakiHelper.isHaoshokuBorn(player);
               if (flag) {
                  return true;
               }
            }

            if (ServerConfig.isHaoshokuUnlockLogicExpBased()) {
               IHakiData props = (IHakiData)HakiCapability.get(user).orElse((Object)null);
               if (props == null) {
                  return false;
               }

               boolean hasExp = (double)props.getTotalHakiExp() >= WyHelper.percentage((double)80.0F, (double)props.getMaxHakiExp());
               boolean hasHakiAbilities = false;
               int i = 0;

               for(RegistryObject<? extends AbilityCore<?>> core : ModAbilities.HAKI_ABILITIES) {
                  if (i >= 2) {
                     hasHakiAbilities = true;
                     break;
                  }

                  if (core.get() != INSTANCE.get() && AbilityCapability.hasUnlockedAbility(user, (AbilityCore)core.get())) {
                     ++i;
                  }
               }

               return hasExp && hasHakiAbilities;
            }
         }

         return false;
      }
   }

   public ResourceLocation getIcon(LivingEntity player) {
      float totalHakiExp = (Float)HakiCapability.get(player).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
      float haoLevel = totalHakiExp / 100.0F;
      int level = 0;
      if (haoLevel > 1.0F && haoLevel <= 1.75F) {
         level = 1;
      } else if (haoLevel > 1.75F) {
         level = 2;
      }

      return ICONS[level];
   }
}
