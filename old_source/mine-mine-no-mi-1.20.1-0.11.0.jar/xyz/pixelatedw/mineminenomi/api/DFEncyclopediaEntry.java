package xyz.pixelatedw.mineminenomi.api;

import java.awt.Color;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class DFEncyclopediaEntry {
   public static final String FRUIT_CLUES_TAG = "fruitClues";
   public static final String FRUIT_KEY_TAG = "fruitKey";
   public static final String BASE_SHAPE_TAG = "baseShape";
   public static final String BASE_COLOR_TAG = "baseColor";
   public static final String STEM_COLOR_TAG = "stemColor";
   public static final String ENCYCLOPEDIA_UNLOCKED_TAG = "unlocked";
   public static final String FRUIT_DECIPHERED_TAG = "deciphered";
   private final Optional<Color> baseColor;
   private final Optional<Color> stemColor;
   private final Optional<Integer> shape;
   private AkumaNoMiItem devilFruit;

   public static DFEncyclopediaEntry empty() {
      return new DFEncyclopediaEntry(Optional.empty(), Optional.empty(), Optional.empty());
   }

   public static DFEncyclopediaEntry of(Optional<Integer> type, Optional<Color> baseColor, Optional<Color> stemColor) {
      return new DFEncyclopediaEntry(type, baseColor, stemColor);
   }

   public static DFEncyclopediaEntry of(CompoundTag nbt) {
      Optional<Integer> shape = Optional.empty();
      int shapeVal = nbt.m_128451_("baseShape");
      if (shapeVal > 0) {
         shape = Optional.of(shapeVal);
      }

      Color baseVal = new Color(nbt.m_128451_("baseColor"));
      Color stemVal = new Color(nbt.m_128451_("stemColor"));
      return new DFEncyclopediaEntry(shape, Optional.of(baseVal), Optional.of(stemVal));
   }

   private DFEncyclopediaEntry(Optional<Integer> shape, Optional<Color> baseColor, Optional<Color> stemColor) {
      this.shape = shape;
      this.baseColor = baseColor;
      this.stemColor = stemColor;
   }

   public Optional<Color> getBaseColor() {
      return this.baseColor;
   }

   public Optional<Color> getStemColor() {
      return this.stemColor;
   }

   public Optional<Integer> getShape() {
      return this.shape;
   }

   public AkumaNoMiItem getDevilFruit() {
      return this.devilFruit;
   }

   public void setDevilFruit(AkumaNoMiItem devilFruit) {
      this.devilFruit = devilFruit;
   }

   public int getCompletion() {
      int sum = 0;
      if (this.getShape().isPresent()) {
         ++sum;
      }

      if (this.getBaseColor().isPresent() && ((Color)this.getBaseColor().get()).getRGB() != Color.BLACK.getRGB()) {
         ++sum;
      }

      if (this.getStemColor().isPresent() && ((Color)this.getStemColor().get()).getRGB() != Color.BLACK.getRGB()) {
         ++sum;
      }

      return sum;
   }

   public boolean isComplete() {
      return this.getCompletion() >= 3;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[ ");
      if (this.getShape().isPresent()) {
         sb.append("Shape: " + String.valueOf(this.getShape().get()) + " ");
      }

      if (this.getBaseColor().isPresent()) {
         sb.append("Base Color: " + ((Color)this.getBaseColor().get()).toString() + " ");
      }

      if (this.getStemColor().isPresent()) {
         sb.append("Stem Color: " + ((Color)this.getStemColor().get()).toString() + " ");
      }

      sb.append("Completion: " + this.getCompletion());
      sb.append(" ]");
      return sb.toString();
   }
}
