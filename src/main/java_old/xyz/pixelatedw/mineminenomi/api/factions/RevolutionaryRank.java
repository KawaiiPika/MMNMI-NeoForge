package xyz.pixelatedw.mineminenomi.api.factions;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public enum RevolutionaryRank implements IFactionRank {
   MEMBER(ModI18n.REVOLUTIONARY_TITLE_MEMBER, 0),
   OFFICER(ModI18n.REVOLUTIONARY_TITLE_OFFICER, 30),
   COMMANDER(ModI18n.REVOLUTIONARY_TITLE_COMMANDER, 50),
   CHIEF_OF_STAFF(ModI18n.REVOLUTIONARY_TITLE_CHIEF_OF_STAFF, 80),
   SUPREME_COMMANDER(ModI18n.REVOLUTIONARY_TITLE_SUPREME_COMMANDER, 100);

   private Component unlocalizedName;
   private int loyaltyRequired;

   private RevolutionaryRank(Component unlocalizedName, int loyaltyRequired) {
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
      if (props != null && props.isRevolutionary()) {
         int nextId = this.ordinal() + 1;
         if (nextId >= values().length) {
            return true;
         } else {
            RevolutionaryRank next = values()[nextId];
            return props.getLoyalty() >= (double)this.getRequiredLoyalty() && (next == null || props.getLoyalty() < (double)next.getRequiredLoyalty());
         }
      } else {
         return false;
      }
   }

   // $FF: synthetic method
   private static RevolutionaryRank[] $values() {
      return new RevolutionaryRank[]{MEMBER, OFFICER, COMMANDER, CHIEF_OF_STAFF, SUPREME_COMMANDER};
   }
}
