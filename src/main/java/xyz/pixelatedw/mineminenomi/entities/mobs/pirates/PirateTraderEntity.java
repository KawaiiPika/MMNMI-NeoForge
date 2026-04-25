package xyz.pixelatedw.mineminenomi.entities.mobs.pirates;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class PirateTraderEntity extends TraderEntity {
   public PirateTraderEntity(EntityType<? extends PirateTraderEntity> type, Level world) {
      super(type, world);
      if (world != null && !world.isClientSide()) {
         this.getStats().setFaction(ModFactions.PIRATE.getId());
      }
   }

   @Override
   public boolean canTrade(Player player) {
      PlayerStats stats = PlayerStats.get(player);
      return stats.isPirate() || stats.isRevolutionary() || stats.isBandit();
   }

   @Override
   public ResourceKey<LootTable> getTradeTable() {
      return ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "entities/trader/pirate_trader"));
   }

   @Override
   public Component getTradeFailMessage() {
      return Component.translatable("gui.mineminenomi.no_pirate");
   }

   @Override
   public Currency getCurrency() {
      return Currency.BELLY;
   }

   @Override
   public ResourceLocation getCurrentTexture() {
       return ModResources.getPirateTraderTexture(this.getId() % 2 + 1);
   }
}
