package xyz.pixelatedw.mineminenomi.handlers.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import xyz.pixelatedw.mineminenomi.api.entities.ClientBossExtraEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;

public class BossUIHandler {
   public static int renderBossHealth(AbstractClientPlayer player, GuiGraphics graphics, LerpingBossEvent bossEvent, int posX, int posY) {
      ICombatData combatData = (ICombatData)CombatCapability.get(player).orElse((Object)null);
      if (combatData == null) {
         return -1;
      } else {
         int split = 19;
         int ret = -1;
         graphics.m_280168_().m_85836_();
         RenderSystem.enableBlend();

         for(Map.Entry<UUID, ClientBossExtraEvent> entry : combatData.getExtraBossInfo().entrySet()) {
            if (bossEvent.m_18860_().equals(entry.getKey())) {
               int bars = ((ClientBossExtraEvent)entry.getValue()).getTotalBars();
               int filledBars = ((ClientBossExtraEvent)entry.getValue()).getActiveBars();

               for(int i = 0; i < bars; ++i) {
                  int posX2 = posX + 177 - i % 19 * 10;
                  int posY2 = posY + 6;
                  if (i >= 19) {
                     posY2 += 10;
                  }

                  graphics.m_280218_(RendererHelper.GUI_ICONS_LOCATION, posX2, posY2, 16, 0, 9, 9);
               }

               for(int i = 0; i < filledBars; ++i) {
                  int u = 36;
                  int posX2 = posX + 177 - i % 19 * 10;
                  int posY2 = posY + 6;
                  if (i >= 19) {
                     posY2 += 10;
                  }

                  graphics.m_280218_(RendererHelper.GUI_ICONS_LOCATION, posX2, posY2, 16 + u, 0, 9, 9);
               }

               if (bars / 19 > 0) {
                  ret = 18 * (bars / 19);
               }
            }
         }

         RenderSystem.disableBlend();
         graphics.m_280168_().m_85849_();
         return ret;
      }
   }
}
