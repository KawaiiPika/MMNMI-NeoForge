package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

public class MarineTraderEntity extends OPEntity {
   public MarineTraderEntity(EntityType<? extends MarineTraderEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.isClientSide) {
         this.getStats().setFaction(ResourceLocation.fromNamespaceAndPath("mineminenomi", "marine"));
      }
   }

   public boolean canTrade(Player player) {
      PlayerStats stats = PlayerStats.get(player);
      if (stats.getFaction().isPresent()) {
         String faction = stats.getFaction().get().getPath();
         return faction.equals("marine") || faction.equals("bounty_hunter");
      }
      return false;
   }

   public ResourceLocation getTradeTable() {
      return ResourceLocation.fromNamespaceAndPath("mineminenomi", "marine_trader");
   }

   public Component getTradeFailMessage() {
      return Component.translatable("entity.mineminenomi.trader.no_pirate");
   }

}
