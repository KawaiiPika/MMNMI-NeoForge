package xyz.pixelatedw.mineminenomi.api.crew;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.ModMain;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;

public class JollyRoger {
   public static final int BACKGROUND_SLOTS = 2;
   public static final int DETAIL_SLOTS = 5;
   private JollyRogerElement base;
   private JollyRogerElement[] backgrounds = new JollyRogerElement[2];
   private JollyRogerElement[] details = new JollyRogerElement[5];

   public CompoundTag write() {
      CompoundTag nbt = new CompoundTag();
      if (this.hasNoElements()) {
         this.setBase((JollyRogerElement)ModJollyRogers.BASE_SKULL.get());
      }

      try {
         JollyRogerElement baseElement = this.getBase();
         CompoundTag baseNBT = new CompoundTag();
         if (baseElement != null) {
            baseNBT.m_128359_("id", baseElement.getRegistryKey().toString());
            baseElement.write(baseNBT);
         }

         nbt.m_128365_("base", baseNBT);
         ListTag backgrounds = new ListTag();

         for(int i = 0; i < this.getBackgrounds().length; ++i) {
            JollyRogerElement bgElement = this.getBackgrounds()[i];
            if (bgElement != null && bgElement.getTexture() != null) {
               CompoundTag backgroundNBT = new CompoundTag();
               backgroundNBT.m_128405_("slot", i);
               backgroundNBT.m_128359_("id", bgElement.getRegistryKey().toString());
               bgElement.write(backgroundNBT);
               backgrounds.add(backgroundNBT);
            } else {
               CompoundTag backgroundNBT = new CompoundTag();
               backgroundNBT.m_128405_("slot", i);
               backgrounds.add(backgroundNBT);
            }
         }

         nbt.m_128365_("backgrounds", backgrounds);
         ListTag details = new ListTag();

         for(int i = 0; i < this.getDetails().length; ++i) {
            JollyRogerElement detailElement = this.getDetails()[i];
            if (detailElement != null && detailElement.getTexture() != null) {
               CompoundTag detailNBT = new CompoundTag();
               detailNBT.m_128405_("slot", i);
               detailNBT.m_128359_("id", detailElement.getRegistryKey().toString());
               detailElement.write(detailNBT);
               details.add(detailNBT);
            } else {
               CompoundTag detailNBT = new CompoundTag();
               detailNBT.m_128405_("slot", i);
               details.add(detailNBT);
            }
         }

         nbt.m_128365_("details", details);
      } catch (Exception ex) {
         ex.printStackTrace();
      }

      return nbt;
   }

   public void read(CompoundTag nbt) {
      try {
         CompoundTag baseNBT = nbt.m_128469_("base");
         if (baseNBT.m_128441_("id")) {
            ResourceLocation id = ResourceLocation.parse(baseNBT.m_128461_("id"));
            if (id != null) {
               JollyRogerElement baseElement = JollyRogerElement.of(id).copy();
               if (baseElement != null) {
                  baseElement.read(baseNBT);
                  this.setBase(baseElement);
               }
            } else {
               this.setBase((JollyRogerElement)null);
            }
         } else {
            this.setBase((JollyRogerElement)null);
         }

         ListTag backgroundsNBT = nbt.m_128437_("backgrounds", 10);

         for(int i = 0; i < backgroundsNBT.size(); ++i) {
            CompoundTag backgroundNBT = backgroundsNBT.m_128728_(i);
            int slot = backgroundNBT.m_128451_("slot");
            if (backgroundNBT.m_128441_("id")) {
               ResourceLocation id = ResourceLocation.parse(backgroundNBT.m_128461_("id"));
               if (id != null) {
                  JollyRogerElement bgElement = JollyRogerElement.of(id).copy();
                  if (bgElement != null) {
                     bgElement.read(backgroundNBT);
                     this.setBackground(slot, bgElement);
                  }
               } else {
                  this.setBackground(slot, (JollyRogerElement)null);
               }
            } else {
               this.setBackground(slot, (JollyRogerElement)null);
            }
         }

         ListTag detailsNBT = nbt.m_128437_("details", 10);

         for(int i = 0; i < detailsNBT.size(); ++i) {
            CompoundTag detailNBT = detailsNBT.m_128728_(i);
            int slot = detailNBT.m_128451_("slot");
            if (detailNBT.m_128441_("id")) {
               ResourceLocation id = ResourceLocation.parse(detailNBT.m_128461_("id"));
               if (id != null) {
                  JollyRogerElement detailElement = JollyRogerElement.of(id).copy();
                  if (detailElement != null) {
                     detailElement.read(detailNBT);
                     this.setDetail(slot, detailElement);
                  }
               } else {
                  this.setDetail(slot, (JollyRogerElement)null);
               }
            } else {
               this.setDetail(slot, (JollyRogerElement)null);
            }
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }

   }

   @Nullable
   public JollyRogerElement getBase() {
      if (this.base == null) {
         this.base = (JollyRogerElement)ModJollyRogers.BASE_SKULL.get();
      }

      return this.base;
   }

   public void setBase(JollyRogerElement base) {
      this.base = base;
   }

   public JollyRogerElement[] getBackgrounds() {
      return this.backgrounds;
   }

   public void setBackgrounds(JollyRogerElement[] elements) {
      this.backgrounds = elements;
   }

   public boolean addBackground(JollyRogerElement bg) {
      for(int i = 0; i < this.backgrounds.length; ++i) {
         JollyRogerElement background = this.backgrounds[i];
         if (background == null) {
            this.backgrounds[i] = bg;
            return true;
         }
      }

      return false;
   }

   public boolean setBackground(int slot, JollyRogerElement check) {
      if (!this.hasBackground(check) && slot <= 2) {
         this.backgrounds[slot] = check;
         return true;
      } else {
         return false;
      }
   }

   public boolean hasBackground(JollyRogerElement check) {
      if (check == null) {
         return false;
      } else {
         int uses = 0;

         for(JollyRogerElement elem : this.backgrounds) {
            if (elem != null && elem.equals(check)) {
               ++uses;
            }

            if (uses >= check.getUseLimit()) {
               return true;
            }
         }

         return false;
      }
   }

   public JollyRogerElement[] getDetails() {
      return this.details;
   }

   public void setDetails(JollyRogerElement[] elements) {
      this.details = elements;
   }

   public boolean addDetail(JollyRogerElement det) {
      for(int i = 0; i < this.details.length; ++i) {
         JollyRogerElement detail = this.details[i];
         if (detail == null) {
            this.details[i] = det;
            return true;
         }
      }

      return false;
   }

   public boolean setDetail(int slot, JollyRogerElement check) {
      if (!this.hasDetail(check) && slot <= 5) {
         this.details[slot] = check;
         return true;
      } else {
         return false;
      }
   }

   public boolean hasDetail(JollyRogerElement check) {
      if (check == null) {
         return false;
      } else {
         int uses = 0;

         for(JollyRogerElement elem : this.details) {
            if (elem != null && elem.equals(check)) {
               ++uses;
            }

            if (uses >= check.getUseLimit()) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasNoElements() {
      boolean missingBase = this.getBase() == null;
      boolean missingBackgrounds = true;

      for(JollyRogerElement elem : this.getBackgrounds()) {
         if (elem != null) {
            missingBackgrounds = false;
            break;
         }
      }

      boolean missingDetails = true;

      for(JollyRogerElement elem : this.getDetails()) {
         if (elem != null) {
            missingDetails = false;
            break;
         }
      }

      return missingBase && missingBackgrounds && missingDetails;
   }

   public Optional<BufferedImage> getAsBufferedImage() {
      try {
         BufferedImage jollyRogerImage = new BufferedImage(128, 128, 2);

         for(JollyRogerElement backgroundElement : this.backgrounds) {
            if (backgroundElement != null) {
               BufferedImage backgroundElementImage = this.elementToImage(backgroundElement);
               jollyRogerImage.getGraphics().drawImage(backgroundElementImage, 0, 0, (ImageObserver)null);
            }
         }

         BufferedImage jollyRogerBase = this.elementToImage(this.getBase());
         jollyRogerImage.getGraphics().drawImage(jollyRogerBase, 0, 0, (ImageObserver)null);

         for(JollyRogerElement detailElement : this.details) {
            if (detailElement != null) {
               BufferedImage detailElementImage = this.elementToImage(detailElement);
               jollyRogerImage.getGraphics().drawImage(detailElementImage, 0, 0, (ImageObserver)null);
            }
         }

         return Optional.of(jollyRogerImage);
      } catch (IOException e) {
         ModMain.LOGGER.error(e.getMessage());
         return Optional.empty();
      }
   }

   private BufferedImage elementToImage(JollyRogerElement element) throws IOException {
      String assetPath = "assets/mineminenomi/";
      BufferedImage elementImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(assetPath + element.getTexture().m_135815_()));
      if (element.canBeColored()) {
         elementImage = this.applyColorToImage(element.getColor(), elementImage);
      }

      return elementImage;
   }

   private BufferedImage applyColorToImage(Color color, BufferedImage image) {
      for(int x = 0; x < image.getWidth(); ++x) {
         for(int y = 0; y < image.getHeight(); ++y) {
            int rgba = image.getRGB(x, y);
            Color pixelColor = new Color(rgba, true);
            if (pixelColor.getAlpha() != 0 && (rgba & 16777215) != 0) {
               Integer tintedPixel = tintABGRPixel(pixelColor.getRGB(), color);
               image.setRGB(x, y, tintedPixel);
            }
         }
      }

      return image;
   }

   public static Integer tintABGRPixel(int pixelColor, Color tintColor) {
      int x = pixelColor >> 16 & 255;
      int y = pixelColor >> 8 & 255;
      int z = pixelColor & 255;
      int top = 2126 * x + 7252 * y + 722 * z;
      int Btemp = (int)((long)(tintColor.getBlue() * top) * 1766117501L >> 52);
      int Gtemp = (int)((long)(tintColor.getGreen() * top) * 1766117501L >> 52);
      int Rtemp = (int)((long)(tintColor.getRed() * top) * 1766117501L >> 52);
      return (pixelColor >> 24 & 255) << 24 | Btemp & 255 | (Gtemp & 255) << 8 | (Rtemp & 255) << 16;
   }

   @OnlyIn(Dist.CLIENT)
   public void render(PoseStack matrixStack, int x, int y, float scale) {
      for(JollyRogerElement element : this.getBackgrounds()) {
         if (element != null && element.getTexture() != null) {
            this.drawElement(element, matrixStack, x, y, scale, true);
         }
      }

      if (this.getBase() != null && this.getBase().getTexture() != null) {
         this.drawElement(this.getBase(), matrixStack, x, y, scale, true);
      }

      for(JollyRogerElement element : this.getDetails()) {
         if (element != null && element.getTexture() != null) {
            this.drawElement(element, matrixStack, x, y, scale, true);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void drawElement(JollyRogerElement element, PoseStack matrixStack, int x, int y, float scale, boolean color) {
      TexturedRectUI rect = element.getTexturedRect();
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      if (element.canBeColored()) {
         red = element.getRedF();
         green = element.getGreenF();
         blue = element.getBlueF();
      }

      rect.setFlipX(element.isFlippedVertically());
      rect.setFlipY(element.isFlippedHorizontally());
      rect.setScale(scale);
      if (color) {
         RenderSystem.setShaderColor(red, green, blue, 1.0F);
      }

      rect.draw(matrixStack, (float)x, (float)y);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
