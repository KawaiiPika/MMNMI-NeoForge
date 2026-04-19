package xyz.pixelatedw.mineminenomi.handlers.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GaugeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class CombatModeHandler {
   public static int abilityBars = 1;
   private static int colorTicks = 1000;
   public static boolean isInCombat;
   private static int iconSum = 0;
   private static int iconMode = 0;

   public static void renderCombatHotbar(AbstractClientPlayer player, IAbilityData abilityDataProps, GuiGraphics graphics, Minecraft mc, int posX, int posY, int maxBars, int offset, float partialTick) {
      Set<IAbility> gauges = abilityDataProps.getEquippedAndPassiveAbilities((ablx) -> ablx.getComponent((AbilityComponentKey)ModAbilityComponents.GAUGE.get()).isPresent());
      int ablNo = 1;
      int posX2 = posX / 2 - 110;

      for(IAbility abl : gauges) {
         boolean isPaused = (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.PAUSE_TICK.get()).map(PauseTickComponent::isPaused).orElse(false);
         if (!isPaused) {
            Optional<GaugeComponent> comp = abl.<GaugeComponent>getComponent((AbilityComponentKey)ModAbilityComponents.GAUGE.get());
            if (comp.isPresent()) {
               ((GaugeComponent)comp.get()).getRenderer().renderGauge(player, graphics, posX2 - 35 * ablNo, posY, abl);
               ++ablNo;
            }
         }
      }

      graphics.m_280168_().m_85836_();
      ForgeGui var10000 = (ForgeGui)mc.f_91065_;
      var10000.rightHeight += offset;
      var10000 = (ForgeGui)mc.f_91065_;
      var10000.leftHeight += offset;

      for(int barId = 0; barId < maxBars; ++barId) {
         if (abilityBars - (abilityDataProps.getCombatBarSet() + barId) > 0) {
            graphics.m_280168_().m_252880_(0.0F, 0.0F, -1.0F);
            int var24 = 1 + abilityDataProps.getCombatBarSet() + barId;
            String barIdStr = "" + var24;
            RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, barIdStr, posX / 2 - (mc.f_91062_.m_92895_(barIdStr) + 102), posY - 14 - barId * 23, Color.WHITE.getRGB());

            for(int slotId = 0; slotId < 8; ++slotId) {
               RenderSystem.enableBlend();
               int j = slotId + (abilityDataProps.getCombatBarSet() + barId) * 8;
               IAbility abl = null;

               try {
                  abl = abilityDataProps.getEquippedAbility(j);
               } catch (Exception var18) {
               }

               if (abl != null) {
                  renderAbilitySlot(graphics, mc, player, posX, posY, abl, barId, slotId, partialTick);
               } else {
                  graphics.m_280218_(ModResources.WIDGETS, (posX - 200 + slotId * 50) / 2, posY - 23 - barId * 23, 0, 0, 23, 23);
               }

               if (ClientConfig.showSlotKeybinds()) {
                  renderHotbarKeybind(graphics, mc.f_91062_, posX, posY, barId, slotId);
               }

               RenderSystem.disableBlend();
            }
         }
      }

      graphics.m_280168_().m_85849_();
   }

   private static void renderAbilitySlot(GuiGraphics graphics, Minecraft mc, AbstractClientPlayer player, int posX, int posY, IAbility abl, int barId, int slotId, float partialTick) {
      double maxNumber = (double)1.0F;
      double number = (double)0.0F;
      Optional<SlotDecorationComponent> slotDecoComponent = abl.<SlotDecorationComponent>getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get());
      if (slotDecoComponent.isPresent()) {
         SlotDecorationComponent slotDeco = (SlotDecorationComponent)slotDecoComponent.get();
         number = (double)slotDeco.getCurrentValue();
         maxNumber = (double)slotDeco.getMaxValue();
         double slotHeight = Mth.m_14008_((double)23.0F - number / maxNumber * (double)23.0F, (double)0.0F, Double.MAX_VALUE);
         int x = (posX - 200 + slotId * 50) / 2;
         int y = posY - 23 - barId * 23;
         TexturedRectUI rect = new TexturedRectUI(ModResources.WIDGETS);
         slotDeco.triggerPreRenderEvents(player, mc, graphics.m_280168_(), graphics.m_280091_(), rect, (float)x, (float)y, partialTick);
         rect.reset();
         rect.setColor(slotDeco.getSlotRed(), slotDeco.getSlotGreen(), slotDeco.getSlotBlue(), 1.0F);
         rect.setSize(23.0F, 23.0F);
         rect.setUV(0.0F, 0.0F);
         rect.draw(graphics.m_280168_(), (float)x, (float)y);
         rect.reset();
         rect.setSize(23.0F, (float)((int)slotHeight));
         rect.setUV(24.0F, 0.0F);
         rect.draw(graphics.m_280168_(), (float)x, (float)y);
         RendererHelper.drawIcon(abl.getIcon(player), graphics.m_280168_(), (float)(x + 4), (float)(y + 4), 1.0F, 16.0F, 16.0F, slotDeco.getIconRed(), slotDeco.getIconGreen(), slotDeco.getIconBlue(), slotDeco.getIconAlpha());
         rect.reset();
         slotDeco.triggerPostRenderEvents(player, mc, graphics.m_280168_(), graphics.m_280091_(), rect, (float)x, (float)y, partialTick);
      }

      graphics.m_280168_().m_252880_(0.0F, 0.0F, 2.0F);
      if (ClientConfig.hasTextCooldownVisuals() && number > (double)0.0F) {
         String numText;
         if (ClientConfig.isDisplayInSeconds()) {
            Object[] var10001 = new Object[]{number / (double)20.0F};
            numText = String.format("%.1f", var10001) + " ";
         } else {
            numText = (int)number + " ";
         }

         RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, numText, (posX - 172 + slotId * 50) / 2 - mc.f_91062_.m_92895_(numText) / 2, posY - 14 - barId * 23, WyHelper.hexToRGB("#FFFFFF").getRGB());
      }

   }

   private static void renderHotbarKeybind(GuiGraphics graphics, Font font, int posX, int posY, int barId, int slotId) {
      int keybindSlot = slotId + barId * 8;
      KeyMapping kb = (KeyMapping)ModKeybindings.COMBAT_BAR_KEYS.get(keybindSlot);
      StringBuilder sb = new StringBuilder();
      colorTicks = (colorTicks + 1) % 500;
      if (kb.m_90862_()) {
         sb.append(colorTicks >= 250 ? "§4" : "§c");
         sb.append("⚠");
      } else {
         if (InputHandler.isHotbarKeyConflicting(kb)) {
            sb.append(colorTicks >= 250 ? "§4" : "§c");
         }

         switch (kb.getKeyModifier()) {
            case ALT -> sb.append("a");
            case CONTROL -> sb.append("c");
            case SHIFT -> sb.append("s");
         }

         sb.append(kb.getKey().m_84875_().getString());
      }

      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_((float)((posX - 195 + slotId * 50) / 2), (float)(posY - 8 - barId * 23), 0.0F);
      graphics.m_280168_().m_85841_(0.66F, 0.66F, 0.66F);
      RendererHelper.drawStringWithBorder(font, graphics, (String)sb.toString(), 0, 0, -1);
      graphics.m_280168_().m_85849_();
   }

   public static void renderHearts(AbstractClientPlayer player, GuiGraphics graphics, Minecraft mc, int posX, int posY) {
      double maxHealth = player.m_21051_(Attributes.f_22276_).m_22135_();
      double health = (double)player.m_21223_();
      int absorptionBonus = Mth.m_14167_(player.m_6103_());
      int rgb = Color.RED.getRGB();
      int posY2 = posY - ((ForgeGui)mc.f_91065_).leftHeight;
      if (absorptionBonus > 0) {
         rgb = Color.YELLOW.getRGB();
      }

      RendererHelper.drawStringWithBorder(mc.f_91062_, graphics, "" + Math.round(health + (double)absorptionBonus), posX / 2 - 27, posY2 + 1, rgb);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

      for(int i = 10; i >= 0; --i) {
         int k = posX / 2 - 91 + i % 10 * 6;
         graphics.m_280218_(RendererHelper.GUI_ICONS_LOCATION, k, posY2, 16, 0, 9, 9);
      }

      double hpPercentage = ((double)100.0F - (maxHealth - health) / maxHealth * (double)100.0F) / (double)10.0F;
      int top = 9 * (mc.f_91073_.m_6106_().m_5466_() ? 5 : 0);
      int anim = player.f_19797_ % 25;

      for(int i = 0; i < 10; ++i) {
         int k = posX / 2 - 91 + i % 10 * 6;
         int y = posY2;
         int lastIdx = (int)Math.floor(hpPercentage);
         int width = 9;
         if (i == lastIdx) {
            width = (int)(hpPercentage * (double)10.0F % (double)10.0F);
         } else if (i > lastIdx) {
            width = 0;
         }

         if (lastIdx < 4 && i == anim) {
            y = posY2 - 2;
         }

         int u = 36;
         if (absorptionBonus > 0) {
            u = 144;
         }

         graphics.m_280218_(RendererHelper.GUI_ICONS_LOCATION, k, y, 16 + u, top, width, 9);
      }

   }

   public static void renderCombatIndicator(AbstractClientPlayer player, IEntityStats entityStatsProps, GuiGraphics graphics, int posX, int posY, int offset) {
      iconSum = 0;
      if (CombatModeHandler.isInCombat) {
         ++iconSum;
      }

      if (entityStatsProps.isRogue()) {
         iconSum += 2;
      }

      if (iconSum == 3) {
         if (iconMode == 0) {
            iconMode = iconSum % 2;
         }

         if (player.f_19797_ % 40 == 0) {
            if (iconMode == 1) {
               iconMode = 2;
            } else if (iconMode == 2) {
               iconMode = 1;
            }
         }
      } else {
         iconMode = iconSum % 3;
      }

      if (iconMode > 0) {
         boolean isInCombat = (Boolean)EntityStatsCapability.get(player).map(IEntityStats::isInCombatMode).orElse(false);
         int offsetIcon = isInCombat ? offset : 0;
         ResourceLocation var10000;
         switch (iconMode) {
            case 1 -> var10000 = ModResources.PIRATE_ICON;
            case 2 -> var10000 = ModResources.WARNING_ICON;
            default -> var10000 = null;
         }

         ResourceLocation icon = var10000;
         if (icon != null) {
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_((float)(posX / 2 - 128), (float)(posY - 170 - offsetIcon), 2.0F);
            graphics.m_280168_().m_252880_(128.0F, 128.0F, 0.0F);
            graphics.m_280168_().m_85841_(0.15F, 0.15F, 1.0F);
            graphics.m_280168_().m_252880_(-128.0F, -128.0F, 0.0F);
            RenderSystem.setShaderColor(1.0F, 0.0F, 0.0F, 1.0F);
            graphics.m_280218_(icon, 0, 0, 0, 0, 256, 256);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            graphics.m_280168_().m_85849_();
         }
      }
   }
}
