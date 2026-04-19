package xyz.pixelatedw.mineminenomi.quests.objectives;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IHealEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class HealEntityObjective extends Objective implements IHealEntityObjective {
   protected ICheckHeal healEvent;

   public HealEntityObjective(Quest parent, Component titleId, int count) {
      this(parent, titleId, count, (ICheckHeal)null);
   }

   public HealEntityObjective(Quest parent, Component titleId, int count, EntityType<?> entityType) {
      this(parent, titleId, count, (ICheckHeal)((player, target, amount) -> target.m_6095_() == entityType));
   }

   public HealEntityObjective(Quest parent, Component titleId, int count, @Nullable ICheckHeal check) {
      super(parent, titleId);
      this.healEvent = (player, target, amount) -> true;
      this.setMaxProgress((float)count);
      if (check != null) {
         this.healEvent = check;
      }

   }

   public boolean checkHeal(Player player, LivingEntity target, float amount) {
      return this.healEvent.test(player, target, amount);
   }

   @FunctionalInterface
   public interface ICheckHeal {
      boolean test(Player var1, LivingEntity var2, float var3);

      default ICheckHeal and(ICheckHeal check) {
         return (player, target, amount) -> this.test(player, target, amount) && check.test(player, target, amount);
      }

      default ICheckHeal or(ICheckHeal check) {
         return (player, target, amount) -> this.test(player, target, amount) || check.test(player, target, amount);
      }
   }
}
