package xyz.pixelatedw.mineminenomi.api.abilities;

import java.text.NumberFormat;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.util.FloatRange;
import xyz.pixelatedw.mineminenomi.api.util.INumberRange;
import xyz.pixelatedw.mineminenomi.api.util.IntRange;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;

public class AbilityStat {
   private Component name;
   private @Nullable Component unit;
   private INumberRange<?> value;
   private INumberRange<?> bonus;
   private AbilityStatType bonusType;
   private boolean isBuff;
   private String sign;

   private AbilityStat(Component name, INumberRange<?> value) {
      this.name = name;
      this.value = value;
   }

   private void setUnit(Component unit) {
      this.unit = unit;
   }

   private void setBonus(INumberRange<?> bonus, AbilityStatType bonusType) {
      this.bonus = bonus;
      this.bonusType = bonusType;
   }

   private void setBuff(boolean flag) {
      this.isBuff = flag;
   }

   private void setSign(String sign) {
      this.sign = sign;
   }

   public Component getStatDescription() {
      return this.getStatDescription(0);
   }

   public Component getStatDescription(int indent) {
      Object[] params = new Object[10];
      int paramId = 0;
      StringBuilder sb = new StringBuilder();
      sb.append("%s%s§r");
      params[paramId++] = this.isBuff ? "§a" : "§c";
      params[paramId++] = this.name.getString();
      NumberFormat nf = NumberFormat.getInstance();
      nf.setMaximumFractionDigits(2);
      float minBonus = 0.0F;
      float maxBonus = 0.0F;
      String bonusType = "";
      if (this.bonus != null && ClientConfig.isAbilityBonusesMergeEnable()) {
         bonusType = this.bonusType.getColorCode();
         minBonus = this.bonus.getMin().floatValue();
         if (this.bonus.isFixed()) {
            maxBonus = minBonus;
         } else if (this.bonus.isRange()) {
            maxBonus = this.bonus.getMax().floatValue();
         }
      }

      if (this.value.isInfinite()) {
         sb.append(" ∞");
      } else if (this.value.isRange()) {
         sb.append(" %s%s%s-%s§r");
         params[paramId++] = bonusType;
         params[paramId++] = this.sign;
         params[paramId++] = nf.format((double)(this.value.getMin().floatValue() + minBonus));
         params[paramId++] = nf.format((double)(this.value.getMax().floatValue() + maxBonus));
      } else {
         sb.append(" %s%s%s§r");
         params[paramId++] = this.sign;
         params[paramId++] = bonusType;
         params[paramId++] = nf.format((double)(this.value.getMin().floatValue() + minBonus));
      }

      if (this.unit != null) {
         sb.append(" %s");
         params[paramId++] = this.unit.getString();
      }

      if (indent > 0) {
         while(indent > 0) {
            sb.insert(0, " ");
            --indent;
         }
      }

      if (this.bonus != null && !ClientConfig.isAbilityBonusesMergeEnable()) {
         float min = this.bonus.getMin().floatValue();
         float max = this.bonus.getMax().floatValue();
         if (this.bonus.isRange() && (min != 0.0F || max != 0.0F)) {
            sb.append(" (%s%s§r-%s%s§r)");
            int var35 = paramId++;
            String var39 = this.bonusType.getColorCode();
            params[var35] = var39 + (min == 0.0F ? "" : (min > 0.0F ? "+" : ""));
            params[paramId++] = nf.format(this.bonus.getMin());
            var35 = paramId++;
            var39 = this.bonusType.getColorCode();
            params[var35] = var39 + (max == 0.0F ? "" : (max > 0.0F ? "+" : ""));
            params[paramId++] = nf.format(this.bonus.getMax());
         } else if (this.bonus.isFixed() && min != 0.0F) {
            sb.append(" (%s%s§r)");
            int var33 = paramId++;
            String var10002 = this.bonusType.getColorCode();
            params[var33] = var10002 + (min > 0.0F ? "+" : "");
            params[paramId++] = nf.format(this.bonus.getMin());
         }
      }

      return Component.m_237113_(String.format(sb.toString(), params));
   }

   public static class Builder {
      private Component name;
      private @Nullable Component unit;
      private INumberRange<?> value;
      private INumberRange<?> bonus;
      private AbilityStatType bonusType;
      private boolean isStatBuff;
      private String sign;

      public Builder(Component name, float value) {
         this.bonusType = AbilityStat.AbilityStatType.NEUTRAL;
         this.isStatBuff = true;
         this.sign = "";
         this.name = name;
         this.value = new FloatRange(value);
      }

      public Builder(Component name, float min, float max) {
         this.bonusType = AbilityStat.AbilityStatType.NEUTRAL;
         this.isStatBuff = true;
         this.sign = "";
         this.name = name;
         this.value = new FloatRange(min, max);
      }

      public Builder(Component name, int value) {
         this.bonusType = AbilityStat.AbilityStatType.NEUTRAL;
         this.isStatBuff = true;
         this.sign = "";
         this.name = name;
         this.value = new IntRange(value);
      }

      public Builder(Component name, int min, int max) {
         this.bonusType = AbilityStat.AbilityStatType.NEUTRAL;
         this.isStatBuff = true;
         this.sign = "";
         this.name = name;
         this.value = new IntRange(min, max);
      }

      public Builder withUnit(Component unit) {
         this.unit = unit;
         return this;
      }

      public Builder withBonus(float bonus, AbilityStatType type) {
         this.bonus = new FloatRange(bonus);
         this.bonusType = type;
         return this;
      }

      public Builder withBonus(float minBonus, float maxBonus, AbilityStatType type) {
         this.bonus = new FloatRange(minBonus, maxBonus);
         this.bonusType = type;
         return this;
      }

      public Builder withBuff(boolean flag) {
         this.isStatBuff = flag;
         return this;
      }

      public Builder withSign(String sign) {
         this.sign = sign;
         return this;
      }

      /** @deprecated */
      @Deprecated
      public AbilityStat build() {
         AbilityStat stat = new AbilityStat(this.name, this.value);
         stat.setUnit(this.unit);
         stat.setBonus(this.bonus, this.bonusType);
         stat.setBuff(this.isStatBuff);
         stat.setSign(this.sign);
         return stat;
      }

      public Component buildComponent() {
         return this.buildComponent(0);
      }

      public Component buildComponent(int indent) {
         AbilityStat stat = new AbilityStat(this.name, this.value);
         stat.setUnit(this.unit);
         stat.setBonus(this.bonus, this.bonusType);
         stat.setBuff(this.isStatBuff);
         stat.setSign(this.sign);
         return stat.getStatDescription(indent);
      }
   }

   public static enum AbilityStatType {
      NEUTRAL(""),
      BUFF("§a"),
      DEBUFF("§c");

      private String color;

      private AbilityStatType(String color) {
         this.color = color;
      }

      public String getColorCode() {
         return this.color;
      }

      public static AbilityStatType from(float val, boolean reverse) {
         if (reverse) {
            return val > 0.0F ? BUFF : (val < 0.0F ? DEBUFF : NEUTRAL);
         } else {
            return val < 0.0F ? BUFF : (val > 0.0F ? DEBUFF : NEUTRAL);
         }
      }

      // $FF: synthetic method
      private static AbilityStatType[] $values() {
         return new AbilityStatType[]{NEUTRAL, BUFF, DEBUFF};
      }
   }
}
