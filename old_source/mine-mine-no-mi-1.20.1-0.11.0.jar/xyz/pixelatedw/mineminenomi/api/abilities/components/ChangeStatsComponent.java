package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ChangeStatsComponent extends AbilityComponent<IAbility> {
   private Map<Attribute, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> modifiers = new HashMap();
   private boolean hasModsApplied;

   public static AbilityDescriptionLine.IDescriptionLine getTooltip() {
      return (e, a) -> {
         Optional<ChangeStatsComponent> comp = a.<ChangeStatsComponent>getComponent((AbilityComponentKey)ModAbilityComponents.CHANGE_STATS.get());
         if (comp.isPresent()) {
            Component[] lines = new Component[((ChangeStatsComponent)comp.get()).modifiers.size() + 1];
            int lineId = 0;
            lines[lineId++] = Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString() + "§r");

            for(Map.Entry<Attribute, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> entry : ((ChangeStatsComponent)comp.get()).modifiers.entrySet()) {
               AttributeModifier mod = (AttributeModifier)((ImmutablePair)entry.getValue()).getRight();
               float value = (float)mod.m_22218_();
               boolean isBuff = value > 0.0F;
               Component attrName = Component.m_237115_(((Attribute)entry.getKey()).m_22087_());
               String op = "";
               switch (mod.m_22217_()) {
                  case ADDITION:
                     op = isBuff ? "+" : "";
                     break;
                  case MULTIPLY_BASE:
                     double base = ((Attribute)entry.getKey()).m_22082_();
                     value = (float)(base * mod.m_22218_());
                     op = isBuff ? "+" : "";
                     break;
                  case MULTIPLY_TOTAL:
                     op = "x";
                     value = (float)((double)1.0F + mod.m_22218_());
               }

               Component statText = (new AbilityStat.Builder(attrName, value)).withSign(op).withBuff(isBuff).build().getStatDescription(2);
               lines[lineId++] = statText;
            }

            StringBuilder sb = new StringBuilder();
            lineId = 0;

            for(Component text : lines) {
               boolean hasFollowingLine = lineId++ < lines.length - 1;
               String var24 = text.getString();
               sb.append(var24 + (hasFollowingLine ? "\n" : ""));
            }

            return Component.m_237113_(sb.toString());
         } else {
            return null;
         }
      };
   }

   public ChangeStatsComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.CHANGE_STATS.get(), ability);
   }

   public ChangeStatsComponent addAttributeModifier(Supplier<Attribute> attr, AttributeModifier modifier) {
      return this.addAttributeModifier((Supplier)attr, modifier, (Predicate)null);
   }

   public ChangeStatsComponent addAttributeModifier(Attribute attr, AttributeModifier modifier) {
      return this.addAttributeModifier(attr, modifier, (Predicate)null);
   }

   public ChangeStatsComponent addAttributeModifier(Attribute attr, AttributeModifier modifier, @Nullable Predicate<LivingEntity> test) {
      this.removeAttributeModifier(attr);
      this.modifiers.put(attr, ImmutablePair.of(test, modifier));
      return this;
   }

   public ChangeStatsComponent addAttributeModifier(Supplier<Attribute> attr, AttributeModifier modifier, @Nullable Predicate<LivingEntity> test) {
      return this.addAttributeModifier((Attribute)attr.get(), modifier, test);
   }

   public ChangeStatsComponent removeAttributeModifier(Attribute attr) {
      this.modifiers.remove(attr);
      return this;
   }

   public ChangeStatsComponent removeAttributeModifier(Supplier<Attribute> attr) {
      return this.removeAttributeModifier((Attribute)attr.get());
   }

   public void clearAttributeModifiers() {
      this.modifiers.clear();
   }

   public void applyModifiers(LivingEntity entity) {
      for(Map.Entry<Attribute, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> entry : this.modifiers.entrySet()) {
         this.applyModifier(entity, (Attribute)entry.getKey(), (AttributeModifier)((ImmutablePair)entry.getValue()).getRight());
      }

      this.hasModsApplied = true;
   }

   private void applyMissingModifier(LivingEntity entity, Attribute attr, AttributeModifier modifier) {
      if (!this.hasModifier(entity, attr, modifier.m_22209_())) {
         this.applyModifier(entity, attr, modifier);
      }

      this.hasModsApplied = true;
   }

   private void applyModifier(LivingEntity entity, Attribute attr, AttributeModifier modifier) {
      this.ensureIsRegistered();
      if (!(modifier instanceof AbilityAttributeModifier) || ((AbilityAttributeModifier)modifier).getAbilityCore().equals(this.getAbility().getCore())) {
         AttributeInstance modAttr = entity.m_21051_(attr);
         if (modAttr != null) {
            modAttr.m_22130_(modifier);
            modAttr.m_22118_(modifier);
         }

      }
   }

   public void removeModifiers(LivingEntity entity) {
      if (this.hasModsApplied) {
         for(Map.Entry<Attribute, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> entry : this.modifiers.entrySet()) {
            this.removeModifier(entity, (Attribute)entry.getKey(), (AttributeModifier)((ImmutablePair)entry.getValue()).getRight());
         }

         this.hasModsApplied = false;
      }
   }

   public void removeModifier(LivingEntity entity, Attribute attr, AttributeModifier modifier) {
      this.removeModifier(entity, attr, modifier.m_22209_());
   }

   public void removeModifier(LivingEntity entity, Attribute attr, UUID id) {
      AttributeInstance modAttr = entity.m_21051_(attr);
      if (this.hasModsApplied && modAttr != null) {
         AttributeModifier modifier = modAttr.m_22111_(id);
         if (modifier instanceof AbilityAttributeModifier && !((AbilityAttributeModifier)modifier).getAbilityCore().equals(this.getAbility().getCore())) {
            return;
         }

         modAttr.m_22120_(id);
         if (attr.equals(Attributes.f_22276_)) {
            float leftHp = entity.m_21223_() - entity.m_21233_();
            if (leftHp > 0.0F) {
               entity.m_21153_(entity.m_21223_() - leftHp);
            }
         }
      }

   }

   public boolean hasModifier(LivingEntity entity, Attribute attr) {
      return entity.m_21051_(attr) != null;
   }

   private boolean hasModifier(LivingEntity entity, Attribute attr, UUID id) {
      AttributeInstance modAttr = entity.m_21051_(attr);
      if (modAttr == null) {
         return false;
      } else {
         return modAttr.m_22111_(id) != null;
      }
   }

   protected void doTick(LivingEntity entity) {
      this.modifiers.forEach((attr, pair) -> {
         if (pair.getLeft() != null) {
            if (((Predicate)pair.getLeft()).test(entity)) {
               this.applyMissingModifier(entity, attr, (AttributeModifier)pair.getRight());
            } else {
               this.removeModifier(entity, attr, (AttributeModifier)pair.getRight());
            }
         }

      });
   }

   public Component getTooltip2() {
      Component[] lines = new Component[this.modifiers.size() + 1];
      int lineId = 0;
      lines[lineId++] = Component.m_237113_("§a" + ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString() + "§r");

      for(Map.Entry<Attribute, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> entry : this.modifiers.entrySet()) {
         AttributeModifier mod = (AttributeModifier)((ImmutablePair)entry.getValue()).getRight();
         float value = (float)mod.m_22218_();
         boolean isBuff = value > 0.0F;
         Component attrName = Component.m_237115_(((Attribute)entry.getKey()).m_22087_());
         String op = "";
         switch (mod.m_22217_()) {
            case ADDITION:
               op = isBuff ? "+" : "";
               break;
            case MULTIPLY_BASE:
               double base = ((Attribute)entry.getKey()).m_22082_();
               value = (float)(base * mod.m_22218_());
               op = isBuff ? "+" : "";
               break;
            case MULTIPLY_TOTAL:
               op = "x";
               value = (float)((double)1.0F + mod.m_22218_());
         }

         Component statText = (new AbilityStat.Builder(attrName, value)).withSign(op).withBuff(isBuff).buildComponent(2);
         lines[lineId++] = statText;
      }

      StringBuilder sb = new StringBuilder();
      lineId = 0;

      for(Component text : lines) {
         boolean hasFollowingLine = lineId++ < lines.length - 1;
         String var22 = text.getString();
         sb.append(var22 + (hasFollowingLine ? "\n" : ""));
      }

      return Component.m_237113_(sb.toString());
   }

   public @Nullable CompoundTag save() {
      CompoundTag nbt = super.save();
      nbt.m_128379_("hasModsApplied", this.hasModsApplied);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      super.load(nbt);
      this.hasModsApplied = nbt.m_128471_("hasModsApplied");
   }
}
