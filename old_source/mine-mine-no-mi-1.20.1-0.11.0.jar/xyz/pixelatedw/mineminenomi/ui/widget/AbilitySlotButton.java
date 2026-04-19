package xyz.pixelatedw.mineminenomi.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.ui.screens.AbilitiesListScreen;

public class AbilitySlotButton extends Button {
   private static final Component EMPTY = Component.m_237119_();
   private static final float FADE_IN_POWER = 0.15F;
   private static final float FADE_OUT_POWER = 0.1F;
   private final Minecraft mc;
   private final AbstractClientPlayer player;
   private float red = 1.0F;
   private float green = 1.0F;
   private float blue = 1.0F;
   private float[] hoverColor;
   private float[] pressedColor;
   private int slotIndex;
   private IAbility ability;
   private boolean isPressed;
   private TexturedRectUI rect;

   public AbilitySlotButton(IAbility ability, int posX, int posY, int width, int height, AbstractClientPlayer player, Button.OnPress onPress) {
      super(posX, posY, width, height, EMPTY, onPress, Button.f_252438_);
      this.rect = new TexturedRectUI(ModResources.WIDGETS);
      this.mc = Minecraft.m_91087_();
      this.player = player;
      this.ability = ability;
      this.setHoverColor(0.0F, 0.5F, 1.0F);
      this.setPressedColor(0.0F, 0.25F, 1.0F);
   }

   public int getSlotIndex() {
      return this.slotIndex;
   }

   public void setSlotIndex(int index) {
      this.slotIndex = index;
   }

   public void setHoverColor(float red, float green, float blue) {
      this.hoverColor = new float[]{red, green, blue};
   }

   public void setPressedColor(float red, float green, float blue) {
      this.pressedColor = new float[]{red, green, blue};
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      graphics.m_280168_().m_85836_();
      graphics.m_280168_().m_252880_(0.0F, 0.0F, 250.0F);
      if (this.f_93624_) {
         this.f_93622_ = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
         RenderSystem.enableBlend();
         if (this.f_93622_) {
            this.red -= 0.15F * partialTicks;
            this.green -= 0.15F * partialTicks;
            this.blue -= 0.15F * partialTicks;
            this.red = Mth.m_14036_(this.red, this.hoverColor[0], 1.0F);
            this.green = Mth.m_14036_(this.green, this.hoverColor[1], 1.0F);
            this.blue = Mth.m_14036_(this.blue, this.hoverColor[2], 1.0F);
         } else {
            this.red += 0.1F * partialTicks;
            this.green += 0.1F * partialTicks;
            this.blue += 0.1F * partialTicks;
            if (this.isPressed) {
               this.red = Mth.m_14036_(this.red, 0.0F, this.pressedColor[0]);
               this.green = Mth.m_14036_(this.green, 0.0F, this.pressedColor[1]);
               this.blue = Mth.m_14036_(this.blue, 0.0F, this.pressedColor[2]);
            } else {
               this.red = Mth.m_14036_(this.red, 0.0F, 1.0F);
               this.green = Mth.m_14036_(this.green, 0.0F, 1.0F);
               this.blue = Mth.m_14036_(this.blue, 0.0F, 1.0F);
            }
         }

         this.rect.setColor(this.red, this.green, this.blue, 1.0F);
         this.rect.setSize(23.0F, 23.0F);
         this.rect.draw(graphics.m_280168_(), (float)this.m_252754_(), (float)this.m_252907_());
         RenderSystem.disableBlend();
         if (this.getAbility() != null) {
            ResourceLocation res = this.getAbility().getIcon(this.player);
            RendererHelper.drawIcon(res, graphics.m_280168_(), (float)(this.m_252754_() + 4), (float)(this.m_252907_() + 4), 0.0F, 16.0F, 16.0F);
            if (this.ability != null && this.f_93622_ && !Screen.m_96639_()) {
               AbilitiesListScreen.renderAbilityTooltip(graphics, this.m_252754_(), this.m_252907_(), this.ability);
            }
         }
      }

      graphics.m_280168_().m_252880_(0.0F, 0.0F, -250.0F);
      graphics.m_280168_().m_85849_();
   }

   public void setIsPressed(boolean flag) {
      this.isPressed = flag;
   }

   public void setAbility(IAbility ability) {
      this.ability = ability;
   }

   public @Nullable IAbility getAbility() {
      return this.ability;
   }
}
