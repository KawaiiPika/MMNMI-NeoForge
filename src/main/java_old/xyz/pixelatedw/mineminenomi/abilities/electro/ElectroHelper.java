package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ElectroHelper {
   public static final UUID SULONG_DAMAGE_BONUS = UUID.fromString("b8ee5e5c-3dee-4ca6-93ae-8340172ae72b");
   public static final UUID SULONG_COOLDOWN_BONUS = UUID.fromString("7fbdd64f-bc93-4bf5-aa59-f877eb014c9e");
   public static final UUID SULONG_RANGE_BONUS = UUID.fromString("009c1be2-c51e-4a0d-8952-5556bbe4a75d");

   public static Result canTransformInSulong(LivingEntity entity, IAbility ability) {
      return !canTransform(entity.m_9236_()) ? Result.fail(ModI18nAbilities.MESSAGE_NEED_MOON) : Result.success();
   }

   public static Ability.ICanUseEvent requireEleclaw(int neededStacks) {
      return (entity, ability) -> {
         EleclawAbility eleclaw = (EleclawAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)EleclawAbility.INSTANCE.get());
         if (eleclaw != null && eleclaw.isContinuous()) {
            int eleclawStacks = (Integer)eleclaw.getComponent((AbilityComponentKey)ModAbilityComponents.STACK.get()).map((comp) -> comp.getStacks()).orElse(0);
            if (eleclawStacks - neededStacks < 0) {
               Component message = Component.m_237110_(ModI18nAbilities.MESSAGE_NOT_ENOUGH_STACKS, new Object[]{neededStacks, ((AbilityCore)EleclawAbility.INSTANCE.get()).getLocalizedName().getString()});
               return Result.fail(message);
            } else {
               return Result.success();
            }
         } else {
            return Result.fail(ModI18nAbilities.MESSAGE_NEED_ELECLAW);
         }
      };
   }

   public static boolean hasSulongActive(LivingEntity entity) {
      SulongAbility sulong = (SulongAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)SulongAbility.INSTANCE.get());
      return sulong != null && sulong.isContinuous();
   }

   public static boolean canTransform(Level world) {
      if (!WyDebug.isDebug() && !world.m_46462_()) {
         return world.m_6042_().m_63936_(world.m_46468_()) == 0 && !world.m_46471_() && !world.m_6042_().f_63856_() && world.m_6042_().f_223549_() && world.m_46462_();
      } else {
         return true;
      }
   }
}
