package xyz.pixelatedw.mineminenomi.ui.panel;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCoreUnlockWrapper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CTogglePassiveAbilityPacket;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

public class AbilitiesListScrollPanel extends ScrollPanel {
   private static final int ENTRY_HEIGHT = 20;
   private static final Color DEFAULT_ICON_COLOR = WyHelper.hexToRGB("#FFFFFF");
   private static final Color PASSIVE_PAUSED_COLOR = WyHelper.hexToRGB("#FF0000");
   private final AbilitiesListScreen parent;
   private final AbstractClientPlayer player;
   private final IAbilityData props;
   private final List<Entry<IAbility>> allAbilities;
   private final TreeSet<AbilityCore<IAbility>> unlockedAbilities;
   private final TreeSet<IAbility> passiveAbilities;
   private final AbilityCategory category;
   private final Map<AbilityCore<IAbility>, IAbility> unlockedToEquippedMap = new HashMap();
   private final Minecraft mc = Minecraft.m_91087_();
   private AbilitiesListScreen.Compactness compactness;
   private Entry<IAbility> hoveredEntry;

   public AbilitiesListScrollPanel(AbilitiesListScreen parent, IAbilityData abilityProps, AbilityCategory category) {
      super(parent.getMinecraft(), 210, 130, parent.f_96544_ / 2 - 92, parent.f_96543_ / 2 - 102);
      Minecraft parentMinecraft = parent.getMinecraft();
      this.parent = parent;
      this.player = parentMinecraft.f_91074_;
      this.props = abilityProps;
      this.category = category;
      this.compactness = parent.getCompactness();
      this.allAbilities = new ArrayList();
      this.unlockedAbilities = (TreeSet)this.props.getUnlockedAbilitiesStream().map(AbilityCoreUnlockWrapper::getAbilityCore).filter((corex) -> !corex.isHidden() && corex.getType().equals(AbilityType.ACTION) && category.isCorePartofCategory().test(corex)).collect(Collectors.toCollection(TreeSet::new));
      this.passiveAbilities = (TreeSet)this.props.getPassiveAbilities((ablx) -> !ablx.getCore().isHidden() && ablx.getCore().getType() == AbilityType.PASSIVE && category.isAbilityPartofCategory().test(ablx)).stream().collect(Collectors.toCollection(TreeSet::new));

      for(AbilityCore<IAbility> core : this.unlockedAbilities) {
         this.allAbilities.add(new Entry(core));
      }

      this.allAbilities.add(new Entry());

      for(IAbility abl : this.passiveAbilities) {
         this.allAbilities.add(new Entry(abl));
      }

      this.unlockedToEquippedMap.clear();

      for(AbilityCore<IAbility> core : this.unlockedAbilities) {
         IAbility abl = this.props.getEquippedAbility(core);
         if (abl == null) {
            abl = core.createAbility();
         }

         this.unlockedToEquippedMap.put(core, abl);
      }

   }

   protected void drawGradientRect(GuiGraphics graphics, int left, int top, int right, int bottom, int color1, int color2) {
   }

   public boolean m_6348_(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      return true;
   }

   protected int getContentHeight() {
      int size = ((List)this.allAbilities.stream().collect(Collectors.toList())).size();
      return (int)((float)size * this.getEntrySpacing() + 2.0F);
   }

   public float getEntrySpacing() {
      return 20.0F * this.compactness.getSpacing();
   }

   public void setCompactness(AbilitiesListScreen.Compactness compactness) {
      this.compactness = compactness;
   }

   protected int getScrollAmount() {
      return 15;
   }

   protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
      float y = (float)relativeY;
      float x = (float)(this.parent.f_96543_ / 2 - 100 + 40);

      for(AbilityCore<IAbility> core : this.unlockedAbilities) {
         if (core != null) {
            IAbility abl = (IAbility)this.unlockedToEquippedMap.get(core);
            int color = 16777215;
            boolean isHovering = (float)mouseX >= x && (float)mouseY >= y - 2.0F && (float)mouseX < x + (float)this.width - 50.0F && (float)mouseY < y + this.getEntrySpacing() - 2.0F;
            if (this.props.hasEquippedAbility(core)) {
               color = 16711680;
            } else if (isHovering) {
               color = 10014975;
            }

            RenderSystem.enableBlend();
            graphics.m_280168_().m_85836_();
            graphics.m_280168_().m_252880_(x - 34.0F + (float)this.compactness.getSlotOffsetX(), y - (float)this.compactness.getSlotOffsetY(), 0.0F);
            graphics.m_280168_().m_85841_(this.compactness.getSlotScale(), this.compactness.getSlotScale(), 1.0F);
            graphics.m_280218_(ModResources.WIDGETS, 0, 0, 0, 0, 23, 23);
            graphics.m_280168_().m_85849_();
            RendererHelper.drawStringWithBorder(this.mc.f_91062_, graphics, abl.getDisplayName(), (int)x, (int)y + 4, color);
            RendererHelper.drawIcon(abl.getIcon(this.player), graphics.m_280168_(), (float)(Mth.m_14143_(x) - 30 + this.compactness.getIconOffsetX()), (float)(Mth.m_14143_(y) + this.compactness.getIconOffsetY()), 1.0F, (float)this.compactness.getIconSize(), (float)this.compactness.getIconSize());
            abl.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).ifPresent((comp) -> {
               if (comp.isDisabled()) {
                  RendererHelper.drawIcon(ModResources.DISABLED_ABILITY, graphics.m_280168_(), (float)(Mth.m_14143_(x) - 30 + this.compactness.getIconOffsetX()), (float)(Mth.m_14143_(y) + this.compactness.getIconOffsetY()), 3.0F, (float)this.compactness.getIconSize(), (float)this.compactness.getIconSize(), 1.0F, 1.0F, 1.0F, 1.0F);
               }

            });
            y += this.getEntrySpacing();
         }
      }

      if (this.passiveAbilities.size() > 0) {
         graphics.m_280168_().m_252880_(0.0F, 0.0F, 250.0F);
         RendererHelper.drawStringWithBorder(this.mc.f_91062_, graphics, (Component)Component.m_237113_("Passives"), (int)x - 20, (int)y + 4, 9145227);
         graphics.m_280168_().m_252880_(0.0F, 0.0F, -250.0F);
         y += this.getEntrySpacing();

         for(IAbility abl : this.passiveAbilities) {
            if (abl != null) {
               int textColor = 11206519;
               Color iconColor = DEFAULT_ICON_COLOR;
               Optional<PauseTickComponent> pauseComp = abl.<PauseTickComponent>getComponent((AbilityComponentKey)ModAbilityComponents.PAUSE_TICK.get());
               if (pauseComp.isPresent() && ((PauseTickComponent)pauseComp.get()).isPaused()) {
                  textColor = PASSIVE_PAUSED_COLOR.getRGB();
                  iconColor = PASSIVE_PAUSED_COLOR;
               }

               RenderSystem.enableBlend();
               graphics.m_280168_().m_85836_();
               graphics.m_280168_().m_252880_(x - 34.0F + (float)this.compactness.getSlotOffsetX(), y - (float)this.compactness.getSlotOffsetY(), 0.0F);
               graphics.m_280168_().m_85841_(this.compactness.getSlotScale(), this.compactness.getSlotScale(), 1.0F);
               graphics.m_280218_(ModResources.WIDGETS, 0, 0, 0, 0, 23, 23);
               graphics.m_280168_().m_85849_();
               RendererHelper.drawStringWithBorder(this.mc.f_91062_, graphics, abl.getDisplayName(), (int)x, (int)y + 4, textColor);
               RendererHelper.drawIcon(abl.getIcon(this.player), graphics.m_280168_(), (float)(Mth.m_14143_(x) - 30 + this.compactness.getIconOffsetX()), (float)(Mth.m_14143_(y) + this.compactness.getIconOffsetY()), 1.0F, (float)this.compactness.getIconSize(), (float)this.compactness.getIconSize(), (float)iconColor.getRed() / 255.0F, (float)iconColor.getGreen() / 255.0F, (float)iconColor.getBlue() / 255.0F, 1.0F);
               abl.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).ifPresent((comp) -> {
                  if (comp.isDisabled()) {
                     RendererHelper.drawIcon(ModResources.DISABLED_ABILITY, graphics.m_280168_(), (float)(Mth.m_14143_(x) - 30), (float)Mth.m_14143_(y), 3.0F, 16.0F, 16.0F, 1.0F, 1.0F, 1.0F, 1.0F);
                  }

               });
               y += this.getEntrySpacing();
            }
         }
      }

      graphics.m_280262_();
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return NarrationPriority.NONE;
   }

   public void m_142291_(NarrationElementOutput p_169152_) {
   }

   private Entry<IAbility> findAbilityEntry(int mouseX, int mouseY) {
      double offset = (double)((float)(mouseY - this.top) + this.scrollDistance - 2.0F);
      if (offset <= (double)0.0F) {
         return null;
      } else {
         int lineIdx = (int)(offset / (double)this.getEntrySpacing());
         if (lineIdx >= this.allAbilities.size()) {
            return null;
         } else {
            Entry<IAbility> entry = (Entry)this.allAbilities.get(lineIdx);
            return entry != null ? entry : null;
         }
      }
   }

   public boolean m_6375_(double mouseX, double mouseY, int button) {
      Entry<IAbility> entry = this.findAbilityEntry((int)mouseX, (int)mouseY);
      boolean flag = true;
      if (entry != null && this.m_5953_(mouseX, mouseY) && button == 0) {
         if (entry.isActive()) {
            if (this.parent.getSelectionMode() == AbilitiesListScreen.Selection.DRAG_AND_DROP) {
               this.parent.setDraggedAbility(entry.abilityCore);
            } else if (this.parent.getSelectionMode() == AbilitiesListScreen.Selection.KEYBIND && this.parent.slotSelected >= 0 && flag && entry.getActive() != null) {
               ModNetwork.sendToServer(new CEquipAbilityPacket(this.parent.slotSelected, entry.getActive()));
               this.parent.isDirty = true;
            }
         } else if (entry.isPassive() && entry.getPassive() != null) {
            ModNetwork.sendToServer(new CTogglePassiveAbilityPacket(entry.getPassive().getCore()));
         }
      }

      if (WyDebug.isDebug() && entry != null && button == 1) {
         IAbility abl = null;
         if (entry.isActive()) {
            abl = (IAbility)this.unlockedToEquippedMap.get(entry.getActive());
         } else if (entry.isPassive()) {
            for(IAbility passive : this.passiveAbilities) {
               if (passive.equals(entry.getPassive())) {
                  abl = entry.getPassive();
                  break;
               }
            }
         }

         if (abl != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("§5" + abl.getDisplayName().getString() + "§r\n");

            for(AbilityComponent<?> comp : abl.getComponents().values()) {
               if (comp.getBonusManagers().hasNext()) {
                  comp.getBonusManagers().forEachRemaining((m) -> {
                     if (!m.getBonuses().isEmpty()) {
                        sb.append("§6" + String.valueOf(comp.getKey().getId()) + "§r\n");
                     }
                  });
                  comp.getBonusManagers().forEachRemaining((m) -> m.getBonuses().forEach((e) -> {
                        sb.append("  §2" + String.valueOf(e.getKey()) + "§r\n");
                        sb.append("  " + ((BonusManager.BonusValue)e.getValue()).toString() + "\n\n");
                     }));
               }
            }

            this.mc.f_91074_.m_5661_(Component.m_237113_(sb.toString()), false);
         }
      }

      return super.m_6375_(mouseX, mouseY, button);
   }

   public boolean m_6050_(double mouseX, double mouseY, double scroll) {
      return this.getContentHeight() < this.height ? false : super.m_6050_(mouseX, mouseY, scroll);
   }

   public boolean m_7979_(double mouseX, double mouseY, int button, double dragX, double dragY) {
      return super.m_7979_(mouseX, mouseY, button, dragX, dragY);
   }

   public boolean m_5953_(double mouseX, double mouseY) {
      return mouseX >= (double)this.left && mouseY >= (double)this.top && mouseX < (double)(this.left + this.width - 5) && mouseY < (double)(this.top + this.height);
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
      this.hoveredEntry = null;
      if (this.m_5953_((double)mouseX, (double)mouseY)) {
         this.hoveredEntry = this.findAbilityEntry(mouseX, mouseY);
         if (this.hoveredEntry == null) {
            return;
         }

         IAbility abl = null;
         if (this.hoveredEntry.isActive()) {
            abl = (IAbility)this.unlockedToEquippedMap.get(this.hoveredEntry.getActive());
         } else if (this.hoveredEntry.isPassive()) {
            abl = this.hoveredEntry.getPassive();
         }

         if (abl != null && !this.parent.hasDraggedAbility() && AbilitiesListScreen.canShowTooltips()) {
            AbilitiesListScreen.renderAbilityTooltip(graphics, mouseX, mouseY, abl);
         }
      }

   }

   public @Nullable AbilityCore<?> getHoveredEntry() {
      return this.hoveredEntry != null ? this.hoveredEntry.getActive() : null;
   }

   class Entry<T extends IAbility> {
      private @Nullable T ability;
      private @Nullable AbilityCore<T> abilityCore;

      public Entry() {
      }

      public Entry(T ability) {
         this.ability = ability;
      }

      public Entry(AbilityCore<T> abilityCore) {
         this.abilityCore = abilityCore;
      }

      public AbilityCore<T> getActive() {
         return this.abilityCore;
      }

      public boolean isActive() {
         return this.abilityCore != null;
      }

      public T getPassive() {
         return this.ability;
      }

      public boolean isPassive() {
         return this.ability != null;
      }
   }
}
