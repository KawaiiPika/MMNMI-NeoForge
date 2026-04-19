package xyz.pixelatedw.mineminenomi.api.config.options;

import me.shedaniel.math.Color;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ColorOption extends StringOption {
   public ColorOption(String defaultValue, String optionName, String optionDescription) {
      super(defaultValue, optionName, optionDescription);
   }

   public void saveColor(Color color) {
      this.getValue().set(WyHelper.rgbToHex(color.getRed(), color.getGreen(), color.getBlue()));
   }

   public Color getDefaultColor() {
      java.awt.Color c = WyHelper.hexToRGB((String)this.getDefault());
      return Color.ofRGB(c.getRed(), c.getGreen(), c.getBlue());
   }
}
