package xyz.pixelatedw.mineminenomi.api.ui;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class SequencedString {
   private String message;
   private int maxLength;
   private int color;
   private char[] chars;
   private int maxTicks;
   private int tickCount;
   private int delayTicks;
   private boolean isComplete;

   public SequencedString(String str, int maxLength, int maxTicks) {
      this(str, maxLength, maxTicks, maxTicks + 100);
   }

   public SequencedString(String str, int maxLength, int maxTicks, int delay) {
      this.color = Color.WHITE.getRGB();
      this.delayTicks = this.maxTicks;
      this.setMessage(str);
      this.maxLength = maxLength;
      this.maxTicks = maxTicks;
      this.tickCount = 0;
      this.delayTicks = delay;
   }

   public void render(GuiGraphics graphics, Font font, int posX, int posY, float partialTicks) {
      String tempStr = "";
      if (!this.isComplete) {
         for(int i = 0; i < this.chars.length; ++i) {
            if (this.tickCount >= this.calculateTicksNeeded(i) && (float)this.tickCount * partialTicks < (float)this.delayTicks) {
               tempStr = tempStr + this.chars[i];
            }
         }

         if (tempStr.equals(this.message)) {
            this.isComplete = true;
         }
      } else {
         tempStr = this.message;
      }

      List<FormattedCharSequence> strings = font.m_92923_(Component.m_237113_(tempStr), this.maxLength);

      for(int b = 0; b < strings.size(); ++b) {
         RendererHelper.drawStringWithBorder(font, graphics, (FormattedCharSequence)strings.get(b), posX, posY + 10 * b, this.color);
      }

      ++this.tickCount;
   }

   public int calculateTicksNeeded(int index) {
      int oldRange = this.message.length();
      int newRange = this.maxTicks;
      int newValue = index * newRange / oldRange;
      return newValue;
   }

   public void setMessage(Component message) {
      this.setMessage(message.getString());
   }

   public void setMessage(String message) {
      this.message = message;
      this.chars = new char[this.message.length()];

      for(int i = 0; i < this.message.length(); ++i) {
         this.chars[i] = this.message.charAt(i);
      }

   }

   public void setMaxLength(int len) {
      this.maxLength = len;
   }

   public void setMaxTicks(int ticks) {
      this.maxTicks = ticks;
   }

   public void setDelayTicks(int ticks) {
      this.delayTicks = ticks;
   }

   public void resetCounter() {
      this.tickCount = 0;
      this.isComplete = false;
   }

   public int getTickCount() {
      return this.tickCount;
   }

   public int getDelayTicks() {
      return this.delayTicks;
   }
}
