package xyz.pixelatedw.mineminenomi.handlers.ability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent.OverlayType;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.abilities.magu.LavaFlowAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;

public class MaguPassivesHandler {
   public static void lavaMovementBoost(Player player) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (props != null) {
         if (props.hasUnlockedAbility((AbilityCore)LavaFlowAbility.INSTANCE.get())) {
            if (player.m_20077_() && !player.m_150110_().f_35935_) {
               float a = player.f_20902_ == 0.0F && player.f_20900_ == 0.0F ? 0.5F : 2.0F;
               float y = player.m_6047_() && !(player.m_20186_() - player.f_19855_ > (double)0.0F) ? 2.0F : 0.0F;
               Vec3 vec = player.m_20184_().m_82542_((double)a, (double)y, (double)a);
               boolean isJumping = (Boolean)EntityStatsCapability.get(player).map(IEntityStats::isJumping).orElse(false);
               if (isJumping) {
                  AbilityHelper.setDeltaMovement(player, vec.m_82520_((double)0.0F, 0.4, (double)0.0F));
               } else {
                  AbilityHelper.setDeltaMovement(player, vec);
               }
            }

         }
      }
   }

   public static boolean canSeeThroughFire(Player player, RenderBlockScreenEffectEvent.OverlayType type) {
      if (!type.equals(OverlayType.FIRE)) {
         return false;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            return props.hasUnlockedAbility((AbilityCore)LavaFlowAbility.INSTANCE.get());
         }
      }
   }

   public static boolean canSeeInsideLava(Player player) {
      if (!player.isEyeInFluidType((FluidType)ForgeMod.LAVA_TYPE.get())) {
         return false;
      } else {
         IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            return props.hasUnlockedAbility((AbilityCore)LavaFlowAbility.INSTANCE.get());
         }
      }
   }
}
