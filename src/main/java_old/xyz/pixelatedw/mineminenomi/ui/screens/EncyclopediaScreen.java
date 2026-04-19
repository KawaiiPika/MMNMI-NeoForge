package xyz.pixelatedw.mineminenomi.ui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.ui.widget.PlankButton;

public class EncyclopediaScreen extends Screen {
   private int currPage;
   private ItemStack bookStack;
   private List<DFEncyclopediaEntry> entries = Lists.newArrayList();

   protected EncyclopediaScreen(ItemStack stack) {
      super(Component.m_237119_());
      this.bookStack = stack;
      this.entries = this.getDefaultList(stack);
   }

   public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(graphics);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int posX = (this.f_96543_ - 192) / 2;
      int posY = 2;
      graphics.m_280218_(BookViewScreen.f_98252_, posX, posY, 0, 0, 192, 192);
      DFEncyclopediaEntry entry = !this.entries.isEmpty() ? (DFEncyclopediaEntry)this.entries.get(this.currPage) : null;
      Component currentPageLabel = Component.m_237110_("book.pageIndicator", new Object[]{this.currPage + 1, Math.max(this.getPageCount(), 1)});
      int textSize = this.f_96547_.m_92852_(currentPageLabel);
      RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (Component)currentPageLabel, posX - textSize + 192 - 44, 18, -1);
      graphics.m_280168_().m_85836_();
      ResourceLocation stemIcon = null;
      Color baseColor = entry != null ? (Color)entry.getBaseColor().orElse(Color.BLACK) : Color.BLACK;
      Color stemColor = entry != null ? (Color)entry.getStemColor().orElse(Color.BLACK) : Color.BLACK;
      ResourceLocation baseIcon;
      if (entry != null && entry.getShape().isPresent() && (Integer)entry.getShape().get() > 0) {
         baseIcon = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/items/fruits/generic/generic_fruit_" + String.valueOf(entry.getShape().get()) + ".png");
         stemIcon = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/items/fruits/generic/generic_fruit_" + String.valueOf(entry.getShape().get()) + "_stem.png");
      } else {
         baseIcon = ModResources.QUESTION_MARK;
      }

      graphics.m_280168_().m_252880_((float)(posX + 55), (float)(posY + 40), 2.0F);
      graphics.m_280168_().m_252880_(64.0F, 64.0F, 0.0F);
      graphics.m_280168_().m_85841_(0.8F, 0.8F, 0.8F);
      graphics.m_280168_().m_252880_(-64.0F, -64.0F, 1.0F);
      if (baseIcon.equals(ModResources.QUESTION_MARK)) {
         drawUpperIcon(baseIcon, graphics, 0, 0, 64, 32, (float)stemColor.getRed() / 255.0F, (float)stemColor.getGreen() / 255.0F, (float)stemColor.getBlue() / 255.0F, 1.0F);
         drawLowerIcon(baseIcon, graphics, 0, 32, 64, 32, (float)baseColor.getRed() / 255.0F, (float)baseColor.getGreen() / 255.0F, (float)baseColor.getBlue() / 255.0F, 1.0F);
      } else {
         RendererHelper.drawIcon(baseIcon, graphics.m_280168_(), 0.0F, 0.0F, 1.0F, 64.0F, 64.0F, (float)baseColor.getRed() / 255.0F, (float)baseColor.getGreen() / 255.0F, (float)baseColor.getBlue() / 255.0F, 1.0F);
      }

      if (stemIcon != null && entry != null && entry.getShape().isPresent() && (Integer)entry.getShape().get() > 0) {
         RendererHelper.drawIcon(stemIcon, graphics.m_280168_(), 0.0F, 0.0F, 1.0F, 64.0F, 64.0F, (float)stemColor.getRed() / 255.0F, (float)stemColor.getGreen() / 255.0F, (float)stemColor.getBlue() / 255.0F, 1.0F);
      }

      graphics.m_280168_().m_85849_();
      if (entry != null) {
         Component fruitName = entry.getDevilFruit().getDevilFruitName();
         int color = entry.isComplete() ? ChatFormatting.GOLD.m_126665_() : -1;
         List<FormattedCharSequence> splitText = this.f_96547_.m_92923_(fruitName, 110);

         for(int j = 0; j < splitText.size(); ++j) {
            RendererHelper.drawStringWithBorder(this.f_96547_, graphics, (FormattedCharSequence)splitText.get(j), posX + 93 - this.f_96547_.m_92724_((FormattedCharSequence)splitText.get(j)) / 2, posY + 113 + j * 12, color);
         }
      }

      super.m_88315_(graphics, mouseX, mouseY, partialTicks);
   }

   protected void m_7856_() {
      int posX = (this.f_96543_ - 192) / 2;
      int posY = 2;
      this.m_142416_(new PageButton(posX + 116, posY + 159, true, (button) -> this.nextPage(), true));
      this.m_142416_(new PageButton(posX + 43, posY + 159, false, (button) -> this.previousPage(), true));
      this.m_142416_(new PlankButton(this.f_96543_ / 2 - 150, 196, 98, 25, ModI18n.GUI_ALL, (button) -> {
         this.entries = this.getDefaultList(this.bookStack);
         this.currPage = 0;
      }));
      this.m_142416_(new PlankButton(this.f_96543_ / 2 - 50, 196, 98, 25, ModI18n.GUI_PARTIALLY_COMPLETE_LABEL, (button) -> {
         this.entries = (List)this.getDefaultList(this.bookStack).stream().filter((entry) -> entry.getCompletion() > 0).collect(Collectors.toList());
         this.currPage = 0;
      }));
      this.m_142416_(new PlankButton(this.f_96543_ / 2 + 50, 196, 98, 25, ModI18n.GUI_ONLY_COMPLETE_LABEL, (button) -> {
         this.entries = (List)this.getDefaultList(this.bookStack).stream().filter((entry) -> entry.getCompletion() >= 3).collect(Collectors.toList());
         this.currPage = 0;
      }));
      super.m_7856_();
   }

   private void previousPage() {
      if (this.currPage > 0) {
         --this.currPage;
      } else if (this.currPage == 0) {
         this.currPage = this.getPageCount() - 1;
      }

   }

   private void nextPage() {
      if (this.currPage < this.getPageCount() - 1) {
         ++this.currPage;
      } else if (this.currPage == this.getPageCount() - 1) {
         this.currPage = 0;
      }

   }

   private int getPageCount() {
      return this.entries.size();
   }

   public List<DFEncyclopediaEntry> getDefaultList(ItemStack stack) {
      List<DFEncyclopediaEntry> list = Lists.newArrayList();
      CompoundTag nbt = stack.m_41698_("unlocked");

      for(AkumaNoMiItem fruit : ModValues.DEVIL_FRUITS) {
         if (fruit.getRegistryKey() != null) {
            String key = fruit.getRegistryKey().toString();
            if (!nbt.m_128456_() && nbt.m_128441_(key)) {
               DFEncyclopediaEntry entry = DFEncyclopediaEntry.of(nbt.m_128469_(key));
               entry.setDevilFruit(fruit);
               list.add(entry);
            } else {
               DFEncyclopediaEntry entry = DFEncyclopediaEntry.of(Optional.empty(), Optional.empty(), Optional.empty());
               entry.setDevilFruit(fruit);
               list.add(entry);
            }
         }
      }

      Collections.reverse(list);
      return list;
   }

   public boolean m_7043_() {
      return false;
   }

   public static void drawUpperIcon(ResourceLocation rs, GuiGraphics graphics, int x, int y, int u, int v, float red, float green, float blue, float alpha) {
      Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
      RenderSystem.setShaderTexture(0, rs);
      RenderSystem.setShader(GameRenderer::m_172814_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85818_);
      bufferbuilder.m_252986_(matrix, (float)x, (float)(y + v), 1.0F).m_85950_(red, green, blue, alpha).m_7421_(0.0F, 0.5F).m_5752_();
      bufferbuilder.m_252986_(matrix, (float)(x + u), (float)(y + v), 1.0F).m_85950_(red, green, blue, alpha).m_7421_(1.0F, 0.5F).m_5752_();
      bufferbuilder.m_252986_(matrix, (float)(x + u), (float)y, 1.0F).m_85950_(red, green, blue, alpha).m_7421_(1.0F, 0.0F).m_5752_();
      bufferbuilder.m_252986_(matrix, (float)x, (float)y, 1.0F).m_85950_(red, green, blue, alpha).m_7421_(0.0F, 0.0F).m_5752_();
      Tesselator.m_85913_().m_85914_();
   }

   public static void drawLowerIcon(ResourceLocation rs, GuiGraphics graphics, int x, int y, int u, int v, float red, float green, float blue, float alpha) {
      Matrix4f matrix = graphics.m_280168_().m_85850_().m_252922_();
      RenderSystem.setShaderTexture(0, rs);
      RenderSystem.setShader(GameRenderer::m_172814_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85818_);
      bufferbuilder.m_252986_(matrix, (float)x, (float)(y + v), 1.0F).m_85950_(red, green, blue, alpha).m_7421_(0.0F, 1.0F).m_5752_();
      bufferbuilder.m_252986_(matrix, (float)(x + u), (float)(y + v), 1.0F).m_85950_(red, green, blue, alpha).m_7421_(1.0F, 1.0F).m_5752_();
      bufferbuilder.m_252986_(matrix, (float)(x + u), (float)y, 1.0F).m_85950_(red, green, blue, alpha).m_7421_(1.0F, 0.5F).m_5752_();
      bufferbuilder.m_252986_(matrix, (float)x, (float)y, 1.0F).m_85950_(red, green, blue, alpha).m_7421_(0.0F, 0.5F).m_5752_();
      Tesselator.m_85913_().m_85914_();
   }

   public static void open(ItemStack stack) {
      Minecraft.m_91087_().m_91152_(new EncyclopediaScreen(stack));
   }
}
