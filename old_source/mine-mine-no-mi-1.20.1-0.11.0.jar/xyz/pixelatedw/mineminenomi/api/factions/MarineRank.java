package xyz.pixelatedw.mineminenomi.api.factions;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public enum MarineRank implements IFactionRank {
   CHORE_BOY(ModI18n.MARINE_TITLE_CHORE_BOY, 0),
   SEAMAN(ModI18n.MARINE_TITLE_SEAMAN, 5),
   PETTY_OFFICER(ModI18n.MARINE_TITLE_PETTY_OFFICER, 10),
   LIEUTENANT(ModI18n.MARINE_TITLE_LIEUTENANT, 15),
   COMMANDER(ModI18n.MARINE_TITLE_COMMANDER, 20),
   CAPTAIN(ModI18n.MARINE_TITLE_CAPTAIN, 25),
   COMMODORE(ModI18n.MARINE_TITLE_COMMODORE, 40),
   VICE_ADMIRAL(ModI18n.MARINE_TITLE_VICE_ADMIRAL, 50),
   ADMIRAL(ModI18n.MARINE_TITLE_ADMIRAL, 70),
   FLEET_ADMIRAL(ModI18n.MARINE_TITLE_FLEET_ADMIRAL, 100);

   private Component unlocalizedName;
   private int loyaltyRequired;

   private MarineRank(Component unlocalizedName, int loyaltyRequired) {
      this.unlocalizedName = unlocalizedName;
      this.loyaltyRequired = loyaltyRequired;
   }

   public Component getLocalizedName() {
      return this.unlocalizedName;
   }

   public int getRequiredLoyalty() {
      return this.loyaltyRequired;
   }

   public boolean isUnlocked(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null && props.isMarine()) {
         int nextId = this.ordinal() + 1;
         if (nextId >= values().length) {
            return true;
         } else {
            MarineRank next = values()[nextId];
            return props.getLoyalty() >= (double)this.getRequiredLoyalty() && (next == null || props.getLoyalty() < (double)next.getRequiredLoyalty());
         }
      } else {
         return false;
      }
   }

   // $FF: synthetic method
   private static MarineRank[] $values() {
      return new MarineRank[]{CHORE_BOY, SEAMAN, PETTY_OFFICER, LIEUTENANT, COMMANDER, CAPTAIN, COMMODORE, VICE_ADMIRAL, ADMIRAL, FLEET_ADMIRAL};
   }
}
