package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class RequiredStyleUnlockCondition implements NodeUnlockCondition {
   private final FightingStyle style;

   public static RequiredStyleUnlockCondition requires(FightingStyle style) {
      return new RequiredStyleUnlockCondition(style);
   }

   public RequiredStyleUnlockCondition(FightingStyle style) {
      this.style = style;
   }

   public boolean test(LivingEntity target) {
      return (Boolean)EntityStatsCapability.getLazy(target).map((props) -> (FightingStyle)props.getFightingStyle().orElse((Object)null)).filter(Objects::nonNull).map((r) -> r.equals(this.style)).orElse(false);
   }

   public MutableComponent getTooltip() {
      return Component.m_237110_(ModI18nNodes.STYLE_CHECK, new Object[]{this.style.getLabel().getString()});
   }
}
