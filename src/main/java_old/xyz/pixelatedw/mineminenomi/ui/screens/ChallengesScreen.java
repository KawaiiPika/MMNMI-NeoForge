package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInvitation;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.ui.IEventReceiverScreen;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CDisbandChallengeGroupPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CFetchPossibleInvitesPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CStartChallengePacket;
import xyz.pixelatedw.mineminenomi.ui.events.ChallengesScreenEvent;
import xyz.pixelatedw.mineminenomi.ui.panel.ChallengeGroupSelectorPanel;
import xyz.pixelatedw.mineminenomi.ui.panel.ChallengeInvitesPanel;
import xyz.pixelatedw.mineminenomi.ui.panel.ChallengesListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widget.ArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widget.GroupMemberButton;
import xyz.pixelatedw.mineminenomi.ui.widget.NewCheckboxButton;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class ChallengesScreen extends Screen implements IEventReceiverScreen<ChallengesScreenEvent> {
   private static final float MAX_ANIMATION_TICKS = 20.0F;
   private final LocalPlayer player;
   private final List<Challenge> challenges;
   private final List<ChallengeInvitation> invites;
   private final Map<String, List<Challenge>> challengesMap;
   private final List<String> categories = new ArrayList();
   private List<Challenge> displayedChallenges;
   @Nullable
   private Challenge selectedChallenge;
   private String selectedChallengeStars;
   private String selectedCategory;
   private int selectedCategoryId;
   private Component selectedCategoryLocalizedName;
   @Nullable
   private ResourceLocation selectedCategoryIcon;
   @Nullable
   private LivingEntity[] targets = new LivingEntity[4];
   private int targetsCount;
   private LivingEntity[] group = new LivingEntity[3];
   private GroupMemberButton[] groupMemberButtons = new GroupMemberButton[3];
   private GroupMemberButton groupOwnerButton;
   private ChallengesListScrollPanel challengesListPanel;
   private ChallengeGroupSelectorPanel challengesGroupSelectorPanel;
   private ChallengeInvitesPanel challengeInvitesPanel;
   private LivingEntity dummyBust;
   private int selectedMemberSlot;
   private NewCheckboxButton freeCheckbox;
   private boolean startedChallenge = false;
   private int setupStep;
   private PlankButton startChallengeButton;
   private ArrowButton nextStepButton;
   private ArrowButton prevStepButton;
   private float renderTick;
   private boolean runInfoPanelAnimation;
   private boolean runCategoryChangeAnimation;
   private float infoPanelAnimationTick = 0.0F;
   private float categoryChangeAnimationTick = 0.0F;

   public ChallengesScreen(List<Challenge> challenges, List<ChallengeInvitation> invites) {
      super(Component.m_237119_());
      this.player = Minecraft.m_91087_().f_91074_;
      this.challenges = challenges;
      this.invites = invites;
      this.challenges.sort((o1, o2) -> o2.getCore().getOrder() - o1.getCore().getOrder());
      this.challengesMap = (Map)challenges.stream().collect(Collectors.groupingBy(Challenge::getCategory));
      this.categories.add("");
      this.categories.addAll(this.challengesMap.keySet());
      this.selectedCategoryId = 0;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      if (this.runCategoryChangeAnimation && this.categoryChangeAnimationTick < 20.0F) {
         this.categoryChangeAnimationTick += 2.2F * this.f_96541_.m_91297_();
         this.categoryChangeAnimationTick = Math.min(this.categoryChangeAnimationTick, 20.0F);
      }

      if (this.runInfoPanelAnimation && this.infoPanelAnimationTick < 20.0F) {
         this.infoPanelAnimationTick += 2.0F * this.f_96541_.m_91297_();
         this.infoPanelAnimationTick = Math.min(this.infoPanelAnimationTick, 20.0F);
      }

      float infoPanelAnimationOffset = EasingFunctionHelper.easeOutBack(this.infoPanelAnimationTick / 20.0F);
      float categoryChangeAnimationOffset = EasingFunctionHelper.easeOutCubic(this.categoryChangeAnimationTick / 20.0F);
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_((float)(posX - 210), (float)(posY - 122) - categoryChangeAnimationOffset * 4.0F, 0.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 0.0F);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.selectedCategoryLocalizedName, 40, 15, -1);
      if (this.selectedCategoryIcon != null) {
         graphics.m_280168_().m_85841_(0.15F, 0.15F, 0.0F);
         graphics.m_280218_(this.selectedCategoryIcon, 0, 0, 0, 0, 256, 256);
      }

      graphics.m_280168_().m_252880_(-128.0F, -128.0F, 0.0F);
      graphics.m_280168_().m_85849_();
      posY += 25;
      if (this.selectedChallenge != null && infoPanelAnimationOffset > 0.0F) {
         infoPanelAnimationOffset *= 320.0F;
         graphics.m_280168_().m_85836_();
         graphics.m_280168_().m_252880_((float)(posX + 115) - infoPanelAnimationOffset, (float)(posY - 246), 0.0F);
         graphics.m_280168_().m_252880_(256.0F, 256.0F, 0.0F);
         graphics.m_280168_().m_85841_(1.25F, 1.25F, 0.0F);
         graphics.m_280218_(ModResources.BOARD, 0, 0, 0, 0, 256, 256);
         graphics.m_280218_(ModResources.BLANK_NEW, 0, 0, 0, 0, 256, 256);
         graphics.m_280168_().m_252880_(-256.0F, -256.0F, 0.0F);
         graphics.m_280168_().m_85849_();
         int posX2 = posX + 450 - (int)infoPanelAnimationOffset;
         if (this.setupStep == 0) {
            graphics.m_280168_().m_85836_();
            if (this.targetsCount == 1) {
               if (this.targets[0] != null) {
                  RendererHelper.drawLivingBust(this.targets[0], graphics, posX2 + 5, posY + 200, 40, -30, 10, false);
               }
            } else if (this.targetsCount == 2) {
               if (this.targets[0] != null) {
                  RendererHelper.drawLivingBust(this.targets[0], graphics, posX2 - 25, posY + 180, 35, -30, 10, false);
               }

               if (this.targets[1] != null) {
                  RendererHelper.drawLivingBust(this.targets[1], graphics, posX2 + 25, posY + 180, 35, -30, 10, false);
               }
            } else {
               if (this.targets[0] != null) {
                  RendererHelper.drawLivingBust(this.targets[0], graphics, posX2 - 25, posY + 150, 30, -30, 10, false);
               }

               if (this.targets[1] != null) {
                  RendererHelper.drawLivingBust(this.targets[1], graphics, posX2 + 25, posY + 150, 30, -30, 10, false);
               }

               if (this.targets[2] != null) {
                  RendererHelper.drawLivingBust(this.targets[2], graphics, posX2 - 25, posY + 200, 30, -30, 10, false);
               }

               if (this.targets[3] != null) {
                  RendererHelper.drawLivingBust(this.targets[3], graphics, posX2 + 25, posY + 200, 30, -30, 10, false);
               }
            }

            graphics.m_280168_().m_85849_();
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)this.selectedChallenge.getCore().getLocalizedTitle().getString(), posX2 - 30, posY + 35, -1);
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)this.selectedChallenge.getCore().getLocalizedObjective(), posX2 - 20, posY + 50, -1);
            String timeLimit = WyHelper.formatTimeMMSS((long)(this.selectedChallenge.getCore().getTimeLimit() * 60));
            String var10000 = ModI18n.GUI_TIME_LIMIT.getString();
            String timeLimitStr = var10000 + ": " + timeLimit;
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)timeLimitStr, posX2 + 60, posY + 90, -1);
            String pbTime = WyHelper.formatTimeMMSS((long)this.selectedChallenge.getBestTimeTick());
            var10000 = ModI18n.GUI_PERSONAL_BEST.getString();
            String pbTimeStr = var10000 + ": " + pbTime;
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)pbTimeStr, posX2 + 60, posY + 105, -1);
            var10000 = ModI18n.GUI_DIFFICULTY.getString();
            String difficultyStr = var10000 + ": " + this.selectedChallengeStars;
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)difficultyStr, posX2 + 60, posY + 120, -1);
            if (this.selectedChallenge.getCore().getDifficulty() != ChallengeDifficulty.STANDARD) {
               RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)ModI18n.GUI_RESTRICTIONS, posX2 + 60, posY + 135, -1);
               RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)"  - §cNo Food§r", posX2 + 60, posY + 145, -1);
               RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)"  - §cNo Potions§r", posX2 + 60, posY + 155, -1);
            }
         } else if (this.setupStep == 1) {
            this.groupOwnerButton.m_252865_(posX2 - 30);
            this.groupOwnerButton.m_253211_(posY + 40);
            this.groupMemberButtons[0].m_252865_(posX2 + 40);
            this.groupMemberButtons[0].m_253211_(posY + 40);
            this.groupMemberButtons[1].m_252865_(posX2 - 30);
            this.groupMemberButtons[1].m_253211_(posY + 95);
            this.groupMemberButtons[2].m_252865_(posX2 + 40);
            this.groupMemberButtons[2].m_253211_(posY + 95);

            for(int i = 0; i < this.groupMemberButtons.length; ++i) {
               if (this.groupMemberButtons[i] != null) {
                  this.groupMemberButtons[i].m_88315_(graphics, mouseX, mouseY, partialTicks);
               }
            }

            this.groupOwnerButton.m_88315_(graphics, mouseX, mouseY, partialTicks);
            this.freeCheckbox.m_252865_(posX2 - 40);
            this.freeCheckbox.m_253211_(posY + 160);
         } else if (this.setupStep == 2 && this.selectedMemberSlot >= 0 && this.challengesGroupSelectorPanel != null) {
            this.challengesGroupSelectorPanel.m_88315_(graphics, mouseX, mouseY, partialTicks);
         }
      } else if (this.selectedChallenge == null && infoPanelAnimationOffset > 0.0F) {
         infoPanelAnimationOffset *= 320.0F;
         graphics.m_280168_().m_85836_();
         graphics.m_280168_().m_252880_((float)(posX + 115) - infoPanelAnimationOffset, (float)(posY - 246), 0.0F);
         graphics.m_280168_().m_252880_(256.0F, 256.0F, 0.0F);
         graphics.m_280168_().m_85841_(1.25F, 1.25F, 0.0F);
         graphics.m_280218_(ModResources.BOARD, 0, 0, 0, 0, 256, 256);
         graphics.m_280218_(ModResources.BLANK_NEW, 0, 0, 0, 0, 256, 256);
         graphics.m_280168_().m_252880_(-256.0F, -256.0F, 0.0F);
         graphics.m_280168_().m_85849_();
         int posX2 = posX + 450 - (int)infoPanelAnimationOffset;
         if (this.challengeInvitesPanel != null) {
            this.challengeInvitesPanel.m_88315_(graphics, mouseX, mouseY, partialTicks);
         }
      }

      if (this.challengesListPanel != null) {
         this.challengesListPanel.m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

      this.renderTick += 1.0F * this.f_96541_.m_91297_();
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      this.renderTick = 0.0F;
      this.m_169413_();
      this.dummyBust = (LivingEntity)EntityType.f_20492_.m_20615_(this.f_96541_.f_91073_);
      ArrowButton prevCategoryButton = new ArrowButton(posX - 110, posY + 10, 30, 20, (btn) -> {
         if (this.selectedCategoryId - 1 >= 0) {
            --this.selectedCategoryId;
            this.updateSelectedCategory();
         } else {
            this.selectedCategoryId = this.categories.size() - 1;
            this.updateSelectedCategory();
         }

      });
      prevCategoryButton.setWoodTexture();
      this.m_142416_(prevCategoryButton);
      ArrowButton nextCategoryButton = new ArrowButton(posX + 70, posY + 10, 30, 20, (btn) -> {
         if (this.selectedCategoryId + 1 < this.categories.size()) {
            ++this.selectedCategoryId;
            this.updateSelectedCategory();
         } else {
            this.selectedCategoryId = 0;
            this.updateSelectedCategory();
         }

      });
      nextCategoryButton.setWoodTexture();
      nextCategoryButton.setFlipped();
      this.m_142416_(nextCategoryButton);
      if (this.invites.size() > 0) {
         Component invitesMessage = Component.m_237110_(ModI18n.GUI_INVITES_AMOUNT, new Object[]{this.invites.size()});
         PlankButton invitesButton = new PlankButton(posX + 120, posY + 8, 100, 25, invitesMessage, (btn) -> this.showInvitesScreen());
         this.m_142416_(invitesButton);
      }

      this.prevStepButton = new ArrowButton(posX + 90, posY + 220, 50, 30, (btn) -> {
         if (this.setupStep == 1) {
            this.showChallengeInfoStep();
         } else if (this.setupStep == 2) {
            this.showGroupStep();
         }

      });
      this.prevStepButton.setWoodTexture();
      this.prevStepButton.f_93624_ = false;
      this.m_142416_(this.prevStepButton);
      this.nextStepButton = new ArrowButton(posX + 170, posY + 220, 50, 30, (btn) -> {
         if (this.setupStep == 0) {
            this.showGroupStep();
         }

      });
      this.nextStepButton.setWoodTexture();
      this.nextStepButton.setFlipped();
      this.nextStepButton.f_93624_ = false;
      this.m_142416_(this.nextStepButton);
      this.startChallengeButton = new PlankButton(posX + 250, posY + 220, 70, 30, ModI18n.GUI_START, (btn) -> {
         this.startedChallenge = true;
         boolean isFree = this.freeCheckbox != null && this.freeCheckbox.m_93840_();
         ModNetwork.sendToServer(new CStartChallengePacket(this.selectedChallenge.getCore().getRegistryKey(), isFree));
         this.f_96541_.m_91152_((Screen)null);
      });
      this.startChallengeButton.f_93624_ = false;
      this.m_142416_(this.startChallengeButton);
      this.freeCheckbox = new NewCheckboxButton(posX + 90, posY + 185, 60, 20, ModI18n.GUI_TRAINING, false);
      Tooltip freeCheckboxTooltip = Tooltip.m_257550_(ModI18nChallenges.MESSAGE_TRAINING_INFO);
      this.freeCheckbox.m_257544_(freeCheckboxTooltip);
      this.freeCheckbox.f_93624_ = false;
      this.m_142416_(this.freeCheckbox);
      this.updateSelectedCategory();
   }

   private void showInvitesScreen() {
      this.startInfoPanelAnimation();
      this.selectedChallenge = null;
      this.nextStepButton.f_93624_ = false;
      this.prevStepButton.f_93624_ = false;
      this.freeCheckbox.f_93624_ = false;
      this.startChallengeButton.f_93624_ = false;
      this.removeGroupMemberElements();
      this.m_169411_(this.challengeInvitesPanel);
      this.challengeInvitesPanel = new ChallengeInvitesPanel(this, this.player, this.invites);
      this.m_142416_(this.challengeInvitesPanel);
   }

   private void showChallengeInfoStep() {
      this.startInfoPanelAnimation();
      this.setupStep = 0;
      this.startChallengeButton.f_93624_ = false;
      this.nextStepButton.f_93624_ = true;
      this.prevStepButton.f_93624_ = false;
      this.freeCheckbox.f_93624_ = false;
      this.removeGroupMemberElements();
      this.m_169411_(this.challengeInvitesPanel);
      this.selectedMemberSlot = -1;
   }

   public void showGroupStep() {
      this.startInfoPanelAnimation();
      this.setupStep = 1;
      this.startChallengeButton.f_93624_ = true;
      this.nextStepButton.f_93624_ = false;
      this.prevStepButton.f_93624_ = true;
      this.freeCheckbox.f_93624_ = true;
      this.m_169411_(this.challengesGroupSelectorPanel);
      this.createGroupButtons();
   }

   private void showGroupSelectionStep() {
      this.startInfoPanelAnimation();
      this.setupStep = 2;
      this.startChallengeButton.f_93624_ = false;
      this.nextStepButton.f_93624_ = false;
      this.prevStepButton.f_93624_ = true;
      this.freeCheckbox.f_93624_ = false;
      this.removeGroupMemberElements();
   }

   private void createGroupButtons() {
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      this.removeGroupMemberElements();
      this.selectedMemberSlot = -1;
      this.groupOwnerButton = new GroupMemberButton(posX + 100, posY + 65, 45, 45, this.player, this.dummyBust, (btn) -> {
      });
      this.groupOwnerButton.f_93623_ = false;
      this.m_142416_(this.groupOwnerButton);
      GroupMemberButton groupMember1 = new GroupMemberButton(posX + 170, posY + 65, 45, 45, this.group[0], this.dummyBust, (btn) -> ModNetwork.sendToServer(new CFetchPossibleInvitesPacket(0)));
      this.groupMemberButtons[0] = groupMember1;
      this.m_142416_(groupMember1);
      GroupMemberButton groupMember2 = new GroupMemberButton(posX + 100, posY + 120, 45, 45, this.group[1], this.dummyBust, (btn) -> ModNetwork.sendToServer(new CFetchPossibleInvitesPacket(1)));
      this.groupMemberButtons[1] = groupMember2;
      this.m_142416_(groupMember2);
      GroupMemberButton groupMember3 = new GroupMemberButton(posX + 170, posY + 120, 45, 45, this.group[2], this.dummyBust, (btn) -> ModNetwork.sendToServer(new CFetchPossibleInvitesPacket(2)));
      this.groupMemberButtons[2] = groupMember3;
      this.m_142416_(groupMember3);
      this.freeCheckbox.f_93624_ = this.selectedChallenge.getCore().getDifficulty() != ChallengeDifficulty.STANDARD;
   }

   public void removeGroupMemberElements() {
      this.m_169411_(this.groupOwnerButton);

      for(int i = 0; i < this.groupMemberButtons.length; ++i) {
         this.m_169411_(this.groupMemberButtons[i]);
      }

   }

   public void setGroupMember(int idx, LivingEntity entity) {
      this.group[idx] = entity;
      this.showGroupStep();
   }

   public void setGroupMember(LivingEntity entity) {
      this.group[this.selectedMemberSlot] = entity;
      this.showGroupStep();
   }

   public int getSelectedMemberSlot() {
      return this.selectedMemberSlot;
   }

   public LivingEntity[] getGroup() {
      return this.group;
   }

   public void handleEvent(ChallengesScreenEvent event) {
      if (event.getPossibleInvites() != null) {
         this.openNearbyGroupSelector(event.getPossibleInvites().getSlot(), event.getPossibleInvites().getIds());
      }

   }

   public ChallengesScreenEvent decode(CompoundTag data) {
      ChallengesScreenEvent event = new ChallengesScreenEvent();
      event.deserializeNBT(data);
      return event;
   }

   private void openNearbyGroupSelector(int id, int[] nearbyIds) {
      this.showGroupSelectionStep();
      this.selectedMemberSlot = id;
      this.m_169411_(this.challengesGroupSelectorPanel);
      this.challengesGroupSelectorPanel = new ChallengeGroupSelectorPanel(this, this.player, nearbyIds);
      this.m_142416_(this.challengesGroupSelectorPanel);
   }

   private String createDifficultyStars() {
      if (this.selectedChallenge == null) {
         return "";
      } else {
         int difficulty = this.selectedChallenge.getCore().getDifficultyStars();
         boolean showSimpleDifficulty = ClientConfig.isSimpleDisplaysEnabled();
         StringBuilder sb = new StringBuilder();
         if (showSimpleDifficulty) {
            int layer = difficulty / 10;
            ChatFormatting color = ChatFormatting.RESET;
            if (layer == 0) {
               color = ChatFormatting.YELLOW;
            } else if (layer == 1) {
               color = ChatFormatting.RED;
            } else if (layer == 2) {
               color = ChatFormatting.DARK_PURPLE;
            }

            String var10001 = color.toString();
            sb.append(var10001 + difficulty + " / " + ModValues.MAX_DIFFICULTY);
         } else {
            String[] starChars = new String[]{"☆", "☆", "☆", "☆", "☆", "☆", "☆", "☆", "☆", "☆"};
            int max_layer = (difficulty - 1) / starChars.length;

            for(int i = 0; i < ModValues.MAX_DIFFICULTY; ++i) {
               int starPos = i % starChars.length;
               int layer = i / starChars.length;
               if (i < difficulty) {
                  ChatFormatting color = ChatFormatting.RESET;
                  if (layer == 0) {
                     color = ChatFormatting.YELLOW;
                  } else if (layer == 1) {
                     color = ChatFormatting.RED;
                  } else if (layer == 2) {
                     color = ChatFormatting.DARK_PURPLE;
                  }

                  String var10002 = color.toString();
                  starChars[starPos] = var10002 + (layer < max_layer ? "☆" : "★") + "§r";
               }
            }

            for(String star : starChars) {
               sb.append(star);
            }
         }

         return sb.toString();
      }
   }

   @Nullable
   public Challenge getSelectedChallenge() {
      return this.selectedChallenge;
   }

   public void setSelectedChallenge(@Nullable Challenge ch) {
      this.selectedChallenge = ch;
      this.selectedChallengeStars = this.createDifficultyStars();
      this.setupStep = 0;
      this.updateTargets();
      this.removeGroupMemberElements();
      this.startChallengeButton.f_93624_ = false;
      this.nextStepButton.f_93624_ = this.selectedChallenge != null;
      this.prevStepButton.f_93624_ = false;
      this.freeCheckbox.f_93624_ = false;
      this.selectedMemberSlot = -1;

      for(int i = 0; i < this.group.length; ++i) {
         this.group[i] = null;
      }

   }

   @Nullable
   private void updateTargets() {
      if (this.selectedChallenge != null) {
         this.targets = new LivingEntity[4];
         int i = 0;

         for(LivingEntity target : this.selectedChallenge.getCore().getTargetShowcase(this.f_96541_.f_91073_)) {
            if (i > 3) {
               break;
            }

            if (target != null) {
               this.targets[i] = target;
               ++i;
            }
         }

         this.targetsCount = i;
      }
   }

   public void updateSelectedCategory() {
      this.selectedCategory = (String)this.categories.get(this.selectedCategoryId);
      this.setSelectedChallenge((Challenge)null);
      this.runInfoPanelAnimation = false;
      this.infoPanelAnimationTick = 0.0F;
      this.runCategoryChangeAnimation = true;
      this.categoryChangeAnimationTick = 0.0F;
      if (Strings.isNullOrEmpty(this.selectedCategory)) {
         this.displayedChallenges = this.challenges;
         this.selectedCategoryLocalizedName = ModI18n.GUI_ALL;
         this.selectedCategoryIcon = null;
      } else {
         this.displayedChallenges = (List)this.challengesMap.get(this.selectedCategory);
         this.selectedCategoryLocalizedName = Component.m_237115_(this.selectedCategory);
         String categoryId = this.selectedCategory.replace("challenge.category.", "").replace("crew.name.", "");
         this.selectedCategoryIcon = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/gui/challenges/" + WyHelper.getResourceName(categoryId) + ".png");
      }

      this.m_169411_(this.challengesListPanel);
      this.challengesListPanel = new ChallengesListScrollPanel(this, this.displayedChallenges);
      this.m_142416_(this.challengesListPanel);
   }

   public void renderEntityBust(@Nullable LivingEntity entity, GuiGraphics graphics, int posX, int posY) {
      if (entity != null) {
         RendererHelper.drawLivingBust(entity, graphics, posX, posY, 30, -30, 10, false);
         String entityName = this.getEntityName(entity);
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)entityName, posX - this.f_96547_.m_92895_(entityName) / 2, posY - 30, -1);
      } else {
         RendererHelper.drawLivingBust(this.dummyBust, graphics, posX, posY, 30, -30, 10, true);
         String entityName = ModI18n.GUI_SELECT_ONE.getString();
         RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (String)entityName, posX - this.f_96547_.m_92895_(entityName) / 2, posY - 30, -1);
      }

   }

   private String getEntityName(LivingEntity entity) {
      if (entity instanceof Player player) {
         return player.m_36316_().getName();
      } else {
         return entity.m_5446_().getString();
      }
   }

   public void startInfoPanelAnimation() {
      this.runInfoPanelAnimation = true;
      this.infoPanelAnimationTick = 0.0F;
   }

   public LivingEntity getDummyBust() {
      return this.dummyBust;
   }

   public boolean isInfoPanelAnimationComplete() {
      return this.selectedChallenge != null && this.runInfoPanelAnimation && this.infoPanelAnimationTick >= 20.0F;
   }

   public void m_7379_() {
      if (!this.startedChallenge) {
         ModNetwork.sendToServer(new CDisbandChallengeGroupPacket());
      }

      super.m_7379_();
   }

   public boolean m_7043_() {
      return false;
   }
}
