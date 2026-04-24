package xyz.pixelatedw.mineminenomi.api.crew;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.ImageIO;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

public class JollyRoger {
   private static final Logger LOGGER = LogUtils.getLogger();
   private JollyRogerElement base;
   private JollyRogerElement[] backgrounds = new JollyRogerElement[3];
   private JollyRogerElement[] details = new JollyRogerElement[6];
   private volatile BufferedImage cachedImage = null;
   private final AtomicBoolean loadingDispatched = new AtomicBoolean(false);

   public JollyRoger() {
   }

   public CompoundTag write() {
      CompoundTag nbt = new CompoundTag();

      try {
         if (this.getBase() != null) {
            CompoundTag baseElementNBT = new CompoundTag();
            if (this.getBase().getRegistryKey() != null) {
               baseElementNBT.putString("id", this.getBase().getRegistryKey().toString());
            }

            this.getBase().write(baseElementNBT);
            nbt.put("base", baseElementNBT);
         } else {
            nbt.put("base", new CompoundTag());
         }

         ListTag backgrounds = new ListTag();

         for(int i = 0; i < this.getBackgrounds().length; ++i) {
            JollyRogerElement bg = this.getBackgrounds()[i];
            if (bg != null) {
               CompoundTag bgNBT = new CompoundTag();
               bgNBT.putInt("slot", i);
               if (bg.getRegistryKey() != null) {
                  bgNBT.putString("id", bg.getRegistryKey().toString());
               }

               bg.write(bgNBT);
               backgrounds.add(bgNBT);
            }
         }

         nbt.put("backgrounds", backgrounds);
         ListTag details = new ListTag();

         for(int i = 0; i < this.getDetails().length; ++i) {
            JollyRogerElement detail = this.getDetails()[i];
            if (detail != null) {
               CompoundTag detNBT = new CompoundTag();
               detNBT.putInt("slot", i);
               if (detail.getRegistryKey() != null) {
                  detNBT.putString("id", detail.getRegistryKey().toString());
               }

               detail.write(detNBT);
               details.add(detNBT);
            }
         }

         nbt.put("details", details);
      } catch (Exception ex) {
         ex.printStackTrace();
      }

      return nbt;
   }

   public void read(CompoundTag nbt) {
      try {
         CompoundTag baseNBT = nbt.getCompound("base");
         if (baseNBT.contains("id")) {
            ResourceLocation id = ResourceLocation.parse(baseNBT.getString("id"));
            if (id != null) {
               JollyRogerElement baseElement = JollyRogerElement.of(id);
               if (baseElement != null) {
                   baseElement = baseElement.copy();
                  baseElement.read(baseNBT);
                  this.setBase(baseElement);
               }
            } else {
               this.setBase((JollyRogerElement)null);
            }
         } else {
            this.setBase((JollyRogerElement)null);
         }

         ListTag backgroundsNBT = nbt.getList("backgrounds", 10);

         for(int i = 0; i < backgroundsNBT.size(); ++i) {
            CompoundTag backgroundNBT = backgroundsNBT.getCompound(i);
            int slot = backgroundNBT.getInt("slot");
            if (backgroundNBT.contains("id")) {
               ResourceLocation id = ResourceLocation.parse(backgroundNBT.getString("id"));
               if (id != null) {
                  JollyRogerElement bgElement = JollyRogerElement.of(id);
                  if (bgElement != null) {
                      bgElement = bgElement.copy();
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

         ListTag detailsNBT = nbt.getList("details", 10);

         for(int i = 0; i < detailsNBT.size(); ++i) {
            CompoundTag detailNBT = detailsNBT.getCompound(i);
            int slot = detailNBT.getInt("slot");
            if (detailNBT.contains("id")) {
               ResourceLocation id = ResourceLocation.parse(detailNBT.getString("id"));
               if (id != null) {
                  JollyRogerElement detailElement = JollyRogerElement.of(id);
                  if (detailElement != null) {
                      detailElement = detailElement.copy();
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
      if (this.cachedImage != null) {
         return Optional.of(this.cachedImage);
      }

      if (this.loadingDispatched.compareAndSet(false, true)) {
         xyz.pixelatedw.mineminenomi.api.util.ModExecutors.VIRTUAL_THREAD_EXECUTOR.submit(() -> {
            try {
               BufferedImage jollyRogerImage = new BufferedImage(128, 128, 2);

               for(JollyRogerElement backgroundElement : this.backgrounds) {
                  if (backgroundElement != null) {
                     BufferedImage backgroundElementImage = this.elementToImage(backgroundElement);
                     jollyRogerImage.getGraphics().drawImage(backgroundElementImage, 0, 0, (ImageObserver)null);
                  }
               }

               if(this.getBase() != null) {
                   BufferedImage jollyRogerBase = this.elementToImage(this.getBase());
                   jollyRogerImage.getGraphics().drawImage(jollyRogerBase, 0, 0, (ImageObserver)null);
               }

               for(JollyRogerElement detailElement : this.details) {
                  if (detailElement != null) {
                     BufferedImage detailElementImage = this.elementToImage(detailElement);
                     jollyRogerImage.getGraphics().drawImage(detailElementImage, 0, 0, (ImageObserver)null);
                  }
               }

               this.cachedImage = jollyRogerImage;
            } catch (IOException e) {
               LOGGER.error(e.getMessage());
               this.loadingDispatched.set(false);
            }
         });
      }

      return Optional.empty();
   }

   private BufferedImage elementToImage(JollyRogerElement element) throws IOException {
      String assetPath = "assets/mineminenomi/";
      BufferedImage elementImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(assetPath + element.getTexture().getPath()));
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
   public void render(net.minecraft.client.gui.GuiGraphics matrixStack, int x, int y, float scale) {
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
   public void drawElement(JollyRogerElement element, net.minecraft.client.gui.GuiGraphics matrixStack, int x, int y, float scale, boolean color) {
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

      rect.draw(matrixStack.pose(), (float)x, (float)y);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
