package xyz.pixelatedw.mineminenomi.api.abilities;

import java.awt.Color;
import java.util.Random;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class AbilityOverlay {
   private int id;
   private ResourceLocation texture = null;
   private RenderType renderType;
   private Color color;
   private OverlayPart overlayPart;

   private AbilityOverlay() {
      this.renderType = AbilityOverlay.RenderType.NORMAL;
      this.color = new Color(-1, true);
      this.overlayPart = AbilityOverlay.OverlayPart.BODY;
      this.id = (new Random()).nextInt();
   }

   private AbilityOverlay setTexture(ResourceLocation texture) {
      this.texture = texture;
      return this;
   }

   private AbilityOverlay setColor(Color color) {
      this.color = color;
      return this;
   }

   private AbilityOverlay setRenderType(RenderType type) {
      this.renderType = type;
      return this;
   }

   public ResourceLocation getTexture() {
      return this.texture;
   }

   public Color getColor() {
      return this.color;
   }

   public RenderType getRenderType() {
      return this.renderType;
   }

   private void setOverlayPart(OverlayPart part) {
      this.overlayPart = part;
   }

   public OverlayPart getOverlayPart() {
      return this.overlayPart;
   }

   public int hashCode() {
      int result = this.id;
      result = 31 * result + (this.texture == null ? 0 : this.texture.hashCode());
      result = 31 * result + this.color.hashCode();
      result = 31 * result + this.overlayPart.hashCode();
      result = 31 * result + this.renderType.hashCode();
      return result;
   }

   public static enum RenderType {
      NORMAL,
      ENERGY;

      private static RenderType[] $values() {
         return new RenderType[]{NORMAL, ENERGY};
      }
   }

   public static enum OverlayPart {
      BODY,
      LIMB,
      HEAD,
      ARM,
      LEG;

      private static OverlayPart[] $values() {
         return new OverlayPart[]{BODY, LIMB, HEAD, ARM, LEG};
      }
   }

   public static class Builder {
      private ResourceLocation texture = null;
      private RenderType renderType;
      private Color color;
      private OverlayPart overlayPart;

      public Builder() {
         this.renderType = AbilityOverlay.RenderType.NORMAL;
         this.color = new Color(-1, true);
         this.overlayPart = AbilityOverlay.OverlayPart.BODY;
      }

      public Builder setTexture(ResourceLocation texture) {
         this.texture = texture;
         return this;
      }

      public Builder setColor(int color) {
         this.color = new Color(color, true);
         return this;
      }

      public Builder setColor(Color color) {
         this.color = color;
         return this;
      }

      public Builder setColor(String hex) {
         this.color = WyHelper.hexToRGB(hex);
         return this;
      }

      public Builder setRenderType(RenderType type) {
         this.renderType = type;
         return this;
      }

      public Builder setOverlayPart(OverlayPart part) {
         this.overlayPart = part;
         return this;
      }

      public AbilityOverlay build() {
         AbilityOverlay overlay = new AbilityOverlay();
         overlay.setTexture(this.texture);
         overlay.setColor(this.color);
         overlay.setRenderType(this.renderType);
         overlay.setOverlayPart(this.overlayPart);
         return overlay;
      }
   }
}
