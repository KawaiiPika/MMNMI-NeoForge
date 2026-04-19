package xyz.pixelatedw.mineminenomi.entities.mobs.pirates;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class PirateTraderEntity extends TraderEntity {
   public PirateTraderEntity(EntityType<? extends PirateTraderEntity> type, Level world) {
      super(type, world, MobsHelper.PIRATE_TRADERS_TEXTURES);
      if (world != null && !world.f_46443_) {
         this.getEntityStats().setFaction((Faction)ModFactions.PIRATE.get());
      }

   }

   public boolean canTrade(Player player) {
      boolean canTrade = (Boolean)EntityStatsCapability.get(player).map((props) -> props.isPirate() || props.isRevolutionary() || props.isBandit()).orElse(false);
      return canTrade;
   }

   public ResourceLocation getTradeTable() {
      return ModLootTables.PIRATE_TRADER;
   }

   public Component getTradeFailMessage() {
      return ModI18n.TRADER_NO_MARINE;
   }

   public Currency getCurrency() {
      return Currency.BELLY;
   }
}
