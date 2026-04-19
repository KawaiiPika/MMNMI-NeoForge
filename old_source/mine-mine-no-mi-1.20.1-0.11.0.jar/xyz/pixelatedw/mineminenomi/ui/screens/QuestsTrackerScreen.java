package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EnchantmentNames;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CAbandonQuestPacket;
import xyz.pixelatedw.mineminenomi.ui.widget.BigArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class QuestsTrackerScreen extends Screen {
   private Player player;
   private int questIndex = 0;
   private List<String> hiddenTexts = new ArrayList();
   private Quest currentQuest = null;
   private Quest[] availableQuests;
   private List<Objective> availableObjectives = new ArrayList();
   private int availableQuestsCount;

   public QuestsTrackerScreen(Player player, Quest[] availableQuests) {
      super(Component.m_237119_());
      this.player = player;
      this.questIndex = 0;
      this.availableQuests = availableQuests;
      this.availableQuestsCount = this.availableQuests.length;
      this.currentQuest = this.availableQuests[0];
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      graphics.m_280168_().m_85836_();
      float scale = 1.1F;
      graphics.m_280168_().m_252880_((float)(posX - 35), (float)(posY + 10), 0.0F);
      graphics.m_280168_().m_252880_(256.0F, 256.0F, 0.0F);
      graphics.m_280168_().m_85841_(scale * 1.5F, scale * 1.4F, 0.0F);
      graphics.m_280168_().m_252880_(-256.0F, -256.0F, 0.0F);
      graphics.m_280218_(ModResources.BLANK, 0, 0, 0, 0, 256, 256);
      graphics.m_280168_().m_252880_(-30.0F, 50.0F, 0.0F);
      graphics.m_280168_().m_252880_(256.0F, 256.0F, 0.0F);
      graphics.m_280168_().m_85841_(scale * 0.7F, scale * 0.9F, 0.0F);
      graphics.m_280168_().m_252880_(-256.0F, -256.0F, 0.0F);
      graphics.m_280168_().m_85849_();
      String currentQuestName = this.currentQuest != null ? this.currentQuest.getCore().getTitle().getString() : "None";
      double currentQuestProgress = this.currentQuest != null ? (double)(this.currentQuest.getProgress() * 100.0F) : (double)-1.0F;
      if (this.currentQuest != null) {
         graphics.m_280168_().m_85836_();
         graphics.m_280168_().m_252880_((float)(posX + 150), (float)(posY - 110), 0.0F);
         int var10000 = this.questIndex + 1;
         String pageNumber = var10000 + "/" + this.availableQuestsCount;
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)pageNumber, 0, 0, -1);
         graphics.m_280168_().m_85849_();
         graphics.m_280168_().m_85836_();
         scale = 1.4F;
         graphics.m_280168_().m_252880_((float)(posX + 100), (float)(posY + 10), 0.0F);
         graphics.m_280168_().m_252880_(256.0F, 256.0F, 0.0F);
         graphics.m_280168_().m_85841_(scale, scale, 0.0F);
         graphics.m_280168_().m_252880_(-256.0F, -256.0F, 0.0F);
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)currentQuestName, -this.f_96547_.m_92895_(currentQuestName) / 2, 0, -1);
         graphics.m_280168_().m_85849_();
         if (currentQuestProgress != (double)-1.0F) {
            int textColor = 16777215;
            if (this.currentQuest.isComplete()) {
               textColor = 65365;
            }

            String var28 = String.valueOf(ChatFormatting.BOLD);
            String progress = var28 + ModI18n.GUI_QUEST_PROGRESS.getString() + " : " + String.format("%.1f", currentQuestProgress) + "%";
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, progress, -this.f_96547_.m_92895_(progress) / 2 + 315, posY - 70, textColor);
         }

         graphics.m_280168_().m_85836_();
         int yOffset = -20;

         for(Objective obj : this.availableObjectives) {
            String objectiveName = obj.getLocalizedTitle().getString();
            double objectiveProgress = (double)(obj.getProgress() / obj.getMaxProgress() * 100.0F);
            String progress = "";
            yOffset += 20;
            int textColor = 16777215;
            if (obj.isComplete()) {
               textColor = 65280;
            }

            if (obj.isLocked()) {
               textColor = 5263440;
            } else {
               Object[] var10001 = new Object[]{objectiveProgress};
               progress = "- " + String.format("%.1f", var10001) + "%";
            }

            if (obj.isLocked() && obj.isHidden()) {
               RendererHelper.drawStringWithBorder(this.f_96547_, graphics, "• ", posX - 130, posY - 45 + yOffset, textColor);
               List<Objective> hiddenObjs = (List)this.availableObjectives.stream().filter((o) -> o.isHidden()).collect(Collectors.toList());
               if (hiddenObjs.size() > 0) {
                  RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)this.hiddenTexts.get(hiddenObjs.indexOf(obj)), posX - 123, posY - 45 + yOffset, textColor);
               }
            } else {
               String optional = obj.isOptional() ? "(Optional) " : "";
               objectiveName = "• " + optional + objectiveName + " " + progress;
               List<FormattedCharSequence> splitText = this.f_96547_.m_92923_(Component.m_237113_(objectiveName), 280);

               for(int j = 0; j < splitText.size(); ++j) {
                  RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (FormattedCharSequence)splitText.get(j), posX - 130, posY - 45 + yOffset + j * 12, textColor);
               }

               yOffset += splitText.size() * 8;
            }
         }

         if (this.availableQuestsCount <= 0) {
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)ModI18n.TRAINER_NO_OBJECTIVES_LEFT, posX - 120, posY - 20 + yOffset, -1);
         }

         graphics.m_280168_().m_85849_();
      }

      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      this.f_169369_.clear();
      this.updateSelectedQuest();
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      BigArrowButton nextButton = new BigArrowButton(posX + 285, posY + 70, 24, 100, (btn) -> {
         this.questIndex = (this.questIndex + 1) % this.availableQuests.length;
         this.updateSelectedQuest();
      });
      if (this.availableQuestsCount <= 1) {
         nextButton.f_93624_ = false;
      }

      nextButton.setFlipped();
      this.m_142416_(nextButton);
      BigArrowButton prevButton = new BigArrowButton(posX - 55, posY + 70, 24, 100, (btn) -> {
         this.questIndex = Math.floorMod(this.questIndex - 1, this.availableQuests.length);
         this.updateSelectedQuest();
      });
      if (this.availableQuestsCount <= 1) {
         prevButton.f_93624_ = false;
      }

      this.m_142416_(prevButton);
      PlankButton abandonQuestButton = new PlankButton(posX - 40, posY + 210, 80, 30, ModI18n.GUI_QUEST_ABANDON, (btn) -> {
         this.player.m_6915_();
         if (this.currentQuest != null) {
            ModNetwork.sendToServer(new CAbandonQuestPacket(this.currentQuest.getCore()));
         }

      });
      this.m_142416_(abandonQuestButton);
   }

   public void updateSelectedQuest() {
      if (this.questIndex >= 0) {
         this.currentQuest = this.availableQuests[this.questIndex];
         if (this.currentQuest != null) {
            for(Objective obj : this.currentQuest.getObjectives()) {
               if (obj.isHidden()) {
                  StringBuilder sb = new StringBuilder();
                  sb.append("§k");
                  String objTitle = obj.getLocalizedTitle().getString();

                  String obfuscatedString;
                  for(int size = 0; size < objTitle.length() * 2; size += obfuscatedString.length()) {
                     obfuscatedString = EnchantmentNames.m_98734_().m_98737_(this.f_96547_, objTitle.length() * 2).getString();
                     sb.append(obfuscatedString);
                  }

                  sb.append("§r");
                  this.hiddenTexts.add(sb.toString());
               }
            }

            this.availableObjectives = (List)this.currentQuest.getObjectives().stream().limit(5L).collect(Collectors.toList());
         }

      }
   }

   public boolean m_7043_() {
      return false;
   }
}
