package xyz.pixelatedw.mineminenomi.handlers;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.events.entity.UpdateCombatStateEvent;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.ui.BossUIHandler;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.CraftingHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

@EventBusSubscriber(
   modid = "mineminenomi",
   value = {Dist.CLIENT}
)
public class UIEventHandler {
   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
      Minecraft mc = Minecraft.m_91087_();
      PoseStack pose = event.getGuiGraphics().m_280168_();
      AbstractClientPlayer player = mc.f_91074_;
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      IEntityStats entityStatsProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      int posX = mc.m_91268_().m_85445_();
      int posY = mc.m_91268_().m_85446_();
      if (abilityDataProps != null && entityStatsProps != null) {
         boolean shouldRenderSurvivalElements = mc.f_91072_.m_105205_() && mc.m_91288_() instanceof Player;
         int maxBars = Mth.m_14045_(ClientConfig.getAbilityBarsOnScreen(), 1, CombatModeHandler.abilityBars);
         int offset = (maxBars - 1) * 23;
         if (event.getOverlay().id().equals(VanillaGuiOverlay.EXPERIENCE_BAR.id()) && shouldRenderSurvivalElements) {
            CombatModeHandler.renderCombatIndicator(player, entityStatsProps, event.getGuiGraphics(), posX, posY, offset);
         }

         if (event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_HEALTH.id()) && shouldRenderSurvivalElements && ClientConfig.hasHeartsUI()) {
            event.setCanceled(true);
            CombatModeHandler.renderHearts(player, event.getGuiGraphics(), mc, posX, posY);
            ForgeGui var10000 = (ForgeGui)mc.f_91065_;
            var10000.leftHeight += 10;
         }

         if (event.getOverlay().id().equals(VanillaGuiOverlay.FOOD_LEVEL.id())) {
            boolean hasYomi = (Boolean)DevilFruitCapability.get(player).map(IDevilFruit::hasYomiPower).orElse(false);
            if (hasYomi) {
               event.setCanceled(true);
            }
         }

         if (entityStatsProps.isInCombatMode()) {
            if (event.getOverlay().id().equals(VanillaGuiOverlay.EXPERIENCE_BAR.id())) {
               pose.m_85836_();
               pose.m_252880_(0.0F, (float)(-offset), 0.0F);
            }

            if (event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) {
               event.setCanceled(true);
               CombatModeHandler.renderCombatHotbar(player, abilityDataProps, event.getGuiGraphics(), mc, posX, posY, maxBars, offset, event.getPartialTick());
            }
         }

         event.getGuiGraphics().m_280262_();
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
      Minecraft mc = Minecraft.m_91087_();
      PoseStack pose = event.getGuiGraphics().m_280168_();
      AbstractClientPlayer player = mc.f_91074_;
      IEntityStats entityStatsProps = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      if (entityStatsProps != null) {
         if (entityStatsProps.isInCombatMode()) {
            if (event.getOverlay().id().equals(VanillaGuiOverlay.EXPERIENCE_BAR.id())) {
               pose.m_85849_();
            }

         }
      }
   }

   @SubscribeEvent
   public static void onRenderBossEventOverlay(CustomizeGuiOverlayEvent.BossEventProgress event) {
      LocalPlayer player = Minecraft.m_91087_().f_91074_;
      GuiGraphics graphics = event.getGuiGraphics();
      LerpingBossEvent bossEvent = event.getBossEvent();
      int posX = event.getX();
      int posY = event.getY();
      int increment = BossUIHandler.renderBossHealth(player, graphics, bossEvent, posX, posY);
      if (increment >= 0) {
         event.setIncrement(increment);
      }

   }

   @SubscribeEvent
   public static void onCombatStateUpdates(UpdateCombatStateEvent event) {
      if (event.getEntity().m_9236_().f_46443_) {
         if (ClientConfig.hasCombatUpdateChatMessageEnabled()) {
            LivingEntity var2 = event.getEntity();
            if (var2 instanceof Player) {
               Player player = (Player)var2;
               player.m_5661_(event.getCurrentInCombatState() ? ModI18n.INFO_ENTERING_COMBAT : ModI18n.INFO_LEAVING_COMBAT, false);
            }
         }

         CombatModeHandler.isInCombat = event.getCurrentInCombatState();
      }

   }

   @SubscribeEvent
   public static void onScreenInit(ScreenEvent.Init.Post event) {
      Minecraft minecraft = Minecraft.m_91087_();
      Screen screen = event.getScreen();
      int posX = (screen.f_96543_ - 256) / 2;
      int posY = (screen.f_96544_ - 256) / 2;
      if (screen instanceof CraftingScreen craftingScreen) {
         Objects.requireNonNull(event);
         CraftingHandler.initNewDyeLayerWidget(minecraft, craftingScreen, event::addListener);
      }

   }

   @SubscribeEvent
   public static void mouseClickEvent(ScreenEvent.MouseButtonPressed.Pre event) {
      if (Minecraft.m_91087_().f_91074_ != null) {
         if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
            boolean flag = OneFruitPerWorldHandler.cancelMouseClick(event.getScreen());
            event.setCanceled(flag);
         }

      }
   }

   @SubscribeEvent
   public static void mouseReleasedEvent(ScreenEvent.MouseButtonReleased.Pre event) {
      if (Minecraft.m_91087_().f_91074_ != null) {
         if (ServerConfig.hasOneFruitPerWorldExtendedLogic()) {
            boolean flag = OneFruitPerWorldHandler.cancelMouseReleaseEvent(event.getScreen());
            event.setCanceled(flag);
         }

      }
   }

   @SubscribeEvent
   public static void onFOVChanged(ComputeFovModifierEvent event) {
      Minecraft minecraft = Minecraft.m_91087_();
      LocalPlayer player = minecraft.f_91074_;
      IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (props != null) {
         for(IAbility abl : props.getEquippedAbilitiesWith((AbilityComponentKey)ModAbilityComponents.CHANGE_STATS.get())) {
            if (abl != null) {
               ChangeStatsComponent comp = (ChangeStatsComponent)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CHANGE_STATS.get()).get();
               if (comp.hasModifier(player, Attributes.f_22279_)) {
                  event.setNewFovModifier(1.0F);
                  break;
               }
            }
         }

      }
   }
}
