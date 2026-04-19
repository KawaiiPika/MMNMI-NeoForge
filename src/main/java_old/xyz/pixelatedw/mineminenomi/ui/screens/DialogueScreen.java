package xyz.pixelatedw.mineminenomi.ui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.ui.SequencedString;
import xyz.pixelatedw.mineminenomi.ui.widget.FactionButton;

public abstract class DialogueScreen extends Screen {
   private final LivingEntity entity;
   private Player player;
   private SequencedString message = new SequencedString("", 0, 0);
   private List<InteractionAction> interactions = new ArrayList();

   public DialogueScreen(LivingEntity entity) {
      super(Component.m_237119_());
      this.entity = entity;
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      this.message.render(graphics, this.getMinecraft().f_91062_, posX - 150, posY - 105, partialTicks);
      graphics.m_280168_().m_85836_();
      graphics.m_280246_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      InventoryScreen.renderEntityInInventoryFollowsAngle(graphics, posX + 150, posY + 150, 100, (float)Math.toRadians((double)40.0F), (float)Math.toRadians((double)5.0F), this.entity);
      RenderSystem.disableBlend();
      graphics.m_280168_().m_85849_();
      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   public void m_7856_() {
      this.player = this.f_96541_.f_91074_;
      this.interactions.clear();
      this.setupDialogue();
      this.generateButtons();
   }

   private void generateButtons() {
      this.m_169413_();
      int posX = this.f_96543_ / 2;
      int posY = this.f_96544_ / 2;
      int i = 0;

      for(InteractionAction interaction : this.interactions) {
         FactionButton interactionButton = new FactionButton(posX - 180, posY + 10 + i * 20, 100, 20, interaction.getTitle(), interaction.getEvent());
         interactionButton.m_257544_(interaction.getTooltip());
         if (interaction.isDisabled()) {
            interactionButton.f_93623_ = false;
         }

         this.m_142416_(interactionButton);
         ++i;
      }

   }

   public abstract void setupDialogue();

   public void addInteraction(InteractionAction event) {
      this.interactions.add(event);
   }

   public void setMessage(Component message) {
      this.message.setMessage(message);
      this.message.setMaxLength(250);
      int maxTicks = this.f_96547_.m_92852_(message) / 2;
      this.message.setMaxTicks(maxTicks);
      this.message.setDelayTicks(maxTicks + 100);
      this.message.resetCounter();
   }

   public Player getPlayer() {
      return this.player;
   }

   public LivingEntity getTarget() {
      return this.entity;
   }

   public static class InteractionAction {
      @Nullable
      private Tooltip tooltip;
      private Component title;
      private Button.OnPress event;
      private boolean active;

      public InteractionAction(Component title, Button.OnPress event) {
         this(title, event, (Tooltip)null);
      }

      public InteractionAction(Component title, Button.OnPress event, @Nullable Tooltip tooltip) {
         this.active = true;
         this.title = title;
         this.event = event;
         this.tooltip = tooltip;
      }

      public Component getTitle() {
         return this.title;
      }

      public Button.OnPress getEvent() {
         return this.event;
      }

      public void setTooltip(@Nullable Tooltip tooltip) {
         this.tooltip = tooltip;
      }

      @Nullable
      public Tooltip getTooltip() {
         return this.tooltip;
      }

      public void disable() {
         this.active = false;
      }

      public boolean isDisabled() {
         return !this.active;
      }
   }
}
