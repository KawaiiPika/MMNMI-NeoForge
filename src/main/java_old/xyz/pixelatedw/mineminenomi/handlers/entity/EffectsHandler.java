package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.AbilityMobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.effects.IChangeSwingSpeedEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IDisableAbilitiesEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IIgnoreMilkEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ILinkedEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IScreenShaderEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByProximityEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IVanishEffect;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.carry.CarryCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.effects.HandcuffedEffect;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.items.HandcuffsItem;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;
import xyz.pixelatedw.mineminenomi.packets.server.SAddScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SRemoveScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EffectsHandler {
   public static void removeAbilityEffectsOnJoin(LivingEntity entity) {
      for(MobEffectInstance inst : entity.m_21220_()) {
         if (inst instanceof AbilityMobEffectInstance abilityInst) {
            EffectsHelper.removeAbilityEffect(entity, abilityInst);
         }
      }

   }

   public static void removeUnneededEffects(LivingEntity entity) {
      for(MobEffectInstance inst : entity.m_21220_()) {
         if (inst.m_19544_().equals(ModEffects.IN_EVENT.get()) && !NuWorld.isChallengeDimension(entity.m_9236_())) {
            entity.m_21195_((MobEffect)ModEffects.IN_EVENT.get());
         }
      }

   }

   public static boolean checkApplicableEffect(LivingEntity entity, MobEffectInstance inst) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return true;
      } else {
         for(IAbility ability : props.getEquippedAndPassiveAbilities()) {
            MobEffectComponent comp = (MobEffectComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.MOB_EFFECT.get()).orElse((Object)null);
            if (comp != null) {
               boolean canApply = comp.checkEffect(entity, inst);
               if (!canApply) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static float applySwingSpeedEffects(LivingEntity entity, float speed) {
      for(MobEffectInstance effectInstance : entity.m_21220_()) {
         MobEffect var5 = effectInstance.m_19544_();
         if (var5 instanceof IChangeSwingSpeedEffect effect) {
            float swingSpeedModifier = effect.swingSpeedModifier(effectInstance.m_19557_(), effectInstance.m_19564_());
            speed /= swingSpeedModifier;
         }
      }

      return speed;
   }

   public static void removeScreenShader(LivingEntity entity, MobEffectInstance inst) {
      if (!entity.m_9236_().f_46443_ && entity instanceof ServerPlayer player) {
         MobEffect var4 = inst.m_19544_();
         if (var4 instanceof IScreenShaderEffect effect) {
            ModNetwork.sendTo(new SRemoveScreenShaderPacket(effect.getScreenShader()), player);
         }
      }

   }

   public static void applyScreenShader(LivingEntity entity, MobEffectInstance inst) {
      if (!entity.m_9236_().f_46443_ && entity instanceof ServerPlayer player) {
         MobEffect var4 = inst.m_19544_();
         if (var4 instanceof IScreenShaderEffect effect) {
            ModNetwork.sendTo(new SAddScreenShaderPacket(effect.getScreenShader()), player);
         }
      }

   }

   public static void launchedCollision(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModEffects.LAUNCHED.get()) && (entity.f_19862_ || entity.f_19863_)) {
         if (!entity.m_9236_().m_8055_(entity.m_20183_().m_7495_()).m_60795_()) {
            entity.m_6469_(entity.m_9236_().m_269111_().m_268989_(), 10.0F);
         }

         entity.m_21195_((MobEffect)ModEffects.LAUNCHED.get());
      }

   }

   public static void effectsReduction(LivingEntity entity, DamageSource damageSource) {
      DamageSources damageSources = entity.m_9236_().m_269111_();
      boolean isLightningBolt = damageSource == damageSources.m_269548_();
      boolean isLava = damageSource == damageSources.m_269233_();
      boolean isFire = damageSource == damageSources.m_269549_() || damageSource == damageSources.m_269387_();
      if (isLightningBolt || isLava || isFire) {
         if (entity.m_21023_((MobEffect)ModEffects.FROZEN.get())) {
            MobEffectInstance effect = entity.m_21124_((MobEffect)ModEffects.FROZEN.get());
            ((IMobEffectInstanceMixin)effect).setDuration((int)((double)effect.m_19557_() * (double)0.5F));
            entity.m_20095_();
            return;
         }

         if (entity.m_21023_((MobEffect)ModEffects.FROSTBITE.get())) {
            entity.m_21195_((MobEffect)ModEffects.FROSTBITE.get());
            entity.m_20095_();
         }

         if (entity.m_21023_((MobEffect)ModEffects.CANDLE_LOCK.get())) {
            entity.m_21195_((MobEffect)ModEffects.CANDLE_LOCK.get());
            entity.m_20095_();
         }

         if (entity.m_21023_((MobEffect)ModEffects.CANDY_STUCK.get())) {
            entity.m_21195_((MobEffect)ModEffects.CANDY_STUCK.get());
            entity.m_20095_();
         }
      }

   }

   public static void storeDrunkDamage(LivingEntity entity, float amount) {
      if (entity.m_21023_((MobEffect)ModEffects.DRUNK.get())) {
         CombatCapability.get(entity).ifPresent((props) -> {
            float storedDamage = props.getStoredDamage() + amount;
            props.setStoredDamage(storedDamage);
            entity.m_5634_(amount);
         });
      }

   }

   public static boolean blockMilkRemoval(LivingEntity entity, MobEffectInstance inst) {
      if (entity.m_7655_() == null) {
         return false;
      } else {
         MobEffect effect = inst.m_19544_();
         boolean isMilkBucket = entity.m_21120_(entity.m_7655_()).m_41720_() == Items.f_42455_;
         if (isMilkBucket && effect instanceof IIgnoreMilkEffect) {
            IIgnoreMilkEffect ignoreMilkEffect = (IIgnoreMilkEffect)effect;
            if (!ignoreMilkEffect.isRemoveable()) {
               return true;
            }
         }

         return false;
      }
   }

   public static void disableAbilitiesEffect(LivingEntity entity, MobEffectInstance inst) {
      MobEffect var3 = inst.m_19544_();
      if (var3 instanceof IDisableAbilitiesEffect disableEffect) {
         Predicate<IAbility> check = disableEffect.getDisabledAbilities();
         AbilityHelper.disableAbilities(entity, inst.m_19557_(), check);
      }

   }

   public static void enableAbilitiesEffect(LivingEntity entity, MobEffectInstance inst) {
      MobEffect var3 = inst.m_19544_();
      if (var3 instanceof IDisableAbilitiesEffect disableEffect) {
         Predicate<IAbility> check = disableEffect.getDisabledAbilities();
         AbilityHelper.enableAbilities(entity, check);
      }

   }

   public static void damageDehydrationEffect(LivingEntity entity, ItemStack itemStack) {
      if (entity.m_21023_((MobEffect)ModEffects.DEHYDRATION.get())) {
         Item item = itemStack.m_41720_();
         boolean isWaterBottle = PotionUtils.m_43579_(itemStack) == Potions.f_43599_;
         if (item == Items.f_42455_ || item == ModItems.COLA.get() || item == ModItems.ULTRA_COLA.get() || isWaterBottle) {
            MobEffectInstance inst = entity.m_21124_((MobEffect)ModEffects.DEHYDRATION.get());
            if (inst.m_19564_() == 0) {
               entity.m_21195_((MobEffect)ModEffects.DEHYDRATION.get());
            } else {
               int newAmp = inst.m_19564_() - 1;
               ((IMobEffectInstanceMixin)inst).setAmplifier(newAmp);
            }
         }

      }
   }

   public static void applyWetEffectFromProjectile(LivingEntity entity, Projectile projectile) {
      if (projectile instanceof ThrownPotion potionEntity) {
         ItemStack potionStack = potionEntity.m_7846_();
         if (PotionUtils.m_43579_(potionStack) == Potions.f_43599_) {
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.WET.get(), 60, 0, false, false));
         }
      }

   }

   public static void tryLinkEffectsWithPassangers(LivingEntity entity) {
      for(MobEffectInstance effect : entity.m_21220_()) {
         boolean isTransmisibleByTouch = effect.m_19544_() instanceof ITransmisibleByTouchEffect && ((ITransmisibleByTouchEffect)effect.m_19544_()).isTransmisibleByTouch();
         boolean isLinked = effect.m_19544_() instanceof ILinkedEffect;
         if (isTransmisibleByTouch || isLinked) {
            MobEffectInstance newEffect = new MobEffectInstance(effect.m_19544_(), effect.m_19557_(), effect.m_19564_(), effect.m_19571_(), effect.m_19572_(), effect.m_19575_());

            for(Entity passanger : entity.m_20197_()) {
               if (passanger != null && passanger.m_6084_() && passanger instanceof LivingEntity) {
                  ((LivingEntity)passanger).m_7292_(newEffect);
               }
            }

            CarryCapability.get(entity).ifPresent((props) -> {
               if (props.isCarrying()) {
                  props.getCarry().m_7292_(newEffect);
               }

            });
         }
      }

   }

   public static void tryUnlinkEffectsWithPassangers(LivingEntity entity) {
      for(MobEffectInstance effect : entity.m_21220_()) {
         if (effect.m_19544_() instanceof ILinkedEffect) {
            for(Entity passanger : entity.m_20197_()) {
               if (passanger != null && passanger.m_6084_() && passanger instanceof LivingEntity) {
                  ((LivingEntity)passanger).m_21195_(effect.m_19544_());
               }
            }

            CarryCapability.get(entity).ifPresent((props) -> {
               if (props.isCarrying()) {
                  props.getCarry().m_21195_(effect.m_19544_());
               }

            });
         }
      }

   }

   public static void trySpreadProximityEffect(LivingEntity entity, MobEffectInstance inst) {
      MobEffect var3 = inst.m_19544_();
      if (var3 instanceof ITransmisibleByProximityEffect proximityEffect) {
         if (proximityEffect.isTransmisibleByProximity()) {
            float distance = proximityEffect.poximityTransmisionDistance();

            for(LivingEntity target : TargetHelper.getEntitiesInArea(entity.m_9236_(), entity, (double)distance, TargetPredicate.EVERYTHING, LivingEntity.class)) {
               if (!target.m_21023_(inst.m_19544_())) {
                  MobEffectInstance newEffect = new MobEffectInstance(inst.m_19544_(), inst.m_19557_(), inst.m_19564_(), inst.m_19571_(), inst.m_19572_(), inst.m_19575_());
                  target.m_7292_(newEffect);
               }
            }
         }
      }

   }

   public static void damageHandcuffs(LivingEntity entity, float damage) {
      for(MobEffectInstance effectInstance : entity.m_21220_()) {
         if (effectInstance.m_19544_() instanceof HandcuffedEffect) {
            int dur = effectInstance.m_19557_();
            int newDur = (int)((float)dur - damage * 30.0F);
            ((IMobEffectInstanceMixin)effectInstance).setDuration(newDur);
            WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 100, effectInstance);
            if (effectInstance.m_19557_() <= 1 && ((HandcuffedEffect)effectInstance.m_19544_()).getType() == HandcuffType.KAIROSEKI && entity instanceof Player) {
               Player player = (Player)entity;
               AbilityHelper.enableAbilities(player, (ability) -> ability.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
            }
         }

         if (effectInstance.m_19544_() instanceof CaughtInNetEffect) {
            int dur = effectInstance.m_19557_();
            int newDur = (int)((float)dur - damage * 50.0F);
            ((IMobEffectInstanceMixin)effectInstance).setDuration(newDur);
            WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 100, effectInstance);
            if (effectInstance.m_19557_() <= 1 && ((CaughtInNetEffect)effectInstance.m_19544_()).getType() == NetType.KAIROSEKI && entity instanceof Player) {
               Player player = (Player)entity;
               AbilityHelper.enableAbilities(player, (ability) -> ability.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
            }
         }
      }

   }

   public static boolean applyHandcuffs(LivingEntity target, LivingEntity attacker, float damage) {
      boolean hasHandcuffEffect = false;

      for(MobEffectInstance inst : target.m_21220_()) {
         if (inst.m_19544_() instanceof HandcuffedEffect) {
            hasHandcuffEffect = true;
            break;
         }
      }

      if (hasHandcuffEffect) {
         return false;
      } else {
         return !(damage <= 0.0F) && !(target.m_21223_() - damage > 0.0F) ? HandcuffsItem.handleHandcuffActivation(attacker.m_21206_(), target, damage) : false;
      }
   }

   public static void unlockHandcuffs(LivingEntity entity, LivingEntity target) {
      if (entity.m_21205_().m_41720_() == ModItems.KEY.get()) {
         if (target.m_21220_().stream().anyMatch((e) -> e.m_19544_() instanceof HandcuffedEffect)) {
            ItemStack keyStack = entity.m_21205_();
            keyStack.m_41774_(1);
            if (target.m_21023_((MobEffect)ModEffects.HANDCUFFED_EXPLOSIVE.get()) && !target.m_9236_().f_46443_) {
               AbilityExplosion explosion = new AbilityExplosion(target, (IAbility)null, target.m_20185_(), target.m_20186_(), target.m_20189_(), 4.0F);
               explosion.setStaticDamage(15.0F);
               explosion.ignoreFactionChecks();
               explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION5);
               explosion.m_46061_();
            }

            target.m_21195_((MobEffect)ModEffects.HANDCUFFED.get());
            target.m_21195_((MobEffect)ModEffects.HANDCUFFED_KAIROSEKI.get());
            target.m_21195_((MobEffect)ModEffects.HANDCUFFED_EXPLOSIVE.get());
         }

      }
   }

   public static void handcuffedDeath(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModEffects.HANDCUFFED.get())) {
         entity.m_19983_(new ItemStack((ItemLike)ModItems.NORMAL_HANDCUFFS.get()));
      } else if (entity.m_21023_((MobEffect)ModEffects.HANDCUFFED_KAIROSEKI.get())) {
         entity.m_19983_(new ItemStack((ItemLike)ModItems.KAIROSEKI_HANDCUFFS.get()));
      } else if (entity.m_21023_((MobEffect)ModEffects.HANDCUFFED_EXPLOSIVE.get()) && !entity.m_9236_().f_46443_) {
         AbilityExplosion explosion = new AbilityExplosion(entity, (IAbility)null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 4.0F);
         explosion.setStaticDamage(15.0F);
         explosion.ignoreFactionChecks();
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION5);
         explosion.m_46061_();
      }

   }

   public static void bubblyCoralTick(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModEffects.BUBBLY_CORAL.get())) {
         entity.m_20301_(entity.m_6062_());
      }

   }

   public static void damageBubblyCoral(LivingEntity entity) {
      if (entity.m_21023_((MobEffect)ModEffects.BUBBLY_CORAL.get())) {
         float max = 4500.0F;
         float val = (float)((MobEffectInstance)Objects.requireNonNull(entity.m_21124_((MobEffect)ModEffects.BUBBLY_CORAL.get()))).m_19557_() / max;
         if (xyz.pixelatedw.mineminenomi.api.WyHelper.random() > (double)val) {
            entity.m_21195_((MobEffect)ModEffects.BUBBLY_CORAL.get());
         }
      }

   }

   public static class Client {
      public static float getDizzyCameraRoll(LocalPlayer player) {
         return player.m_21023_((MobEffect)ModEffects.DIZZY.get()) ? (float)Math.sin((double)(player.f_19797_ % 100)) : 0.0F;
      }

      public static boolean isInvisible(LivingEntity target) {
         LocalPlayer clientPlayer = Minecraft.m_91087_().f_91074_;
         IAbilityData props = (IAbilityData)AbilityCapability.get(clientPlayer).orElse((Object)null);
         boolean isAuraHakiActive = false;
         if (props != null) {
            KenbunshokuHakiAuraAbility aura = (KenbunshokuHakiAuraAbility)props.getEquippedAbility((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get());
            isAuraHakiActive = aura != null && aura.isContinuous();
         }

         Iterator<MobEffectInstance> iter = target.m_21220_().iterator();

         while(iter.hasNext()) {
            MobEffectInstance eff = (MobEffectInstance)iter.next();
            if (eff.m_19557_() <= 0) {
               iter.remove();
            }

            if (eff.m_19544_() instanceof IVanishEffect && ((IVanishEffect)eff.m_19544_()).isVanished(target, eff.m_19557_(), eff.m_19564_()) && (!isAuraHakiActive || target == clientPlayer)) {
               return true;
            }
         }

         return false;
      }
   }
}
