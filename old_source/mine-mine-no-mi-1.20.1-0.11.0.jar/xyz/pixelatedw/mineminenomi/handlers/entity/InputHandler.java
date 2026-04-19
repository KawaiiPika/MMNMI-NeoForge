package xyz.pixelatedw.mineminenomi.handlers.entity;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import xyz.pixelatedw.mineminenomi.api.ILivingEntityExtension;
import xyz.pixelatedw.mineminenomi.api.IPlayerAbilities;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.client.CRequestSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CUseAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.component.CChangeAbilityAltModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSwitchVehicleModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSyncPlayerInputPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilitiesListScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenPlayerScreenPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilityTreeScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.ChallengesScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.CrewDetailsScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.PlayerStatsScreen;
import xyz.pixelatedw.mineminenomi.ui.screens.QuestsTrackerScreen;

public class InputHandler {
   public static void handleNewPlayerInput(LocalPlayer player) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (props != null && (props.getLeftImpulse() != player.f_20900_ || props.getForwardImpulse() != player.f_20902_ || props.isJumping() != player.f_108618_.f_108572_)) {
         props.setLeftImpulse(player.f_20900_);
         props.setForwardImpulse(player.f_20902_);
         props.setJumping(player.f_108618_.f_108572_);
         ModNetwork.sendToServer(new CSyncPlayerInputPacket(player));
      }

   }

   public static boolean canBlockHandSwingInput(LocalPlayer player, InteractionHand hand) {
      if (hand != InteractionHand.MAIN_HAND) {
         return false;
      } else {
         Stream var10000 = player.m_21220_().stream().map(MobEffectInstance::m_19544_);
         Objects.requireNonNull(IBindHandsEffect.class);
         long count = var10000.filter(IBindHandsEffect.class::isInstance).filter((eff) -> ((IBindHandsEffect)eff).isBlockingSwings()).count();
         return count > 0L;
      }
   }

   public static void resetVanillaKeybinds() {
      Minecraft mc = Minecraft.m_91087_();

      for(int i = 0; i < mc.f_91066_.f_92056_.length; ++i) {
         KeyMapping kb = mc.f_91066_.f_92056_[i];
         if (ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[i] != -1) {
            kb.m_90848_(InputConstants.m_84827_(ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[i], 0));
         }
      }

   }

   public static void resetCachedKeybinds() {
      CombatModeHandler.abilityBars = ServerConfig.getAbilityBars();

      for(int i = 0; i < ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS.length; ++i) {
         ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[i] = -1;
      }

   }

   public static void checkKeybindings(AbstractClientPlayer player, InputConstants.Key input, int action, int key, int type) {
      Minecraft mc = Minecraft.m_91087_();
      if (WyDebug.isDebug() && ModKeybindings.TEST.isActiveAndMatches(input)) {
      }

      if (ModKeybindings.VEHICLE_ALT_MODE.isActiveAndMatches(input)) {
         ModNetwork.sendToServer(new CSwitchVehicleModePacket());
      }

      Screen screen = mc.f_91080_;
      boolean isPlayerScreen = screen != null && screen instanceof PlayerStatsScreen;
      boolean isAbilitiesScreen = screen != null && screen instanceof AbilitiesListScreen;
      boolean isCrewScreen = screen != null && screen instanceof CrewDetailsScreen;
      boolean isChallengesScreen = screen != null && screen instanceof ChallengesScreen;
      boolean isQuestScreen = screen != null && screen instanceof QuestsTrackerScreen;
      boolean isAbilityTreeScreen = screen != null && screen instanceof AbilityTreeScreen;
      boolean isSecondaryScreenOpen = isAbilitiesScreen || isCrewScreen || isChallengesScreen || isQuestScreen || isAbilityTreeScreen;
      if (action == 1) {
         checkScreenActivation(mc, ModKeybindings.OPEN_ABILITIES_MENU, input, action, key, type, screen, isAbilitiesScreen, () -> new COpenAbilitiesListScreenPacket());
         checkScreenActivation(mc, ModKeybindings.OPEN_CHALLENGES_MENU, input, action, key, type, screen, isChallengesScreen, () -> new COpenChallengesScreenPacket());
         checkScreenActivation(mc, ModKeybindings.OPEN_QUESTS_MENU, input, action, key, type, screen, isQuestScreen, () -> new CRequestSyncQuestDataPacket(true));
         checkScreenActivation(mc, ModKeybindings.OPEN_CREW_MENU, input, action, key, type, screen, isCrewScreen, () -> new COpenCrewScreenPacket());
         checkScreenActivation(mc, ModKeybindings.OPEN_ABILITY_TREE_MENU, input, action, key, type, screen, isAbilityTreeScreen, () -> new COpenAbilityTreeScreenPacket());
         if (ModKeybindings.OPEN_PLAYER_MENU.isActiveAndMatches(input) && ModKeybindings.OPEN_PLAYER_MENU.m_90859_() || (screen == null || isPlayerScreen || isSecondaryScreenOpen) && type == 1 && key == ModKeybindings.OPEN_PLAYER_MENU.getKey().m_84873_()) {
            if (isPlayerScreen) {
               mc.m_91152_((Screen)null);
            } else if (isSecondaryScreenOpen) {
               ModNetwork.sendToServer(new COpenPlayerScreenPacket());
            } else if (screen == null) {
               ModNetwork.sendToServer(new COpenPlayerScreenPacket());
            }
         }
      }

      if (screen == null && action != 2) {
         IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         IEntityStats entityStatsProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (entityStatsProps == null) {
            LogManager.getLogger(InputHandler.class).warn("Entity Capability for player " + player.m_7755_().toString() + " is null!");
         } else {
            tryUpdateCombatMode(entityStatsProps, input);
            tryChangeCombatBar(entityStatsProps, abilityDataProps, input);
            if (abilityDataProps == null) {
               LogManager.getLogger(InputHandler.class).warn("Ability Capability for player " + player.m_7755_().toString() + " is null!");
            } else {
               tryUseAbilitySlot(player, abilityDataProps, entityStatsProps, input);
            }
         }
      }
   }

   private static <P> void checkScreenActivation(Minecraft mc, KeyMapping keybind, InputConstants.Key input, int action, int key, int type, Screen currentScreen, boolean isNeededScreen, Supplier<P> packet) {
      if (keybind.isActiveAndMatches(input) && keybind.m_90859_() || (currentScreen == null || isNeededScreen) && type == 1 && key == keybind.getKey().m_84873_()) {
         if (isNeededScreen) {
            mc.m_91152_((Screen)null);
         } else {
            ModNetwork.sendToServer(packet.get());
         }
      }

   }

   private static void tryChangeCombatBar(IEntityStats entityData, IAbilityData abilityDataProps, InputConstants.Key input) {
      if (entityData.isInCombatMode()) {
         int bars = ClientConfig.getAbilityBarsOnScreen();
         int clientMaxBars = ServerConfig.getAbilityBars();
         int maxBars = Math.min(clientMaxBars, ModKeybindings.serverMaxBars);
         int amount = Math.min(bars, maxBars);
         if (ModKeybindings.NEXT_COMBAT_BAR.isActiveAndMatches(input) && ModKeybindings.NEXT_COMBAT_BAR.m_90859_()) {
            if (abilityDataProps.getCombatBarSet() + amount < maxBars) {
               abilityDataProps.nextCombatBarSet(amount);
            } else {
               abilityDataProps.setCombatBarSet(0);
            }
         } else if (ModKeybindings.PREV_COMBAT_BAR.isActiveAndMatches(input) && ModKeybindings.PREV_COMBAT_BAR.m_90859_()) {
            if (abilityDataProps.getCombatBarSet() - amount >= 0) {
               abilityDataProps.prevCombatBarSet(amount);
            } else if (maxBars == amount) {
               abilityDataProps.setCombatBarSet(0);
            } else {
               int barsOnLastPage = 0;

               for(int i = maxBars; i > 0; i -= amount) {
                  barsOnLastPage = i;
               }

               abilityDataProps.setCombatBarSet(maxBars - barsOnLastPage);
            }
         } else if (ModKeybindings.LAST_COMBAT_BAR.isActiveAndMatches(input) && ModKeybindings.LAST_COMBAT_BAR.m_90859_()) {
            int lastCombatBarSet = abilityDataProps.getLastCombatBarSet();
            abilityDataProps.setCombatBarSet(lastCombatBarSet);
         }

         int i = 0;

         for(KeyMapping keybind : ModKeybindings.COMBAT_BAR_SHORTCUTS) {
            if (keybind.isActiveAndMatches(input) && keybind.m_90859_()) {
               int bar = i * bars + 1;
               bar = Mth.m_14045_(bar, 1, clientMaxBars - 1);
               amount = Math.min(bar, maxBars);
               abilityDataProps.setCombatBarSet(amount - 1);
            }

            ++i;
         }
      }

   }

   private static void tryUseAbilitySlot(AbstractClientPlayer player, IAbilityData abilityData, IEntityStats entityData, InputConstants.Key input) {
      for(int i = 0; i < ModKeybindings.COMBAT_BAR_KEYS.size(); ++i) {
         int bar = i / 8;
         if (((KeyMapping)ModKeybindings.COMBAT_BAR_KEYS.get(i)).isActiveAndMatches(input) && ((KeyMapping)ModKeybindings.COMBAT_BAR_KEYS.get(i)).m_90859_()) {
            int k = i % 8 + (abilityData.getCombatBarSet() + bar) * 8;
            IAbility abl = abilityData.getEquippedAbility(k);
            boolean isOnCooldown = false;
            if (abl != null && abl.hasComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get())) {
               isOnCooldown = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).map((comp) -> comp.isOnCooldown() && comp.getCooldown() > 10.0F).get();
            }

            if (entityData.isInCombatMode() && abl != null) {
               if (!isOnCooldown) {
                  if (ModKeybindings.ABILITY_ALT_MODE.m_90857_() && abl.hasComponent((AbilityComponentKey)ModAbilityComponents.ALT_MODE.get())) {
                     ModNetwork.sendToServer(new CChangeAbilityAltModePacket(k));
                  } else {
                     ModNetwork.sendToServer(new CUseAbilityPacket(k));
                  }
               }
            } else {
               player.m_150109_().f_35977_ = i % 8;
            }
         }
      }

   }

   private static void tryUpdateCombatMode(IEntityStats entityStatsProps, InputConstants.Key input) {
      if (ModKeybindings.COMBAT_MODE.isActiveAndMatches(input) && ModKeybindings.COMBAT_MODE.m_90859_()) {
         Minecraft mc = Minecraft.m_91087_();
         entityStatsProps.setCombatMode(!entityStatsProps.isInCombatMode());
         if (entityStatsProps.isInCombatMode()) {
            for(int i = 0; i < mc.f_91066_.f_92056_.length; ++i) {
               KeyMapping kb = mc.f_91066_.f_92056_[i];
               ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[i] = kb.getKey().m_84873_();
               kb.m_90848_(InputConstants.f_84822_);
            }

            KeyMapping.m_90854_();
         } else {
            for(int i = 0; i < mc.f_91066_.f_92056_.length; ++i) {
               KeyMapping kb = mc.f_91066_.f_92056_[i];
               if (ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[i] == -1) {
                  kb.m_90848_(InputConstants.m_84827_(kb.m_90861_().m_84873_(), 0));
               } else {
                  kb.m_90848_(InputConstants.m_84827_(ModKeybindings.PREVIOUS_INVENTORY_KEYBINDS[i], 0));
               }
            }

            KeyMapping.m_90854_();
         }

         ModNetwork.sendToServer(new CToggleCombatModePacket(entityStatsProps.isInCombatMode()));
      }

   }

   public static boolean isHotbarKeyConflicting(KeyMapping keybind) {
      for(KeyMapping kb : ModKeybindings.COMBAT_BAR_KEYS) {
         if (!kb.equals(keybind) && kb.getKey().equals(keybind.getKey()) && kb.getKeyModifier().equals(keybind.getKeyModifier())) {
            return true;
         }
      }

      return false;
   }

   public static class Common {
      public static void handlePropelledFlyingLogic(Player entity, float xzSpeed, float drag) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         if (props != null) {
            if (!((IPlayerAbilities)entity.m_150110_()).hasCustomFlight()) {
               props.setFlyingSpeed(Vec3.f_82478_);
            } else {
               entity.f_19789_ = 0.0F;
               Vec3 flyingSpeed = props.getFlyingSpeed();
               Vec3 lookVector = entity.m_20154_();
               double x = lookVector.f_82479_ * (double)xzSpeed * (double)props.getForwardImpulse();
               double y = lookVector.f_82480_ * (double)xzSpeed * (double)props.getForwardImpulse() + Math.cos((double)entity.f_19797_ / (double)4.0F) / (double)15.0F;
               double z = lookVector.f_82481_ * (double)xzSpeed * (double)props.getForwardImpulse();
               flyingSpeed = flyingSpeed.m_82520_(x, y, z).m_82490_((double)drag);
               props.setFlyingSpeed(flyingSpeed);
               AbilityHelper.setDeltaMovement(entity, flyingSpeed);
            }
         }
      }

      public static void handleFreeFlyingLogic(Player entity, float xzSpeed, float ySpeed, float drag) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
         if (props != null) {
            if (!((IPlayerAbilities)entity.m_150110_()).hasCustomFlight()) {
               props.setFlyingSpeed(Vec3.f_82478_);
            } else {
               boolean isJumping = ((ILivingEntityExtension)entity).isJumping();
               float heightSpeed = 0.0F;
               if (isJumping) {
                  heightSpeed = ySpeed;
               } else if (entity.m_6047_()) {
                  heightSpeed = -ySpeed;
               }

               if (entity.m_20184_().m_82553_() == (double)0.0F && (double)heightSpeed == (double)0.0F) {
                  props.setFlyingSpeed(Vec3.f_82478_);
               } else {
                  entity.f_19789_ = 0.0F;
                  Vec3 flyingSpeed = props.getFlyingSpeed();
                  Vec2 moveVec = new Vec2(props.getLeftImpulse(), props.getForwardImpulse());
                  float moveX = moveVec.f_82470_ * xzSpeed;
                  float moveZ = moveVec.f_82471_ * xzSpeed;
                  float moveXRot = Mth.m_14031_(entity.m_146908_() * ((float)Math.PI / 180F));
                  float moveZRot = Mth.m_14089_(entity.m_146908_() * ((float)Math.PI / 180F));
                  flyingSpeed = flyingSpeed.m_82520_((double)(moveX * moveZRot - moveZ * moveXRot), (double)heightSpeed, (double)(moveZ * moveZRot + moveX * moveXRot)).m_82490_((double)drag);
                  props.setFlyingSpeed(flyingSpeed);
                  AbilityHelper.setDeltaMovement(entity, flyingSpeed);
               }
            }
         }
      }
   }
}
