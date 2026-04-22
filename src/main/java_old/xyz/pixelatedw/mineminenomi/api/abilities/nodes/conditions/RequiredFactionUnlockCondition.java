package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class RequiredFactionUnlockCondition implements NodeUnlockCondition {
   private final Faction faction;

   public static RequiredFactionUnlockCondition requires(Faction faction) {
      return new RequiredFactionUnlockCondition(faction);
   }

   public RequiredFactionUnlockCondition(Faction faction) {
      this.faction = faction;
   }

   public boolean test(LivingEntity target) {
      return (Boolean)EntityStatsCapability.getLazy(target).map((props) -> (Faction)props.getFaction().orElse((Object)null)).filter(Objects::nonNull).map((r) -> r.equals(this.faction)).orElse(false);
   }

   public MutableComponent getTooltip() {
      return Component.m_237110_(ModI18nNodes.FACTION_CHECK, new Object[]{this.faction.getLabel().getString()});
   }
}
