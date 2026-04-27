package xyz.pixelatedw.mineminenomi.abilities.ryusoken;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.basic.PassiveAbility;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

public class RyusokenPassiveBonusesAbility extends PassiveAbility {
   public RyusokenPassiveBonusesAbility() {
      super();
   }

   public xyz.pixelatedw.mineminenomi.api.util.Result canUnlock(LivingEntity entity) {
      if (entity.hasData(ModDataAttachments.PLAYER_STATS)) {
         return entity.getData(ModDataAttachments.PLAYER_STATS).isRyusoken() ? xyz.pixelatedw.mineminenomi.api.util.Result.success() : xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
      }
      return xyz.pixelatedw.mineminenomi.api.util.Result.fail(null);
   }

   public net.minecraft.network.chat.Component getDisplayName() {
       return net.minecraft.network.chat.Component.literal("Ryusoken Passive Bonuses");
   }

   public net.minecraft.resources.ResourceLocation getId() {
       return net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mineminenomi", "ryusoken_passive_bonuses");
   }
}
