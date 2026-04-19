package xyz.pixelatedw.mineminenomi.handlers;

import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.abilities.doa.DoaHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.nagi.SilentAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.events.MobEffectAfterAddedEvent;
import xyz.pixelatedw.mineminenomi.api.events.ability.AbilityUseEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.EntityCarryEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.LivingHealByEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.DorikiEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.HakiExpEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.DamageSourceHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.animation.AnimationCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.IChallengeData;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.gcd.GCDCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.handlers.ability.ComponentsHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.HisoPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.KagePassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.KnockdownHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.KukuPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MaguPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.OutOfBodyHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.ValidationHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.YomiPassiveHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.AnimationsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.AttributesHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.BountyHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.CommandReceiverHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EnchantmentsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.FactionHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.FruitWeaknessHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.GoalsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.ItemsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.KairosekiCoatingHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.ProjectilesHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.QuestsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.RevengeHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.StatsGainHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.StatsRestoreHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.SyncHandler;
import xyz.pixelatedw.mineminenomi.handlers.protection.AbilityProtectionHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.BlocksHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.ChallengesHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.StructuresHandler;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.server.SViewProtectionPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SSetHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SForceDismountEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SSetServerMaxBarsPacket;

@EventBusSubscriber(
   modid = "mineminenomi"
)
public class EntityEventHandler {
   @SubscribeEvent
   public static void onEntityUpdate(LivingEvent.LivingTickEvent event) {
      LivingEntity living = event.getEntity();
      if (living.m_6084_()) {
         if (!living.m_9236_().f_46443_) {
            boolean secondPassed = living.f_19797_ % 20 == 0;
            boolean minutePassed = living.f_19797_ % 1200 == 0;
            if (living instanceof Player) {
               Player player = (Player)living;
               StatsGainHandler.increaseLoyaltyOverTime(player);
               StatsGainHandler.givePassiveKenbunshokuExp(player);
               QuestsHandler.handleSurvivalObjective(player);
               StatsRestoreHandler.restoreHakiFromSleep(player);
               MaguPassivesHandler.lavaMovementBoost(player);
               ItemsHandler.handleEnmaCurse(player);
               if (secondPassed) {
                  StatsRestoreHandler.restoreHakiPassively(player);
               }
            }

            if (living instanceof Mob) {
               Mob mob = (Mob)living;
               CommandReceiverHandler.canMaintainCommand(mob);
            }

            if (living instanceof Animal) {
               Animal animal = (Animal)living;
               if (secondPassed) {
                  HisoPassivesHandler.lookoutMarking(animal);
               }

               if (minutePassed) {
                  HisoPassivesHandler.lookoutCleanup(animal);
               }
            }

            KagePassivesHandler.tryBurningInSun(living);
            YomiPassiveHandler.tick(living);
            EffectsHandler.removeUnneededEffects(living);
            EffectsHandler.bubblyCoralTick(living);
            GCDCapability.tickGCD(living);
            IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(living).orElse((Object)null);
            if (statsProps != null && statsProps.getInvulnerableTime() > 0) {
               statsProps.alterInvulnerableTime(-1);
            }

            CombatCapability.tick(living);
            ValidationHandler.validateAllItemsForComponentChecks(living);
            ItemsHandler.insideTrapBlock(living);
            ItemsHandler.handleImbuingTagging(living);
            RevengeHandler.checkTicks(living);
            if (secondPassed) {
               for(MobEffectInstance inst : living.m_21220_()) {
                  EffectsHandler.trySpreadProximityEffect(living, inst);
               }
            }
         } else {
            AnimationCapability.tick(living);
         }

         if (living instanceof Player) {
            Player player = (Player)living;
            ChallengeCapability.get(player).ifPresent(IChallengeData::tickInvitations);
         }

         YomiPassiveHandler.handleWaterRunning(living);
         FruitWeaknessHandler.tryApplyWaterWeakness(living);
         AttributesHandler.applyJumpForce(living);
         AbilityCapability.tick(living);
      }
   }

   @SubscribeEvent
   public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
      Player player = event.getEntity();
      if (!player.m_9236_().f_46443_) {
         AbilityCapability.get(player).ifPresent((props) -> props.getEquippedAndPassiveAbilities().forEach((abl) -> AbilityHelper.emergencyStopAbility(player, abl)));
         if (NuWorld.isChallengeDimension(player.m_9236_())) {
            ChallengesHandler.kickPlayerFromChallengeOnLogin((ServerPlayer)player, player.m_9236_());
         }
      }

   }

   @SubscribeEvent
   public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
      Player player = event.getEntity();
      if (!player.m_9236_().f_46443_ && ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         OneFruitPerWorldHandler.playerLogout(player);
         if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
            OneFruitPerWorldHandler.dropFruitsFromNearbyContainers(player);
         }
      }

   }

   @SubscribeEvent
   public static void onJoinLevelEvent(EntityJoinLevelEvent event) {
      Level level = event.getLevel();
      if (!level.f_46443_) {
         Entity var3 = event.getEntity();
         if (var3 instanceof Mob) {
            Mob mob = (Mob)var3;
            if (!AbilityProtectionHandler.shouldMobSpawnInProtectionArea(mob)) {
               event.setCanceled(true);
               return;
            }
         }

         var3 = event.getEntity();
         if (var3 instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)var3;
            AttributeHelper.updateHPAttribute(living);
            AttributeHelper.updateToughnessAttribute(living);
            EffectsHandler.removeAbilityEffectsOnJoin(living);
         }

         var3 = event.getEntity();
         if (var3 instanceof Mob) {
            Mob mob = (Mob)var3;
            GoalsHandler.parseNBTGoals(mob);
            StatsGainHandler.setSpawnStats(mob);
         }

         var3 = event.getEntity();
         if (var3 instanceof IronGolem) {
            IronGolem golem = (IronGolem)var3;
            GoalsHandler.addNewIronGolemGoals(golem);
         }

         var3 = event.getEntity();
         if (var3 instanceof AkumaNoMiEntity) {
            AkumaNoMiEntity fruitEntity = (AkumaNoMiEntity)var3;
            if ((ServerConfig.hasChunkUnloadingRealisticLogic() || ServerConfig.hasChunkUnloadingInstantLogic()) && event.loadedFromDisk()) {
               event.setCanceled(OneFruitPerWorldHandler.cleanup(fruitEntity));
            }

            if (ServerConfig.hasOneFruitPerWorldSimpleLogic() && !event.isCanceled()) {
               event.setCanceled(OneFruitPerWorldHandler.cancelDrop(fruitEntity));
            }
         }

         var3 = event.getEntity();
         if (var3 instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)var3;
            SyncHandler.joinWorldSync(player);
            ValidationHandler.forceSelectionScreen(player);
            ProgressionHandler.forceAbilityUnlockAdvancementChecks(player);
            ValidationHandler.validateFaction(player);
            if (ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
               OneFruitPerWorldHandler.playerLogin(player);
               if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
                  OneFruitPerWorldHandler.dropFruitsFromNearbyContainers(player);
               }
            }

            ModNetwork.sendTo(SViewProtectionPacket.update(player.m_284548_()), player);
         }
      }

   }

   @SubscribeEvent
   public static void onDatapackSync(OnDatapackSyncEvent event) {
      if (event.getPlayer() != null) {
         ServerPlayer player = event.getPlayer();
         boolean result = PatreonHandler.handlePatreonAccess(player);
         if (!result) {
            return;
         }

         ModNetwork.sendTo(new SSetServerMaxBarsPacket(ServerConfig.getAbilityBars()), player);
         ModNetwork.sendTo(new SSetHakiColorPacket(player.m_19879_(), ServerConfig.getHaoshokuColoringLogic()), player);
         RandomizedFruitsHandler.Common.setRandomizationSeed(player);
      }

   }

   @SubscribeEvent
   public static void onLivingDies(LivingDeathEvent event) {
      LivingEntity living = event.getEntity();
      Level level = living.m_9236_();
      DamageSource source = event.getSource();
      Entity attacker = source.m_7639_();
      if (!level.f_46443_) {
         ServerLevel serverLevel = (ServerLevel)level;
         FactionsWorldData factionWorldData = FactionsWorldData.get();
         if (!AbilityProtectionHandler.shouldDieInProtectedArea(living)) {
            event.setCanceled(true);
            return;
         }

         if (living instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer)living;
            if (NuWorld.isChallengeDimension(player.m_9236_())) {
               player.m_7292_(new MobEffectInstance((MobEffect)ModEffects.CHALLENGE_FAILED.get(), 40, 0, false, false));
               player.m_21153_(1.0F);
               event.setCanceled(true);
               return;
            }

            if (ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
               OneFruitPerWorldHandler.playerDeath(player);
            }
         }

         if (attacker instanceof Player) {
            Player player = (Player)attacker;
            BountyHandler.redeemBounty(serverLevel, player, living);
         }

         StatsGainHandler.giveStats(living, source);
         StatsGainHandler.giveBusoshokuExp(living, source);
         EffectsHandler.handcuffedDeath(living);
         QuestsHandler.handleKillObjective(living, source);
         factionWorldData.removeBounty(living.m_20148_());
      }

   }

   @SubscribeEvent
   public static void onClonePlayer(PlayerEvent.Clone event) {
      Player original = event.getOriginal();
      Player player = event.getEntity();
      event.getOriginal().reviveCaps();
      if (event.isWasDeath()) {
         boolean keepStats = !AbilityProtectionHandler.shouldLoseStatsInProtectionArea(original);
         if (NuWorld.isChallengeDimension(original.m_9236_()) || keepStats) {
            player.m_150109_().m_36006_(original.m_150109_());
            player.m_21153_(original.m_21223_());
            player.f_36078_ = original.f_36078_;
            player.f_36079_ = original.f_36079_;
            player.f_36080_ = original.f_36080_;
            player.m_36397_(original.m_36344_());
            StatsRestoreHandler.restoreFullData(original, player);
            return;
         }

         if (YomiPassiveHandler.triggerFirstDeath(original, player)) {
            return;
         }

         StatsRestoreHandler.restoreStats(original, player);
      } else {
         StatsRestoreHandler.restoreFullData(original, player);
      }

      event.getOriginal().invalidateCaps();
   }

   @SubscribeEvent
   public static void onPlayerStartsTracking(PlayerEvent.StartTracking event) {
      if (!event.getEntity().m_9236_().f_46443_) {
         Player player = event.getEntity();
         Entity var4 = event.getTarget();
         if (var4 instanceof Mob) {
            Mob mob = (Mob)var4;
            SyncHandler.trackingSync(mob, player);
         } else {
            var4 = event.getTarget();
            if (var4 instanceof Player) {
               Player target = (Player)var4;
               SyncHandler.trackingSync(target, player);
            }
         }

         Entity var6 = event.getTarget();
         if (var6 instanceof Projectile) {
            Projectile proj = (Projectile)var6;
            ProjectilesHandler.setProjectileHakiData(proj, proj.m_19749_());
         }

      }
   }

   @SubscribeEvent
   public static void onEntityFall(LivingFallEvent event) {
      LivingEntity living = event.getEntity();
      float distance = AttributesHandler.applyFallResistance(living, event.getDistance());
      event.setDistance(distance);
   }

   @SubscribeEvent
   public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
      LivingEntity living = event.getEntity();
      AttributesHandler.applyJumpHeight(living);
   }

   @SubscribeEvent
   public static void onPlayerInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
      Player player = event.getEntity();
      Entity target = event.getTarget();
      boolean flag = KairosekiCoatingHandler.applyKairosekiCoat(player, target, event.getItemStack());
      if (flag) {
         event.setCanceled(true);
      } else {
         if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity)target;
            EffectsHandler.unlockHandcuffs(player, livingTarget);
            QuestsHandler.handleEntityInteractionObjective(player, livingTarget);
         }

         if (!event.getLevel().f_46443_ && event.getTarget() instanceof ArmorStand && event.getItemStack().m_41720_() instanceof AkumaNoMiItem && ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
            event.setCanceled(true);
         }

      }
   }

   @SubscribeEvent
   public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
      Player player = event.getEntity();
      Entity var3 = event.getTarget();
      if (var3 instanceof ItemFrame itemFrame) {
         if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
            ItemStack stack = itemFrame.m_31822_();
            event.setCanceled(!stack.m_41619_() && stack.m_41720_() instanceof AkumaNoMiItem);
         }
      }

      if (event.getHand() != InteractionHand.OFF_HAND && !player.m_9236_().f_46443_) {
         Entity var4 = event.getTarget();
         if (var4 instanceof Player) {
            Player target = (Player)var4;
            MorphsHandler.Common.startRidingZoan(player, target);
         } else {
            var4 = event.getTarget();
            if (var4 instanceof LivingEntity) {
               LivingEntity var7 = (LivingEntity)var4;
            }
         }

      }
   }

   @SubscribeEvent
   public static void onEntityTargetedEvent(LivingChangeTargetEvent event) {
      LivingEntity newTarget = event.getNewTarget();
      if (newTarget != null) {
         if (OutOfBodyHandler.isOutOfBody(newTarget)) {
            event.setCanceled(true);
            return;
         }

         if (DoaHelper.isInsideDoor(newTarget)) {
            event.setCanceled(true);
            return;
         }
      }

   }

   @SubscribeEvent
   public static void onMount(EntityMountEvent event) {
      if (!event.getLevel().f_46443_) {
         Entity mounting = event.getEntityMounting();
         Entity mounted = event.getEntityBeingMounted();
         boolean isMounting = event.isMounting();
         if (mounting instanceof LivingEntity) {
            LivingEntity mountingLiving = (LivingEntity)mounting;
            AnimationsHandler.toggleMountingAnimations(mountingLiving, mounted, isMounting);
         }

         if (!isMounting && mounted instanceof Player) {
            Player mountedPlayer = (Player)mounted;
            ModNetwork.sendTo(new SForceDismountEntityPacket(mountedPlayer), mountedPlayer);
         }

         if (mounted instanceof LivingEntity) {
            LivingEntity mountedLiving = (LivingEntity)mounted;
            if (isMounting) {
               EffectsHandler.tryLinkEffectsWithPassangers(mountedLiving);
            } else {
               EffectsHandler.tryUnlinkEffectsWithPassangers(mountedLiving);
            }
         }
      }

   }

   @SubscribeEvent
   public static void onCarry(EntityCarryEvent event) {
      LivingEntity living = event.getEntity();
      if (event.isCarrying()) {
         EffectsHandler.tryLinkEffectsWithPassangers(living);
      } else {
         EffectsHandler.tryUnlinkEffectsWithPassangers(living);
      }

   }

   @SubscribeEvent
   public static void onTameEvent(AnimalTameEvent event) {
      Player player = event.getTamer();
      QuestsHandler.handleTameObjective(player);
   }

   @SubscribeEvent
   public static void onLivingAttack(LivingAttackEvent event) {
      if (!event.getEntity().m_9236_().f_46443_) {
         DamageSource damageSource = event.getSource();
         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(damageSource);
         Entity sourceEntity = damageSource.m_7639_();
         Entity directEntity = damageSource.m_7640_();
         LivingEntity target = event.getEntity();
         if (OutOfBodyHandler.isOutOfBody(target) && !damageSource.m_276093_(ModDamageTypes.OUT_OF_BODY)) {
            event.setCanceled(true);
         } else if (BlocksHandler.tryBlockSuffocationForLogia(target, damageSource)) {
            event.setCanceled(true);
         } else {
            float amount = event.getAmount();
            if (sourceEntity instanceof LivingEntity) {
               LivingEntity attackerLiving = (LivingEntity)sourceEntity;
               if (ComponentsHandler.tryTriggerHitComponents(attackerLiving, target, damageSource)) {
                  event.setCanceled(true);
                  return;
               }

               if (FactionHandler.tryBlockingFriendlyFire(attackerLiving, target, damageSource, amount)) {
                  event.setCanceled(true);
                  return;
               }
            }

            if (!AbilityProtectionHandler.shouldHurtEntityInProtectionArea(target, damageSource)) {
               event.setCanceled(true);
            } else {
               if (directEntity instanceof LivingEntity) {
                  LivingEntity attackerLiving = (LivingEntity)directEntity;
                  if (EffectsHandler.applyHandcuffs(target, attackerLiving, amount)) {
                     event.setCanceled(true);
                     return;
                  }

                  ItemsHandler.cuckShield(target, attackerLiving);
               }

               float damageTaken = ComponentsHandler.handleDamageTakenComponents(target, damageSource, amount, DamageTakenComponent.DamageState.ATTACK);
               boolean blockedDamage = damageTaken <= 0.0F;
               if (blockedDamage) {
                  event.setCanceled(true);
               } else {
                  EffectsHandler.damageHandcuffs(target, amount);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDamage(LivingDamageEvent event) {
      if (!event.getEntity().m_9236_().f_46443_) {
         LivingEntity living = event.getEntity();
         DamageSource damageSource = event.getSource();
         float amount = ComponentsHandler.handleDamageTakenComponents(living, damageSource, event.getAmount(), DamageTakenComponent.DamageState.DAMAGE);
         EffectsHandler.damageBubblyCoral(living);
         EffectsHandler.storeDrunkDamage(living, amount);
         EffectsHandler.effectsReduction(living, damageSource);
         if (!living.m_9236_().f_46443_ && living instanceof OPBossEntity) {
            OPBossEntity bossEntity = (OPBossEntity)living;
            if (bossEntity.getDamageCeiling() > 0.0F && event.getAmount() > bossEntity.getDamageCeiling()) {
               float diff = event.getAmount() - bossEntity.getDamageCeiling();
               float extra = diff / 3.0F;
               float finalAmount = event.getAmount() - diff + extra;
               event.setAmount(finalAmount);
               return;
            }
         }

         event.setAmount(amount);
      }
   }

   @SubscribeEvent
   public static void onLivingHurt(LivingHurtEvent event) {
      if (!event.getEntity().m_9236_().f_46443_) {
         LivingEntity hurtTarget = event.getEntity();
         DamageSource damageSource = event.getSource();
         boolean handleAbilityAwareDamage = false;
         boolean isDirectSource = damageSource.m_276093_(DamageTypes.f_268464_) || damageSource.m_276093_(DamageTypes.f_268566_);
         boolean isAbilitySource = damageSource.m_276093_(ModDamageTypes.ABILITY);
         boolean isProjectileSource = damageSource.m_269533_(DamageTypeTags.f_268524_);
         Entity sourceEntity = damageSource.m_7639_();
         Entity directEntity = damageSource.m_7640_();
         if (isDirectSource || isAbilitySource || isProjectileSource) {
            handleAbilityAwareDamage = true;
         }

         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(damageSource);
         if (!sourceHandler.isInitialized()) {
            IDamageSourceHandler.NuDamageValue value = DamageSourceHelper.initializeDamageValueFromSource(damageSource);
            sourceHandler.addDamage(event.getAmount(), value);
         }

         boolean blockVanillaMath = false;
         if (handleAbilityAwareDamage) {
            if (directEntity instanceof LivingEntity) {
               LivingEntity attacker = (LivingEntity)directEntity;
               EnchantmentsHandler.tryImpactDialEffect(attacker, hurtTarget);
               EnchantmentsHandler.tryFlashDialEffect(attacker, hurtTarget);
            }

            if (sourceEntity instanceof LivingEntity) {
               LivingEntity attacker = (LivingEntity)sourceEntity;
               ItemStack heldItem = attacker.m_21205_();
               isDirectSource |= sourceHandler.hasType(SourceType.FIST) && heldItem.m_41619_();
               if (sourceHandler.hasType(SourceType.INDIRECT)) {
                  isDirectSource = false;
               }

               if (isDirectSource) {
                  ComponentsHandler.triggerHitComponents(attacker, hurtTarget, damageSource, (Set)null);
               }

               blockVanillaMath = true;
            }

            if (damageSource.m_7639_() instanceof Player && FGCommand.SHOW_DEBUG_DAMAGE) {
               float var10000 = sourceHandler.getTotalBaseDamage();
               WyDebug.debug("\nBase Damage: " + var10000 + "\nAbility Damage: " + sourceHandler.getTotalAbilityDamage() + "\nGlobal Pierce: " + sourceHandler.getGlobalPiercing());
            }
         }

         float amount = AttributesHandler.getDamageAfterProtection(damageSource, hurtTarget);
         amount = ComponentsHandler.handleDamageTakenComponents(hurtTarget, damageSource, amount, DamageTakenComponent.DamageState.HURT);
         if (sourceEntity instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity)sourceEntity;
            boolean isKnocked = KnockdownHandler.tryKnockdown(attacker, hurtTarget, amount);
            if (isKnocked) {
               event.setCanceled(true);
               return;
            }

            QuestsHandler.handleHitObjective(attacker, hurtTarget, damageSource, amount);
         }

         if (hurtTarget instanceof Player) {
            Player player = (Player)hurtTarget;
            StatsGainHandler.giveOnHitKenbunshokuExp(player, damageSource, amount);
         }

         RevengeHandler.checkHits(hurtTarget, damageSource);
         if (blockVanillaMath) {
            AttributesHandler.handleHurtMath(damageSource, hurtTarget, amount);
            event.setAmount(0.0F);
         }

      }
   }

   @SubscribeEvent
   public static void onPlayerHitsEntity(AttackEntityEvent event) {
      Player player = event.getEntity();
      if (OutOfBodyHandler.isOutOfBody(player)) {
         event.setCanceled(true);
      } else if (DoaHelper.isInsideDoor(player)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onProjectileImpactEvent(ProjectileImpactEvent event) {
      Projectile projectile = event.getProjectile();
      if (event.getRayTraceResult().m_6662_() == Type.ENTITY) {
         EntityHitResult entityHit = (EntityHitResult)event.getRayTraceResult();
         Entity var4 = entityHit.m_82443_();
         if (var4 instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)var4;
            EffectsHandler.applyWetEffectFromProjectile(living, projectile);
         }
      }

   }

   @SubscribeEvent
   public static void onEntityHeal(LivingHealEvent event) {
      LivingEntity living = event.getEntity();
      float newHeal = AttributesHandler.applyExtraHeal(living, event.getAmount());
      event.setAmount(newHeal);
   }

   public static void onHealedByEvent(LivingHealByEvent event) {
      LivingEntity living = event.getEntity();
      LivingEntity healer = event.getHealer();
      float amount = event.getAmount();
      if (healer instanceof Player player) {
         QuestsHandler.handleHealObjective(player, living, amount);
      }

   }

   @SubscribeEvent
   public static void onItemUsed(LivingEntityUseItemEvent.Start event) {
      LivingEntity entity = event.getEntity();
      ItemStack itemStack = event.getItem();
      if (DoaHelper.isInsideDoor(entity)) {
         event.setCanceled(true);
      } else if (ChallengesHandler.isItemBlockedInChallenge(entity, itemStack)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onItemStoppedUsed(LivingEntityUseItemEvent.Stop event) {
      LivingEntity entity = event.getEntity();
      ItemStack itemStack = event.getItem();
      int useDuration = event.getDuration();
      if (!itemStack.m_41619_()) {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            QuestsHandler.handleUseItemObjective(player, itemStack, useDuration);
         }

         if (ItemsHelper.isBowOrGun(itemStack)) {
            boolean cancel = ComponentsHandler.triggerBowShootComponents(entity);
            if (cancel) {
               event.setCanceled(true);
               return;
            }
         }

      }
   }

   @SubscribeEvent
   public static void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
      LivingEntity entity = event.getEntity();
      ItemStack itemStack = event.getItem();
      if (!itemStack.m_41619_()) {
         EffectsHandler.damageDehydrationEffect(entity, itemStack);
         YomiPassiveHandler.milkHeal(entity, itemStack);
      }
   }

   @SubscribeEvent
   public static void onEntityItemPickup(EntityItemPickupEvent event) {
      LivingEntity entity = event.getEntity();
      ItemEntity itemEntity = event.getItem();
      ItemStack stack = itemEntity.m_32055_();
      if (OutOfBodyHandler.isOutOfBody(entity)) {
         event.setCanceled(true);
      } else if (DoaHelper.isInsideDoor(entity)) {
         event.setCanceled(true);
      } else {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (ItemsHandler.handleEmptyHandsPickup(player, itemEntity)) {
               event.setCanceled(true);
               return;
            }

            QuestsHandler.handleCollectItemObjective(player, stack, stack.m_41613_());
            if (ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
               event.setCanceled(OneFruitPerWorldHandler.cancelPickUpSimple(player, stack));
            }

            if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
               event.setCanceled(OneFruitPerWorldHandler.cancelPickUpExtended(player, stack));
            }
         }

         Item var5 = stack.m_41720_();
         if (var5 instanceof AkumaNoMiItem) {
            AkumaNoMiItem devilFruit = (AkumaNoMiItem)var5;
            OneFruitWorldData worldData = OneFruitWorldData.get();
            worldData.inventoryOneFruit(devilFruit, entity, (String)null);
         }

      }
   }

   @SubscribeEvent
   public static void onItemTossed(ItemTossEvent event) {
      Player player = event.getPlayer();
      ItemEntity itemEntity = event.getEntity();
      ItemStack stack = itemEntity.m_32055_();
      Item item = stack.m_41720_();
      if (OutOfBodyHandler.isOutOfBody(player)) {
         player.m_36356_(event.getEntity().m_32055_());
         event.setCanceled(true);
      } else if (DoaHelper.isInsideDoor(player)) {
         player.m_36356_(event.getEntity().m_32055_());
         event.setCanceled(true);
      } else {
         ItemsHandler.removeImbuingTag(stack);
         QuestsHandler.handleCollectItemObjective(player, stack, -stack.m_41613_());
      }
   }

   @SubscribeEvent
   public static void onItemExpire(ItemExpireEvent event) {
      if (ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         Item var2 = event.getEntity().m_32055_().m_41720_();
         if (var2 instanceof AkumaNoMiItem) {
            AkumaNoMiItem devilFruit = (AkumaNoMiItem)var2;
            OneFruitPerWorldHandler.expire(devilFruit);
         }
      }

   }

   @SubscribeEvent
   public static void onEquipmentUpdate(LivingEquipmentChangeEvent event) {
      if (!event.getEntity().m_9236_().f_46443_) {
         LivingEntity living = event.getEntity();
         EquipmentSlot slot = event.getSlot();
         ItemStack toStack = event.getTo();
         ProgressionHandler.checkForEquipmentUnlocks(living);
         if (living instanceof Player) {
            Player player = (Player)living;
            QuestsHandler.handleEquipmentObjective(player, slot, toStack);
         }

      }
   }

   @SubscribeEvent
   public static void onEntityRightClickItem(PlayerInteractEvent.RightClickItem event) {
      Player player = event.getEntity();
      ItemStack heldStack = event.getItemStack();
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (ServerConfig.areExtraWaterChecksEnabled() && props != null && props.hasAnyDevilFruit() && AbilityHelper.isAffectedByWater(player)) {
         event.setCanceled(true);
      } else if (OutOfBodyHandler.isOutOfBody(player)) {
         event.setCanceled(true);
      } else {
         KukuPassivesHandler.enableKukuEating(player, heldStack);
         RandomizedFruitsHandler.Common.handleClueUsage(player, heldStack);
      }
   }

   @SubscribeEvent
   public static void onEntityBreaksBlocks(BlockEvent.BreakEvent event) {
      Player player = event.getPlayer();
      if (OutOfBodyHandler.isOutOfBody(player)) {
         event.setCanceled(true);
      } else if (DoaHelper.isInsideDoor(player)) {
         event.setCanceled(true);
      } else {
         if (event.getState().m_60734_() instanceof LeavesBlock) {
            OneFruitPerWorldHandler.breakLeaves(event.getPlayer(), (Level)event.getLevel(), event.getPos(), ServerConfig.hasOneFruitPerWorldSimpleLogic());
         }

      }
   }

   @SubscribeEvent
   public static void onEntityPlaceBlocks(BlockEvent.EntityPlaceEvent event) {
      Entity var2 = event.getEntity();
      if (var2 instanceof LivingEntity living) {
         IDevilFruit var3 = (IDevilFruit)DevilFruitCapability.get(living).orElse((Object)null);
         if (ServerConfig.areExtraWaterChecksEnabled() && var3 != null && var3.hasAnyDevilFruit() && AbilityHelper.isAffectedByWater(living)) {
            event.setCanceled(true);
         } else if (OutOfBodyHandler.isOutOfBody(living)) {
            event.setCanceled(true);
         } else if (DoaHelper.isInsideDoor(living)) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
      Player player = event.getEntity();
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      float breakSpeed = event.getOriginalSpeed();
      breakSpeed = AttributesHandler.applyBreakSpeed(player, breakSpeed);
      if (props != null && props.hasAnyDevilFruit() && AbilityHelper.isAffectedByWater(player)) {
         breakSpeed /= 15.0F;
         if (ServerConfig.areExtraWaterChecksEnabled()) {
            breakSpeed = 0.0F;
         }
      }

      breakSpeed = EffectsHandler.applySwingSpeedEffects(player, breakSpeed);
      if (HakiHelper.hasImbuingActive(player)) {
         breakSpeed += 5.0F;
      }

      if (breakSpeed != event.getOriginalSpeed()) {
         event.setNewSpeed(breakSpeed);
      }

   }

   @SubscribeEvent
   public static void onEntityLeftClickBlocks(PlayerInteractEvent.LeftClickBlock event) {
      Player player = event.getEntity();
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (ServerConfig.areExtraWaterChecksEnabled() && props != null && props.hasAnyDevilFruit() && AbilityHelper.isAffectedByWater(player)) {
         event.setCanceled(true);
      } else if (OutOfBodyHandler.isOutOfBody(player)) {
         event.setCanceled(true);
      } else if (DoaHelper.isInsideDoor(player)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onEntityRightClickBlocks(PlayerInteractEvent.RightClickBlock event) {
      Player player = event.getEntity();
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (ServerConfig.areExtraWaterChecksEnabled() && props != null && props.hasAnyDevilFruit() && AbilityHelper.isAffectedByWater(player)) {
         event.setCanceled(true);
      } else if (OutOfBodyHandler.isOutOfBody(player)) {
         event.setCanceled(true);
      } else if (DoaHelper.isInsideDoor(player)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onPotionExpires(MobEffectEvent.Applicable event) {
      LivingEntity entity = event.getEntity();
      MobEffectInstance inst = event.getEffectInstance();
      boolean canApply = EffectsHandler.checkApplicableEffect(entity, inst);
      if (!canApply) {
         event.setResult(Result.DENY);
      }

   }

   @SubscribeEvent
   public static void onPotionAdded(MobEffectAfterAddedEvent event) {
      LivingEntity entity = event.getEntity();
      MobEffectInstance inst = event.getEffectInstance();
      EffectsHandler.disableAbilitiesEffect(entity, inst);
      EffectsHandler.tryLinkEffectsWithPassangers(entity);
      EffectsHandler.trySpreadProximityEffect(entity, inst);
      EffectsHandler.applyScreenShader(entity, inst);
   }

   @SubscribeEvent
   public static void onPotionExpires(MobEffectEvent.Expired event) {
      LivingEntity entity = event.getEntity();
      MobEffectInstance inst = event.getEffectInstance();
      if (inst != null) {
         if (inst.m_19544_() == ModEffects.DRUNK.get()) {
            CombatCapability.get(entity).ifPresent((props) -> {
               entity.m_6469_(ModDamageSources.getInstance().storedDamage(), props.getStoredDamage());
               props.setStoredDamage(0.0F);
            });
         }

         EffectsHandler.enableAbilitiesEffect(entity, inst);
         EffectsHandler.tryUnlinkEffectsWithPassangers(entity);
         EffectsHandler.removeScreenShader(entity, inst);
      }
   }

   @SubscribeEvent
   public static void onPotionRemoved(MobEffectEvent.Remove event) {
      LivingEntity entity = event.getEntity();
      MobEffectInstance inst = event.getEffectInstance();
      if (inst != null) {
         if (EffectsHandler.blockMilkRemoval(entity, inst)) {
            event.setCanceled(true);
         }

         EffectsHandler.enableAbilitiesEffect(entity, inst);
         EffectsHandler.tryUnlinkEffectsWithPassangers(entity);
         EffectsHandler.removeScreenShader(entity, inst);
         if (entity instanceof Player) {
            Player player = (Player)entity;
            QuestsHandler.handleCureEffectObjective(player, inst);
         }

      }
   }

   @SubscribeEvent
   public static void onHakiExpGained(HakiExpEvent.Post event) {
      Player player = event.getEntity();
      ProgressionHandler.checkForHakiUnlocks(player);
   }

   @SubscribeEvent
   public static void onDorikiGained(DorikiEvent.Post event) {
      Player player = event.getEntity();
      double doriki = event.getDoriki();
      QuestsHandler.handleReachDorikiObjective(player, doriki);
      ProgressionHandler.checkForRacialUnlocks(player);
      ProgressionHandler.checkForHakiUnlocks(player);
   }

   public static void onPostAbilityUsed(AbilityUseEvent.Pre event) {
      LivingEntity entity = event.getEntity();
      IAbility usedAbility = event.getAbility();
      if (OutOfBodyHandler.isOutOfBody(entity)) {
         if (!OutOfBodyHandler.canUseAbility(entity, usedAbility)) {
            event.setCanceled(true);
         }
      }
   }

   public static void onPostAbilityUsed(AbilityUseEvent.Post event) {
      LivingEntity entity = event.getEntity();
      IAbility usedAbility = event.getAbility();
      if (entity instanceof Player player) {
         QuestsHandler.handleUseAbilityObjective(player, usedAbility);
      }

   }

   @SubscribeEvent
   public static void onChestOpened(PlayerContainerEvent.Open event) {
      Player player = event.getEntity();
      StructuresHandler.tryLowerLoyaltyFromStealing(player, event.getContainer());
   }

   @SubscribeEvent
   public static void onContainerClose(PlayerContainerEvent.Close event) {
      Player player = event.getEntity();
      QuestsHandler.handleRemainingCollectedItems(player);
      AbstractContainerMenu var3 = event.getContainer();
      if (var3 instanceof InventoryMenu inv) {
         if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
            OneFruitPerWorldHandler.containerClose(inv, event.getEntity());
         }
      }

   }

   @SubscribeEvent
   public static void onServerMessage(ServerChatEvent event) {
      Player player = event.getPlayer();
      boolean isNagiUser = (Boolean)AbilityCapability.get(player).map((props) -> (SilentAbility)props.getEquippedAbility((AbilityCore)SilentAbility.INSTANCE.get())).map(Ability::isContinuous).orElse(false);
      if (player.m_21023_((MobEffect)ModEffects.SILENT.get()) && !isNagiUser) {
         event.setCanceled(true);
      }
   }
}
