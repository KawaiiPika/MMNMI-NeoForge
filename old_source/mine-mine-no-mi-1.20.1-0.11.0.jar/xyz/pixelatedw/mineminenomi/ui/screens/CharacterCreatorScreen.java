package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.CharacterCreatorSelectionInfo;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.client.CFinishCCPacket;
import xyz.pixelatedw.mineminenomi.ui.widget.AbilitySlotButton;
import xyz.pixelatedw.mineminenomi.ui.widget.ArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widget.BigArrowButton;
import xyz.pixelatedw.mineminenomi.ui.widget.BookSignSideButton;
import xyz.pixelatedw.mineminenomi.ui.widget.SimpleButton;

public class CharacterCreatorScreen extends Screen {
   private AbstractClientPlayer player;
   private ResourceLocation icon;
   private Component label;
   private Component subRaceLabel;
   private List<? extends AbilityCore<?>> topAbilities;
   private List<? extends AbilityCore<?>> bottomAbilities;
   private Faction[] factions;
   private Race[] races;
   private FightingStyle[] styles;
   private Race[] subRaces;
   private Page page;
   private int maxOpt;
   private int renderTick;
   private int subRaceId;
   private boolean hasSubRaces;
   private int[] options;
   private BookSignSideButton factionButton;
   private BookSignSideButton raceButton;
   private BookSignSideButton styleButton;
   private BigArrowButton previousSubRaceButton;
   private BigArrowButton nextSubRaceButton;
   private final List<AbilitySlotButton> abilitySlots;
   private final boolean hasRandomizedRace;
   private final boolean allowSubRaceSelect;

   public CharacterCreatorScreen(boolean hasRandomizedRace, boolean allowMinkRaceSelect) {
      super(Component.m_237119_());
      this.icon = ModResources.RANDOM;
      this.label = ModI18n.GUI_RANDOM;
      this.subRaceLabel = Component.m_237119_();
      this.topAbilities = new ArrayList();
      this.bottomAbilities = new ArrayList();
      this.page = CharacterCreatorScreen.Page.FACTION;
      this.renderTick = 0;
      this.subRaceId = 0;
      this.hasSubRaces = false;
      this.options = new int[3];
      this.abilitySlots = Lists.newArrayList();
      this.hasRandomizedRace = hasRandomizedRace;
      this.allowSubRaceSelect = allowMinkRaceSelect;
      this.factions = (Faction[])((IForgeRegistry)WyRegistry.FACTIONS.get()).getValues().stream().filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toArray((x$0) -> new Faction[x$0]);
      this.races = (Race[])((IForgeRegistry)WyRegistry.RACES.get()).getValues().stream().filter(Race::isMainRace).filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toArray((x$0) -> new Race[x$0]);
      this.styles = (FightingStyle[])((IForgeRegistry)WyRegistry.FIGHTING_STYLES.get()).getValues().stream().filter(ICharacterCreatorEntry::isInBook).sorted(ICharacterCreatorEntry::compareTo).toArray((x$0) -> new FightingStyle[x$0]);
   }

   public void m_88315_(GuiGraphics graphics, int x, int y, float f) {
      this.m_280273_(graphics);
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_((float)(posX - 120), (float)(posY - 80), 0.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 0.0F);
      graphics.m_280168_().m_85841_(1.2F, 1.2F, 1.2F);
      graphics.m_280168_().m_252880_(-128.0F, -128.0F, 0.0F);
      graphics.m_280218_(ModResources.BOOK, 0, 0, 0, 0, 256, 256);
      graphics.m_280168_().m_85849_();
      posX += 65;
      posY -= 20;
      if (!this.allowSubRaceSelect && this.renderTick % 150 == 0) {
         this.increaseSubRace();
      }

      this.drawCategoryIcon(graphics, this.icon, posX - 115, posY - 120, 0.7F);
      this.drawCategoryLabel(graphics, this.label, posX + 75, posY - 5, 1.5F);
      this.drawCategoryLabel(graphics, this.subRaceLabel, posX + 22, posY - 42, 1.1F);

      for(AbilitySlotButton btn : this.abilitySlots) {
         btn.m_88315_(graphics, x, y, f);
      }

      super.m_88315_(graphics, x, y, f);
      ++this.renderTick;
   }

   public void m_7856_() {
      this.player = this.f_96541_.f_91074_;
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      posX += 65;
      posY -= 40;
      posX -= 65;
      posY += 5;
      this.factionButton = new BookSignSideButton(posX - 110, posY + 65, 110, 35, ModI18n.GUI_FACTION, (btn) -> {
         this.page = CharacterCreatorScreen.Page.FACTION;
         this.resetButtonState();
         this.factionButton.setIsPressed(true);
         this.tryToggleSubRaceButtons();
      });
      this.m_142416_(this.factionButton);
      posY += 45;
      this.raceButton = new BookSignSideButton(posX - 110, posY + 65, 110, 35, ModI18n.GUI_RACE, (btn) -> {
         this.page = CharacterCreatorScreen.Page.RACE;
         this.resetButtonState();
         this.raceButton.setIsPressed(true);
         this.tryToggleSubRaceButtons();
      });
      if (!this.hasRandomizedRace) {
         this.m_142416_(this.raceButton);
         posY += 45;
      }

      this.styleButton = new BookSignSideButton(posX - 110, posY + 65, 110, 35, ModI18n.GUI_STYLE, (btn) -> {
         this.page = CharacterCreatorScreen.Page.FIGHTING_STYLE;
         this.resetButtonState();
         this.styleButton.setIsPressed(true);
         this.tryToggleSubRaceButtons();
      });
      this.m_142416_(this.styleButton);
      if (this.hasRandomizedRace) {
         posY += 45;
      }

      posX += 65;
      posY -= 95;
      posX += 30;
      posY += 130;
      ArrowButton prevButton = new ArrowButton(posX + 60, posY + 80, 35, 15, (btn) -> {
         this.decreaseSelectedPage();
         this.tryToggleSubRaceButtons();
      });
      this.m_142416_(prevButton);
      posX -= 90;
      ArrowButton nextButton = new ArrowButton(posX + 215, posY + 80, 35, 15, (btn) -> {
         this.increaseSelectedPage();
         this.tryToggleSubRaceButtons();
      });
      nextButton.setFlipped();
      this.m_142416_(nextButton);
      posX += 30;
      posY -= 110;
      this.previousSubRaceButton = new BigArrowButton(posX + 110, posY + 80, 20, 100, (btn) -> this.decreaseSubRace());
      this.previousSubRaceButton.f_93624_ = false;
      this.m_142416_(this.previousSubRaceButton);
      this.nextSubRaceButton = new BigArrowButton(posX + 208, posY + 80, 20, 100, (btn) -> this.increaseSubRace());
      this.nextSubRaceButton.setFlipped();
      this.nextSubRaceButton.f_93624_ = false;
      this.m_142416_(this.nextSubRaceButton);
      posX -= 115;
      posY += 30;
      SimpleButton finishButton = new SimpleButton(posX + 115, posY + 175, 70, 30, ModI18n.GUI_FINISH, (btn) -> this.completeCreation());
      finishButton.setTextOnly();
      finishButton.setFontSize(1.5F);
      this.m_142416_(finishButton);
      this.createAbilitySlotButtons();
   }

   private void tryToggleSubRaceButtons() {
      this.previousSubRaceButton.f_93624_ = false;
      this.nextSubRaceButton.f_93624_ = false;
      if (this.page == CharacterCreatorScreen.Page.RACE && this.hasSubRaces && this.allowSubRaceSelect) {
         this.previousSubRaceButton.f_93624_ = true;
         this.nextSubRaceButton.f_93624_ = true;
      }

   }

   private void resetButtonState() {
      this.factionButton.setIsPressed(false);
      this.raceButton.setIsPressed(false);
      this.styleButton.setIsPressed(false);
      this.updateSelection();
   }

   private void completeCreation() {
      Minecraft.m_91087_().m_91152_((Screen)null);
      ModNetwork.sendToServer(new CFinishCCPacket(this.getSelectedFactionId(), this.getSelectedRaceId(), this.getSelectedStyleId(), this.subRaceId));
   }

   public void m_86600_() {
      int var10001;
      switch (this.page) {
         case FACTION -> var10001 = this.factions.length + 1;
         case FIGHTING_STYLE -> var10001 = this.styles.length + 1;
         case RACE -> var10001 = this.races.length + 1;
         default -> throw new IncompatibleClassChangeError();
      }

      this.maxOpt = var10001;
   }

   private void drawCategory(GuiGraphics graphics, String text, int posX, int posY, float scale) {
      this.drawCategoryLabel(graphics, Component.m_237113_(text), posX, posY, scale);
   }

   private void drawCategoryIcon(GuiGraphics graphics, ResourceLocation icon, int posX, int posY, float scale) {
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_((float)posX, (float)posY, 2.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 0.0F);
      graphics.m_280168_().m_85841_(scale, scale, 1.0F);
      graphics.m_280168_().m_252880_(-128.0F, -128.0F, 0.0F);
      graphics.m_280218_(this.icon, 0, 0, 0, 0, 256, 256);
      graphics.m_280168_().m_85849_();
   }

   private void drawCategoryLabel(GuiGraphics graphics, Component text, int posX, int posY, float scale) {
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_((float)posX, (float)posY, 2.0F);
      graphics.m_280168_().m_252880_(128.0F, 128.0F, 0.0F);
      graphics.m_280168_().m_85841_(scale, scale, 1.0F);
      graphics.m_280168_().m_252880_(-128.0F, -128.0F, 0.0F);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)text, -this.f_96547_.m_92852_(text) / 2, 0, -1);
      graphics.m_280168_().m_85849_();
   }

   public int getSelectedFactionId() {
      return this.options[CharacterCreatorScreen.Page.FACTION.ordinal()];
   }

   public int getSelectedRaceId() {
      return this.options[CharacterCreatorScreen.Page.RACE.ordinal()];
   }

   public int getSelectedStyleId() {
      return this.options[CharacterCreatorScreen.Page.FIGHTING_STYLE.ordinal()];
   }

   public int getSelectedId() {
      return this.options[this.page.ordinal()];
   }

   public void increaseSelectedPage() {
      if (this.options[this.page.ordinal()] + 1 < this.maxOpt) {
         int var10002 = this.options[this.page.ordinal()]++;
      } else {
         this.options[this.page.ordinal()] = 0;
      }

      this.updateSelection();
   }

   public void decreaseSelectedPage() {
      if (this.options[this.page.ordinal()] - 1 > -1) {
         int var10002 = this.options[this.page.ordinal()]--;
      } else {
         this.options[this.page.ordinal()] = this.maxOpt - 1;
      }

      this.updateSelection();
   }

   private void updateSelection() {
      CharacterCreatorSelectionInfo info = ICharacterCreatorEntry.RANDOM.getSelectionInfo();
      Component label = ModI18n.GUI_RANDOM;
      Component subRaceLabel = Component.m_237119_();
      this.hasSubRaces = false;
      if (this.getSelectedId() > 0) {
         switch (this.page) {
            case FACTION:
               Faction faction = this.factions[this.getSelectedId() - 1];
               info = faction.getSelectionInfo();
               label = faction.getLabel();
               break;
            case FIGHTING_STYLE:
               FightingStyle style = this.styles[this.getSelectedId() - 1];
               info = style.getSelectionInfo();
               label = style.getLabel();
               break;
            case RACE:
               Race race = this.races[this.getSelectedId() - 1];
               if (race.hasSubRaces()) {
                  this.subRaces = (Race[])race.getSubRaces().stream().map((obj) -> (Race)obj.get()).filter(ICharacterCreatorEntry::isInBook).sorted((e1, e2) -> e1.getBookOrder() - e2.getBookOrder()).toArray((x$0) -> new Race[x$0]);
                  if (this.subRaces.length > 0) {
                     this.hasSubRaces = true;
                  }

                  subRaceLabel = Component.m_237113_(this.subRaces[this.subRaceId].getLabel().getString().replace(race.getLabel().getString(), ""));
               }

               info = race.getSelectionInfo();
               label = race.getLabel();
               break;
            default:
               throw new IllegalArgumentException("Unexpected value: " + String.valueOf(this.page));
         }
      }

      this.icon = info.getIcon();
      this.label = label;
      this.subRaceLabel = subRaceLabel;
      this.topAbilities = info.getTopAbilities().stream().map((supp) -> (AbilityCore)supp.get()).toList();
      this.bottomAbilities = info.getBottomAbilities().stream().map((supp) -> (AbilityCore)supp.get()).toList();
      this.createAbilitySlotButtons();
   }

   private void createAbilitySlotButtons() {
      int posX = (this.f_96543_ - 256) / 2;
      int posY = (this.f_96544_ - 256) / 2;
      this.abilitySlots.clear();
      int i = 0;
      int xOffset = 0;
      int yOffset = 0;

      for(AbilityCore<?> core : this.topAbilities) {
         if (i % 3 == 0) {
            xOffset = 0;
            yOffset += 30;
         } else {
            xOffset += 38;
         }

         IAbility abl = core.createAbility();
         AbilitySlotButton slot = new AbilitySlotButton(abl, posX + 20 + xOffset, posY + 10 + yOffset, 22, 22, this.player, (btn) -> {
         });
         this.abilitySlots.add(slot);
         this.m_7787_(slot);
         ++i;
      }

      i = 0;
      xOffset = 0;
      yOffset = 0;

      for(AbilityCore<?> core : this.bottomAbilities) {
         if (i % 3 == 0) {
            xOffset = 0;
            yOffset += 30;
         } else {
            xOffset += 38;
         }

         IAbility abl = core.createAbility();
         AbilitySlotButton slot = new AbilitySlotButton(abl, posX + 20 + xOffset, posY + 100 + yOffset, 22, 22, this.player, (btn) -> {
         });
         this.abilitySlots.add(slot);
         this.m_7787_(slot);
         ++i;
      }

   }

   public void increaseSubRace() {
      if (this.subRaceId == this.subRaces.length - 1) {
         this.subRaceId = 0;
      } else {
         ++this.subRaceId;
      }

      this.updateSubRaceSelection();
   }

   public void decreaseSubRace() {
      if (this.subRaceId == 0) {
         this.subRaceId = this.subRaces.length - 1;
      } else {
         --this.subRaceId;
      }

      this.updateSubRaceSelection();
   }

   private void updateSubRaceSelection() {
      this.icon = this.subRaces[this.subRaceId].getSelectionInfo().getIcon();
      this.subRaceLabel = Component.m_237113_(this.subRaces[this.subRaceId].getLabel().getString().replace(this.label.getString(), ""));
   }

   private static enum Page {
      FACTION,
      RACE,
      FIGHTING_STYLE;

      // $FF: synthetic method
      private static Page[] $values() {
         return new Page[]{FACTION, RACE, FIGHTING_STYLE};
      }
   }
}
