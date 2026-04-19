package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenTraderScreenPacket;

public class SkypieanTraderEntity extends TraderEntity {
   private int dirtTradesLeft = 120;

   public SkypieanTraderEntity(EntityType<? extends SkypieanTraderEntity> type, Level world) {
      super(type, world, MobsHelper.SKYPEAN_TRADERS_TEXTURES);
      this.setCanBuyFromPlayers();
   }

   public void m_8099_() {
      EntityStatsCapability.get(this).ifPresent((props) -> {
         props.setFaction((Faction)ModFactions.CIVILIAN.get());
         props.setRace((Race)ModRaces.SKYPIEAN.get());
      });
   }

   public boolean canTrade(Player player) {
      return true;
   }

   public ResourceLocation getTradeTable() {
      return ModLootTables.SKYPIEAN_TRADER;
   }

   public void m_7380_(CompoundTag nbt) {
      super.m_7380_(nbt);
      nbt.m_128405_("tradesLeft", this.dirtTradesLeft);
   }

   public void m_7378_(CompoundTag nbt) {
      super.m_7378_(nbt);
      this.dirtTradesLeft = nbt.m_128451_("tradesLeft");
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (!player.m_9236_().f_46443_) {
         ModNetwork.sendTo(new SOpenTraderScreenPacket(this.m_19879_(), this.tradeEntries), player);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void removeTradesLeft(int value) {
      if (this.dirtTradesLeft - value > 0) {
         this.dirtTradesLeft -= value;
      } else {
         this.dirtTradesLeft = 0;
      }

   }

   public void setTradesLeft(int value) {
      this.dirtTradesLeft = value;
   }

   public int getTradesLeft() {
      return this.dirtTradesLeft;
   }

   public long getExtolLeftInStock() {
      return Currency.getExtolFromBelly((long)this.dirtTradesLeft);
   }

   public Component getTradeFailMessage() {
      return Component.m_237119_();
   }

   public Currency getCurrency() {
      return Currency.EXTOL;
   }
}
