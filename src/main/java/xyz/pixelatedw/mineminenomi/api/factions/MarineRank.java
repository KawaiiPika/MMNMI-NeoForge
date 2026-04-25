package xyz.pixelatedw.mineminenomi.api.factions;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

public enum MarineRank implements IFactionRank {
   CHORE_BOY("entity.mineminenomi.marine_rank.chore_boy", 0),
   SEAMAN("entity.mineminenomi.marine_rank.seaman", 5),
   PETTY_OFFICER("entity.mineminenomi.marine_rank.petty_officer", 10),
   LIEUTENANT("entity.mineminenomi.marine_rank.lieutenant", 15),
   COMMANDER("entity.mineminenomi.marine_rank.commander", 20),
   CAPTAIN("entity.mineminenomi.marine_rank.captain", 25),
   COMMODORE("entity.mineminenomi.marine_rank.commodore", 40),
   VICE_ADMIRAL("entity.mineminenomi.marine_rank.vice_admiral", 50),
   ADMIRAL("entity.mineminenomi.marine_rank.admiral", 70),
   FLEET_ADMIRAL("entity.mineminenomi.marine_rank.fleet_admiral", 100);

   private Component unlocalizedName;
   private int loyaltyRequired;

   private MarineRank(String unlocalizedName, int loyaltyRequired) {
      this.unlocalizedName = Component.translatable(unlocalizedName);
      this.loyaltyRequired = loyaltyRequired;
   }

   @Override
   public Component getLocalizedName() {
      return this.unlocalizedName;
   }

   public int getRequiredLoyalty() {
      return this.loyaltyRequired;
   }

   @Override
   public boolean isUnlocked(LivingEntity entity) {
      PlayerStats stats = PlayerStats.get(entity);
      if (stats != null && stats.getFaction().isPresent() && stats.getFaction().get().getPath().equals("marine")) {
         int nextId = this.ordinal() + 1;
         if (nextId >= values().length) {
            return true;
         } else {
            MarineRank next = values()[nextId];
            double loyalty = stats.getLoyalty();
            return loyalty >= (double)this.getRequiredLoyalty() && (next == null || loyalty < (double)next.getRequiredLoyalty());
         }
      } else {
         return false;
      }
   }
}
