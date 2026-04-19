package xyz.pixelatedw.mineminenomi.api.crew;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class JollyRogerElement {
   private ResourceLocation key;
   private Component localizedName;
   private ResourceLocation texture;
   private boolean canBeColored;
   private boolean canBeFlippedVertically;
   private boolean canBeFlippedHorizontally;
   private int red;
   private int green;
   private int blue;
   private int color;
   private float inversedRed;
   private float inversedGreen;
   private float inversedBlue;
   private Color fullColor;
   private boolean isFlippedVertically;
   private boolean isFlippedHorizontally;
   private int useLimit;
   private List<ICanUse> canUseChecks;
   private final LayerType type;
   private TexturedRectUI rect;

   public JollyRogerElement(LayerType type) {
      this.localizedName = ModI18n.GUI_NO_NAME;
      this.canBeColored = false;
      this.canBeFlippedVertically = false;
      this.canBeFlippedHorizontally = false;
      this.red = 255;
      this.green = 255;
      this.blue = 255;
      this.color = 16777215;
      this.fullColor = new Color(this.color);
      this.isFlippedVertically = false;
      this.isFlippedHorizontally = false;
      this.useLimit = 1;
      this.canUseChecks = new ArrayList();
      this.type = type;
   }

   public JollyRogerElement(LayerType type, ResourceLocation texture) {
      this.localizedName = ModI18n.GUI_NO_NAME;
      this.canBeColored = false;
      this.canBeFlippedVertically = false;
      this.canBeFlippedHorizontally = false;
      this.red = 255;
      this.green = 255;
      this.blue = 255;
      this.color = 16777215;
      this.fullColor = new Color(this.color);
      this.isFlippedVertically = false;
      this.isFlippedHorizontally = false;
      this.useLimit = 1;
      this.canUseChecks = new ArrayList();
      this.type = type;
      this.setTexture(texture);
   }

   public static JollyRogerElement of(ResourceLocation res) {
      return (JollyRogerElement)((IForgeRegistry)WyRegistry.JOLLY_ROGER_ELEMENTS.get()).getValue(res);
   }

   public boolean canBeColored() {
      return this.canBeColored;
   }

   public JollyRogerElement setCanBeColored() {
      this.canBeColored = true;
      return this;
   }

   public boolean canBeFlippedVertically() {
      return this.canBeFlippedVertically;
   }

   public boolean canBeFlippedHorizontally() {
      return this.canBeFlippedHorizontally;
   }

   public JollyRogerElement setCanBeFlipped(boolean vertical, boolean horizontal) {
      this.canBeFlippedVertically = vertical;
      this.canBeFlippedHorizontally = horizontal;
      return this;
   }

   public int getUseLimit() {
      return this.useLimit;
   }

   public JollyRogerElement setUseLimit(int limit) {
      this.useLimit = limit;
      return this;
   }

   public Color getFullColor() {
      return this.fullColor;
   }

   public int getRed() {
      return this.red;
   }

   public float getRedF() {
      return (float)this.red / 255.0F;
   }

   public int getGreen() {
      return this.green;
   }

   public float getGreenF() {
      return (float)this.green / 255.0F;
   }

   public int getBlue() {
      return this.blue;
   }

   public float getBlueF() {
      return (float)this.blue / 255.0F;
   }

   public Color getColor() {
      return this.fullColor;
   }

   public int getColorI() {
      return this.color;
   }

   public float getInversedRedF() {
      return this.inversedRed;
   }

   public float getInversedGreenF() {
      return this.inversedGreen;
   }

   public float getInversedBlueF() {
      return this.inversedBlue;
   }

   public JollyRogerElement setColor(int color) {
      int val = -16777216 | color;
      int r = val >> 16 & 255;
      int g = val >> 8 & 255;
      int b = val >> 0 & 255;
      this.setColor(r, g, b);
      return this;
   }

   public JollyRogerElement setColor(int red, int green, int blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.color = WyHelper.rgbToInt(red, green, blue, 255);
      this.fullColor = new Color(this.color);
      this.inversedRed = (float)(255 - this.red) / 255.0F;
      this.inversedGreen = (float)(255 - this.green) / 255.0F;
      this.inversedBlue = (float)(255 - this.blue) / 255.0F;
      return this;
   }

   public boolean isFlippedVertically() {
      return this.isFlippedVertically;
   }

   public JollyRogerElement setFlipVertically(boolean flag) {
      this.isFlippedVertically = flag;
      return this;
   }

   public boolean isFlippedHorizontally() {
      return this.isFlippedHorizontally;
   }

   public JollyRogerElement setFlipHorizontally(boolean flag) {
      this.isFlippedHorizontally = flag;
      return this;
   }

   public TexturedRectUI getTexturedRect() {
      if (this.rect == null || !this.rect.getTexture().equals(this.texture)) {
         this.rect = new TexturedRectUI(this.texture);
      }

      return this.rect;
   }

   public ResourceLocation getTexture() {
      return this.texture;
   }

   public JollyRogerElement setTexture(ResourceLocation texture) {
      this.texture = texture;
      return this;
   }

   public JollyRogerElement setLocalizedName(Component localizedName) {
      this.localizedName = localizedName;
      return this;
   }

   public JollyRogerElement addUseCheck(ICanUse check) {
      this.canUseChecks.add(check);
      return this;
   }

   public boolean canUse(Player player, Crew crew) {
      boolean flag = true;

      for(ICanUse check : this.canUseChecks) {
         if (check != null && !check.canUse(player, crew)) {
            flag = false;
            break;
         }
      }

      return flag;
   }

   public LayerType getLayerType() {
      return this.type;
   }

   public boolean equals(Object element) {
      if (element instanceof JollyRogerElement other) {
         return this.getTexture() != null && other.getTexture() != null ? this.getTexture().equals(other.getTexture()) : false;
      } else {
         return false;
      }
   }

   public @Nullable JollyRogerElement create() {
      try {
         return (JollyRogerElement)this.getClass().getConstructor().newInstance();
      } catch (Exception ex) {
         ex.printStackTrace();
         return null;
      }
   }

   public void write(CompoundTag nbt) {
      nbt.m_128405_("color", this.color);
      nbt.m_128379_("isFlippedVertically", this.isFlippedVertically);
      nbt.m_128379_("isFlippedHorizontally", this.isFlippedHorizontally);
   }

   public void read(CompoundTag nbt) {
      int color = nbt.m_128451_("color");
      this.setColor(color);
      this.isFlippedVertically = nbt.m_128471_("isFlippedVertically");
      this.isFlippedHorizontally = nbt.m_128471_("isFlippedHorizontally");
   }

   public @Nullable ResourceLocation getRegistryKey() {
      if (this.key == null) {
         for(Map.Entry<ResourceKey<JollyRogerElement>, JollyRogerElement> entry : ((IForgeRegistry)WyRegistry.JOLLY_ROGER_ELEMENTS.get()).getEntries()) {
            if (this.equals(entry.getValue())) {
               this.key = ((ResourceKey)entry.getKey()).m_135782_();
               break;
            }
         }
      }

      return this.key;
   }

   public Component getLocalizedName() {
      return this.localizedName;
   }

   public JollyRogerElement copy() {
      JollyRogerElement newElement = new JollyRogerElement(this.getLayerType(), this.getTexture());
      newElement.key = this.key;
      newElement.localizedName = this.localizedName;
      newElement.texture = this.texture;
      newElement.canBeColored = this.canBeColored;
      newElement.canBeFlippedVertically = this.canBeFlippedVertically;
      newElement.canBeFlippedHorizontally = this.canBeFlippedHorizontally;
      newElement.useLimit = this.useLimit;
      newElement.canUseChecks = this.canUseChecks;
      return newElement;
   }

   public static enum LayerType {
      BASE,
      BACKGROUND,
      DETAIL;

      // $FF: synthetic method
      private static LayerType[] $values() {
         return new LayerType[]{BASE, BACKGROUND, DETAIL};
      }
   }

   public interface ICanUse extends Serializable {
      boolean canUse(Player var1, Crew var2);
   }
}
