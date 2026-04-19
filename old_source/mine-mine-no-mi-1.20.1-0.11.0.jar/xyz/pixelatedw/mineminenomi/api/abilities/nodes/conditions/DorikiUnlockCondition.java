package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class DorikiUnlockCondition implements NodeUnlockCondition {
   private final double dorikiNeeded;

   public static DorikiUnlockCondition atLeast(double doriki) {
      return new DorikiUnlockCondition(doriki);
   }

   public DorikiUnlockCondition(double doriki) {
      this.dorikiNeeded = doriki;
   }

   public boolean test(LivingEntity target) {
      double doriki = (Double)EntityStatsCapability.getLazy(target).map((props) -> props.getDoriki()).orElse((double)0.0F);
      return doriki >= this.dorikiNeeded;
   }

   public MutableComponent getTooltip() {
      return Component.m_237110_(ModI18nNodes.DORIKI_CHECK, new Object[]{this.dorikiNeeded});
   }
}
