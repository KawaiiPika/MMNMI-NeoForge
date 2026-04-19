package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IDescriptiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.handlers.ui.CombatModeHandler;
import xyz.pixelatedw.mineminenomi.init.ModKeybindings;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nTutorials;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.ui.panel.AbilitiesListScrollPanel;
import xyz.pixelatedw.mineminenomi.ui.widget.AbilitySlotButton;
import xyz.pixelatedw.mineminenomi.ui.widget.AnimatedTextButton;
import xyz.pixelatedw.mineminenomi.ui.widget.BookSignSideButton;

public class AbilitiesListScreen extends Screen {
   private static final List<Component> EMPTY_TOOLTIPS = new ArrayList();
   private static final int TOOLTIP_BACKGROUND_START = WyHelper.hexToRGB("#A78342").getRGB();
   private static final int TOOLTIP_BACKGROUND_END = WyHelper.hexToRGB("#AD8F58").getRGB();
   private static final int TOOLTIP_BORDER_START = WyHelper.hexToRGB("#e3b160").getRGB();
   private static final int TOOLTIP_BORDER_END = WyHelper.hexToRGB("#cb7e23").getRGB();
   protected AbstractClientPlayer player;
   private AbilitiesListScrollPanel abilitiesList;
   public int groupSelected = 1;
   public int slotSelected = -1;
   public boolean isDirty;
   private int tickCount;
   private IAbilityData abilityDataProps;
   protected final List<AbilitySlotButton> abilitySlots = Lists.newArrayList();
   private final List<Renderable> backgroundWidgets = new ArrayList();
   private final List<Renderable> foregroundWidgets = new ArrayList();
   private static final Map<AbilityCore<? extends IAbility>, List<Component>> TOOLTIPS_CACHE = new HashMap();
   private static boolean hasStatsShown = false;
   private static boolean clearCache = false;
   private Compactness compactness;
   private Selection selection;
   private boolean showTooltips;
   private AbilityCore<?> draggedAbility = null;

   public AbilitiesListScreen() {
      super(Component.m_237119_());
      this.player = Minecraft.m_91087_().f_91074_;
      this.f_96541_ = Minecraft.m_91087_();
      this.compactness = ClientConfig.getCompactness();
      this.selection = ClientConfig.getSelectionMode();
      this.showTooltips = ClientConfig.getTooltipsShown();
      this.abilityDataProps = (IAbilityData)AbilityCapability.get(this.player).orElse((Object)null);
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = this.f_96543_;
      int posY = this.f_96544_;
      graphics.m_280218_(ModResources.BOARD, (posX - 250) / 2, (posY - 230) / 2, 0, 0, 256, 256);
      graphics.m_280218_(ModResources.BOARD, (posX - 250) / 2, posY - 60, 0, 0, 256, 256);

      for(int i = 0; i < this.backgroundWidgets.size(); ++i) {
         ((Renderable)this.backgroundWidgets.get(i)).m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

      graphics.m_280218_(ModResources.BLANK_NEW, (posX - 250) / 2, (posY - 230) / 2, 0, 0, 256, 256);
      graphics.m_280218_(ModResources.BLANK_NEW, (posX - 250) / 2, posY - 60, 0, 0, 256, 256);
      String barId = "" + (1 + this.groupSelected);
      RendererHelper.drawStringWithBorder(this.f_96541_.f_91062_, graphics, barId, (posX + 8) / 2 - (this.f_96541_.f_91062_.m_92895_(barId) + 104), posY - 24, Color.WHITE.getRGB());
      if (this.abilitiesList != null) {
         this.abilitiesList.m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

      for(int i = 0; i < this.abilitySlots.size(); ++i) {
         AbilitySlotButton slot = (AbilitySlotButton)this.abilitySlots.get(i);
         slot.m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

      for(int i = 0; i < this.foregroundWidgets.size(); ++i) {
         ((Renderable)this.foregroundWidgets.get(i)).m_88315_(graphics, mouseX, mouseY, partialTicks);
      }

      if (this.hasDraggedAbility()) {
         RendererHelper.drawIcon(this.getDraggedAbility().getIcon(), graphics.m_280168_(), (float)mouseX, (float)mouseY, 1.0F, 16.0F, 16.0F);
      }

   }

   public void m_7856_() {
      int posX2 = this.f_96543_ / 2;
      int posY2 = this.f_96544_ / 2;
      TOOLTIPS_CACHE.clear();
      this.groupSelected = this.abilityDataProps.getCombatBarSet();
      this.foregroundWidgets.clear();
      this.backgroundWidgets.clear();
      this.updateSlots();
      this.setupOptionsAndHelp();
      int idx = 0;

      for(AbilityCategory category : AbilityCategory.values()) {
         if (category != AbilityCategory.ALL) {
            AbilityCore<?> core = (AbilityCore)this.abilityDataProps.getUnlockedAbilities().stream().map(AbilityCoreUnlockWrapper::getAbilityCore).filter(category.isCorePartofCategory()).findFirst().orElse((Object)null);
            if (core != null) {
               boolean isFlipped = false;
               int iconOffset = 0;
               int backgroundOffset = 0;
               if (idx == 4) {
                  posX2 += 250;
                  posY2 -= 140;
               }

               if (idx >= 4) {
                  isFlipped = true;
                  iconOffset = 3;
                  backgroundOffset = 24;
               }

               int posY3 = posY2 - 100 + idx * 70 / 2;
               ResourceLocation icon = category.getIcon(this.player);
               if (icon == null) {
                  IAbility abl = this.abilityDataProps.getEquippedOrPassiveAbility(core);
                  if (abl != null) {
                     icon = abl.getIcon(this.player);
                  } else {
                     icon = core.getIcon();
                  }
               }

               BookSignSideButton button = new BookSignSideButton(posX2 - 165 + backgroundOffset, posY3, 60, 35, Component.m_237119_(), (btn) -> this.updateListScreen(category));
               button.setTextureWidth(60);
               button.setIcon(icon, posX2 - 136 + iconOffset, posY3 + 5, 1.45F);
               if (isFlipped) {
                  button.setFlipped();
               }

               this.backgroundWidgets.add(button);
               this.m_142416_(button);
               ++idx;
            }
         }
      }

      this.updateListScreen(AbilityCategory.DEVIL_FRUITS);
   }

   public void updateSlots() {
      int posX = this.f_96543_;
      int posY = this.f_96544_;
      this.slotSelected = -1;

      for(AbilitySlotButton btn : this.abilitySlots) {
         this.m_169411_(btn);
      }

      this.abilitySlots.clear();
      posX += 10;

      for(int i = 0; i < 8; ++i) {
         RenderSystem.enableBlend();
         int id = this.getRealSlotId(i);
         IAbility abl = this.abilityDataProps.getEquippedAbility(id);
         AbilitySlotButton slotButton = new AbilitySlotButton(abl, posX / 2 - 101 + i * 25, posY - 31, 22, 21, this.player, (btnx) -> this.onClickAbilitySlot((AbilitySlotButton)btnx));
         slotButton.setSlotIndex(id);
         this.m_7787_(slotButton);
         this.abilitySlots.add(slotButton);
      }

   }

   private Tooltip getHelpTooltips() {
      StringBuilder sb = new StringBuilder();
      if (this.selection == AbilitiesListScreen.Selection.DRAG_AND_DROP) {
         sb.append(ModI18nTutorials.ABILITY_SELECTOR_DRAG_AND_DROP.getString());
      } else if (this.selection == AbilitiesListScreen.Selection.KEYBIND) {
         sb.append(ModI18nTutorials.ABILITY_SELECTOR_KEYBIND.getString());
      }

      sb.append("\n\n");
      if (this.showTooltips) {
         sb.append(ModI18nTutorials.ABILITY_SELECTOR_HIDE_TOOLTIPS.getString());
      } else {
         sb.append(ModI18nTutorials.ABILITY_SELECTOR_SHOW_TOOLTIPS.getString());
      }

      return Tooltip.m_257550_(Component.m_237113_(sb.toString()));
   }

   public void setupOptionsAndHelp() {
      int posX = this.f_96543_;
      AnimatedTextButton helpButton = new AnimatedTextButton(posX - 20, this.f_96544_ - 20, 20, 20, ModI18n.GUI_HELP_SHORT, (btn) -> {
      });
      helpButton.m_257544_(this.getHelpTooltips());
      if (ClientConfig.getHelpButtonShown()) {
         this.foregroundWidgets.add(helpButton);
         this.m_142416_(helpButton);
      }

      Button.OnPress compactnessPress = (btn) -> {
         this.compactness = this.compactness.nextElement();
         this.abilitiesList.setCompactness(this.compactness);
         btn.m_93666_(this.compactness.getShortNotation());
         btn.m_257544_(Tooltip.m_257550_(this.compactness.getLabel()));
         ClientConfig.setCompactness(this.compactness);
         btn.m_93692_(false);
      };
      Button compactnessButton = (new Button.Builder(this.compactness.getShortNotation(), compactnessPress)).m_252794_(posX - 20, 0).m_253046_(20, 20).m_253136_();
      compactnessButton.m_257544_(Tooltip.m_257550_(this.compactness.getLabel()));
      this.foregroundWidgets.add(compactnessButton);
      this.m_142416_(compactnessButton);
      Button.OnPress selectionPress = (btn) -> {
         this.slotSelected = -1;

         for(AbilitySlotButton slot : this.abilitySlots) {
            slot.setIsPressed(false);
         }

         this.selection = this.selection.nextElement();
         btn.m_93666_(this.selection.getShortNotation());
         btn.m_257544_(Tooltip.m_257550_(this.selection.getLabel()));
         ClientConfig.setSelectionMode(this.selection);
         helpButton.setMarked();
         helpButton.m_257544_(this.getHelpTooltips());
         btn.m_93692_(false);
      };
      Button selectionButton = (new Button.Builder(this.selection.getShortNotation(), selectionPress)).m_252794_(posX - 40, 0).m_253046_(20, 20).m_253136_();
      selectionButton.m_257544_(Tooltip.m_257550_(this.selection.getLabel()));
      this.foregroundWidgets.add(selectionButton);
      this.m_142416_(selectionButton);
      Button.OnPress showTooltipsPress = (btn) -> {
         ClientConfig.toggleShowingTooltips();
         this.showTooltips = ClientConfig.getTooltipsShown();
         btn.m_93666_(this.showTooltips ? ModI18n.SHOW_TOOLTIPS_ON_LABEL : ModI18n.SHOW_TOOLTIPS_OFF_LABEL);
         helpButton.setMarked();
         helpButton.m_257544_(this.getHelpTooltips());
      };
      Button showTooltips = (new Button.Builder(this.showTooltips ? ModI18n.SHOW_TOOLTIPS_ON_LABEL : ModI18n.SHOW_TOOLTIPS_OFF_LABEL, showTooltipsPress)).m_252794_(posX - 60, 0).m_253046_(20, 20).m_253136_();
      showTooltips.m_257544_(Tooltip.m_257550_(ModI18n.SHOW_TOOLTIPS_LABEL));
      this.foregroundWidgets.add(showTooltips);
      this.m_142416_(showTooltips);
   }

   public void onClickAbilitySlot(AbilitySlotButton btn) {
      int slotId = btn.getSlotIndex();
      if (this.getSelectionMode() == AbilitiesListScreen.Selection.DRAG_AND_DROP) {
         IAbility ability = this.abilityDataProps.getEquippedAbility(slotId);
         if (ability != null) {
            this.setDraggedAbility(ability.getCore());
         }

         this.removeAbilityFromSlot(slotId);
      } else {
         if (this.slotSelected != slotId) {
            this.slotSelected = slotId;

            for(AbilitySlotButton slotBtn : this.abilitySlots) {
               slotBtn.setIsPressed(false);
            }

            btn.setIsPressed(true);
         } else {
            IAbility ability = this.abilityDataProps.getEquippedAbility(this.slotSelected);
            if (ability == null) {
               return;
            }

            this.removeAbilityFromSlot(slotId);
         }

      }
   }

   public void m_86600_() {
      if (this.isDirty && this.tickCount++ >= 1) {
         int i = 0;

         for(AbilitySlotButton slotBtn : this.abilitySlots) {
            int id = i + this.groupSelected * 8;
            IAbility abl = this.abilityDataProps.getEquippedAbility(id);
            slotBtn.setAbility(abl);
            ++i;
         }

         this.isDirty = false;
         this.tickCount = 0;
      }

   }

   public boolean m_7933_(int key, int pScanCode, int pModifiers) {
      this.checkKeybinding(key);
      return super.m_7933_(key, pScanCode, pModifiers);
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      this.checkKeybinding(button);
      if (button == 1) {
         AbilitySlotButton slot = this.getHoverSlotIndex(mouseX, mouseY);
         if (slot != null) {
            this.slotSelected = slot.getSlotIndex();
            if (this.slotSelected >= 0) {
               if (this.getSelectionMode() == AbilitiesListScreen.Selection.KEYBIND) {
                  this.removeAbilityFromSlot(this.slotSelected);
                  this.slotSelected = -1;
               } else if (this.getSelectionMode() == AbilitiesListScreen.Selection.DRAG_AND_DROP) {
                  this.removeAbilityFromSlot(this.slotSelected);
               }
            }
         }
      }

      return super.m_6375_(mouseX, mouseY, button);
   }

   public boolean m_6348_(double mouseX, double mouseY, int button) {
      if (this.getSelectionMode() == AbilitiesListScreen.Selection.DRAG_AND_DROP && this.hasDraggedAbility() && button == 0) {
         AbilitySlotButton slot = this.getHoverSlotIndex(mouseX, mouseY);
         if (slot != null) {
            int slotIndex = slot.getSlotIndex();
            this.addAbilityToSlot(slotIndex, this.getDraggedAbility());
         }

         this.setDraggedAbility((AbilityCore)null);
      }

      return true;
   }

   private void checkKeybinding(int key) {
      if (this.getSelectionMode() == AbilitiesListScreen.Selection.KEYBIND) {
         for(int i = 0; i < ModKeybindings.COMBAT_BAR_KEYS.size(); ++i) {
            KeyMapping bind = (KeyMapping)ModKeybindings.COMBAT_BAR_KEYS.get(i);
            if (bind.getKey().m_84873_() == key) {
               this.slotSelected = this.getRealSlotId(i);
               AbilityCore<?> core = this.abilitiesList.getHoveredEntry();
               if (core != null) {
                  if (this.abilityDataProps.hasEquippedAbility(core)) {
                     int slotId = this.abilityDataProps.getEquippedAbilitySlot(core);
                     this.removeAbilityFromSlot(slotId);
                  }

                  this.addAbilityToSlot(this.slotSelected, core);

                  for(AbilitySlotButton slot : this.abilitySlots) {
                     slot.setIsPressed(false);
                  }

                  ((AbilitySlotButton)this.abilitySlots.get(i)).setIsPressed(true);
               }
               break;
            }
         }
      }

      int clientMaxBars = ServerConfig.getAbilityBars();
      int maxBars = Math.min(clientMaxBars, CombatModeHandler.abilityBars);
      if (key == ModKeybindings.NEXT_COMBAT_BAR.getKey().m_84873_()) {
         if (this.groupSelected < maxBars - 1) {
            ++this.groupSelected;
         } else {
            this.groupSelected = 0;
         }

         this.updateSlots();
      } else if (key == ModKeybindings.PREV_COMBAT_BAR.getKey().m_84873_()) {
         if (this.groupSelected > 0) {
            --this.groupSelected;
         } else {
            this.groupSelected = maxBars - 1;
         }

         this.updateSlots();
      }

   }

   @Nullable
   protected AbilitySlotButton getHoverSlotIndex(double mouseX, double mouseY) {
      for(int i = 0; i < this.abilitySlots.size(); ++i) {
         AbilitySlotButton slot = (AbilitySlotButton)this.abilitySlots.get(i);
         if (slot != null && slot.m_5953_(mouseX, mouseY)) {
            return slot;
         }
      }

      return null;
   }

   protected int getRealSlotId(int slot) {
      slot %= 8;
      return slot + this.groupSelected * 8;
   }

   protected void addAbilityToSlot(int slot, AbilityCore<?> core) {
      ModNetwork.sendToServer(new CEquipAbilityPacket(slot, core));
      this.isDirty = true;
   }

   protected void removeAbilityFromSlot(int slot) {
      ModNetwork.sendToServer(new CRemoveAbilityPacket(slot));
      this.isDirty = true;
   }

   public void updateListScreen(AbilityCategory category) {
      this.m_169411_(this.abilitiesList);
      this.abilitiesList = new AbilitiesListScrollPanel(this, this.abilityDataProps, category);
      this.m_142416_(this.abilitiesList);
      this.m_7522_(this.abilitiesList);
   }

   public static boolean canShowTooltips() {
      boolean canShowTooltips = ClientConfig.getTooltipsShown();
      if (canShowTooltips) {
         return !Screen.m_96639_();
      } else {
         return Screen.m_96639_();
      }
   }

   private static List<Component> generateAbilityTooltip(IAbility ability) {
      boolean canShowAdvancedTooltips = Minecraft.m_91087_().f_91066_.f_92125_;
      boolean canShowStats = !ClientConfig.hidesAbilityStats() || Screen.m_96638_();
      Set<AbilityDescriptionLine> newSet = ability.getCore().getDescription();
      List<Component> tooltips = new ArrayList();
      AbstractClientPlayer player = Minecraft.m_91087_().f_91074_;
      String var10001 = ability.getCore().getLocalizedName().getString();
      tooltips.add(Component.m_237113_(var10001 + (newSet != null && !newSet.isEmpty() ? "\n" : "")));
      if (newSet != null) {
         long advancedLines = newSet.stream().filter((line) -> line.isAdvanced()).count();
         Stream var10000 = newSet.stream().filter((line) -> !line.isAdvanced() || line.isAdvanced() && canShowStats).map((line) -> line.getTextComponent(player, ability)).map((text) -> {
            if (text == null) {
               return null;
            } else {
               try {
                  return ComponentUtils.m_130731_((CommandSourceStack)null, text, player, 1);
               } catch (CommandSyntaxException e) {
                  e.printStackTrace();
                  return null;
               }
            }
         }).filter(Objects::nonNull);
         Objects.requireNonNull(tooltips);
         var10000.forEach(tooltips::add);
         if (ability instanceof IDescriptiveAbility) {
            IDescriptiveAbility descAbility = (IDescriptiveAbility)ability;
            tooltips.add(AbilityDescriptionLine.NEW_LINE.expand(player, ability));
            List<Component> desc = new ArrayList();
            descAbility.appendDescription(player, desc);
            tooltips.addAll(desc);
         }

         if (advancedLines > 0L && ClientConfig.hidesAbilityStats() && !Screen.m_96638_()) {
            tooltips.add(AbilityDescriptionLine.NEW_LINE.expand(player, ability));
            tooltips.add(ModI18n.GUI_SHOW_ABILITY_STATS.m_6881_().m_6270_(Style.f_131099_.m_131155_(true).m_131140_(ChatFormatting.YELLOW)));
         }
      }

      if (canShowAdvancedTooltips) {
         String color = "§7";
         ResourceLocation key = ability.getCore().getRegistryKey();
         if (key != null) {
            tooltips.add(Component.m_237113_("\n" + color + key.toString()));
         }
      }

      return tooltips;
   }

   public static void renderAbilityTooltip(GuiGraphics graphics, int mouseX, int mouseY, IAbility ability) {
      Minecraft mc = Minecraft.m_91087_();
      Window window = mc.m_91268_();
      if (ClientConfig.hidesAbilityStats()) {
         if (Screen.m_96638_() && !hasStatsShown) {
            clearCache = true;
            hasStatsShown = true;
         } else if (!Screen.m_96638_() && hasStatsShown) {
            clearCache = true;
            hasStatsShown = false;
         }

         if (clearCache) {
            TOOLTIPS_CACHE.clear();
            clearCache = false;
         }
      }

      if (!TOOLTIPS_CACHE.containsKey(ability.getCore())) {
         TOOLTIPS_CACHE.putIfAbsent(ability.getCore(), generateAbilityTooltip(ability));
      }

      List<Component> tooltips = (List)TOOLTIPS_CACHE.getOrDefault(ability.getCore(), EMPTY_TOOLTIPS);
      RendererHelper.drawAbilityTooltip(ability, graphics, tooltips, mouseX, mouseY, window.m_85445_(), window.m_85446_(), 210, TOOLTIP_BACKGROUND_START, TOOLTIP_BACKGROUND_END, TOOLTIP_BORDER_START, TOOLTIP_BORDER_END, mc.f_91062_, DefaultTooltipPositioner.f_262752_);
      RenderSystem.enableBlend();
   }

   public boolean m_7043_() {
      return false;
   }

   public Compactness getCompactness() {
      return this.compactness;
   }

   public Selection getSelectionMode() {
      return this.selection;
   }

   public boolean showTooltips() {
      return this.showTooltips;
   }

   @Nullable
   public AbilityCore<?> getDraggedAbility() {
      return this.draggedAbility;
   }

   public boolean hasDraggedAbility() {
      return this.draggedAbility != null;
   }

   public void setDraggedAbility(AbilityCore<?> core) {
      this.draggedAbility = core;
   }

   public static enum Selection implements Enumeration<Selection> {
      DRAG_AND_DROP(ModI18n.SELECTION_DD_LABEL, ModI18n.SELECTION_DD_SHORT_LABEL),
      KEYBIND(ModI18n.SELECTION_KB_LABEL, ModI18n.SELECTION_KB_SHORT_LABEL);

      private final Component label;
      private final Component shortLabel;

      private Selection(Component label, Component shortLabel) {
         this.label = label;
         this.shortLabel = shortLabel;
      }

      public Component getShortNotation() {
         return this.shortLabel;
      }

      public Component getLabel() {
         return this.label;
      }

      public boolean hasMoreElements() {
         return true;
      }

      public Selection nextElement() {
         return values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static Selection[] $values() {
         return new Selection[]{DRAG_AND_DROP, KEYBIND};
      }
   }

   public static enum Compactness implements Enumeration<Compactness> {
      SPATIOUS(ModI18n.COMPACTNESS_S_LABEL, ModI18n.COMPACTNESS_S_SHORT_LABEL, 1.25F, 16, 1.0F, 0, 4, 0, 0),
      COMPACT(ModI18n.COMPACTNESS_C_LABEL, ModI18n.COMPACTNESS_C_SHORT_LABEL, 1.05F, 16, 1.0F, 0, 4, 0, 0),
      MINIMAL(ModI18n.COMPACTNESS_M_LABEL, ModI18n.COMPACTNESS_M_SHORT_LABEL, 0.9F, 12, 0.8F, 5, 2, 5, 2);

      private final float spacing;
      private final int iconSize;
      private final float slotScale;
      private final int slotOffsetX;
      private final int slotOffsetY;
      private final int iconOffsetX;
      private final int iconOffsetY;
      private final Component label;
      private final Component shortLabel;

      private Compactness(Component label, Component shortLabel, float spacing, int iconSize, float slotScale, int slotOffsetX, int slotOffsetY, int iconOffsetX, int iconOffsetY) {
         this.spacing = spacing;
         this.iconSize = iconSize;
         this.slotScale = slotScale;
         this.slotOffsetX = slotOffsetX;
         this.slotOffsetY = slotOffsetY;
         this.iconOffsetX = iconOffsetX;
         this.iconOffsetY = iconOffsetY;
         this.label = label;
         this.shortLabel = shortLabel;
      }

      public float getSpacing() {
         return this.spacing;
      }

      public int getIconSize() {
         return this.iconSize;
      }

      public float getSlotScale() {
         return this.slotScale;
      }

      public int getSlotOffsetX() {
         return this.slotOffsetX;
      }

      public int getSlotOffsetY() {
         return this.slotOffsetY;
      }

      public int getIconOffsetX() {
         return this.iconOffsetX;
      }

      public int getIconOffsetY() {
         return this.iconOffsetY;
      }

      public String toString() {
         return WyHelper.capitalize(this.name());
      }

      public Component getShortNotation() {
         return this.shortLabel;
      }

      public Component getLabel() {
         return this.label;
      }

      public boolean hasMoreElements() {
         return true;
      }

      public Compactness nextElement() {
         return values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static Compactness[] $values() {
         return new Compactness[]{SPATIOUS, COMPACT, MINIMAL};
      }
   }
}
