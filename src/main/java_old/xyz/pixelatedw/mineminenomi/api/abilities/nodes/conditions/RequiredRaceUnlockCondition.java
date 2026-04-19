package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class RequiredRaceUnlockCondition implements NodeUnlockCondition {
   private final Race race;

   public static RequiredRaceUnlockCondition requires(Race race) {
      return new RequiredRaceUnlockCondition(race);
   }

   public RequiredRaceUnlockCondition(Race race) {
      this.race = race;
   }

   public boolean test(LivingEntity target) {
      return (Boolean)EntityStatsCapability.getLazy(target).map((props) -> (Race)props.getRace().orElse((Object)null)).filter(Objects::nonNull).map((r) -> r.equals(this.race)).orElse(false);
   }

   public MutableComponent getTooltip() {
      return Component.m_237110_(ModI18nNodes.RACE_CHECK, new Object[]{this.race.getLabel().getString()});
   }
}
