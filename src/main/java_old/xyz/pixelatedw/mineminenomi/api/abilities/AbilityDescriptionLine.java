package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class AbilityDescriptionLine {
   public static final IDescriptionLine NEW_LINE = (e, a) -> Component.m_237113_(" ");
   public static final IDescriptionLine DOUBLE_NEW_LINE = (e, a) -> Component.m_237113_(System.getProperty("line.separator"));
   private @Nullable Component staticLine;
   private @Nullable IDescriptionLine dynamicLine;
   private boolean isAdvanced;

   private AbilityDescriptionLine(IDescriptionLine line, boolean isAdvanced) {
      this.dynamicLine = line;
      this.isAdvanced = isAdvanced;
   }

   private AbilityDescriptionLine(Component line, boolean isAdvanced) {
      this.staticLine = line;
      this.isAdvanced = isAdvanced;
   }

   public static AbilityDescriptionLine of(IDescriptionLine line) {
      return new AbilityDescriptionLine(line, false);
   }

   public static AbilityDescriptionLine of(IDescriptionLine line, boolean isAdvanced) {
      return new AbilityDescriptionLine(line, isAdvanced);
   }

   public static AbilityDescriptionLine of(Component text) {
      return new AbilityDescriptionLine(text, false);
   }

   public static AbilityDescriptionLine of(Component text, boolean isAdvanced) {
      return new AbilityDescriptionLine(text, isAdvanced);
   }

   public @Nullable Component getTextComponent(LivingEntity entity, IAbility ability) {
      return this.staticLine != null ? this.staticLine : this.dynamicLine.expand(entity, ability);
   }

   public @Nullable Component getStaticTextComponent() {
      return this.staticLine;
   }

   public boolean isStatic() {
      return this.staticLine != null;
   }

   public boolean isDynamic() {
      return this.dynamicLine != null;
   }

   public boolean isAdvanced() {
      return this.isAdvanced;
   }

   @FunctionalInterface
   public interface IDescriptionLine {
      Component expand(LivingEntity var1, IAbility var2);

      static IDescriptionLine of(Component text) {
         return (e, a) -> text;
      }
   }
}
