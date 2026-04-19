package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IUseAbilityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class UseAbilityObjective extends Objective implements IUseAbilityObjective {
   protected ICheckAbilityUse useEvent;

   public UseAbilityObjective(Quest parent, Component titleId, int count) {
      this(parent, titleId, count, (ICheckAbilityUse)null);
   }

   public UseAbilityObjective(Quest parent, Component titleId, int count, AbilityCore<?> ability) {
      this(parent, titleId, count, (ICheckAbilityUse)((player, abl) -> abl.getCore().equals(ability)));
   }

   public UseAbilityObjective(Quest parent, Component titleId, int count, ICheckAbilityUse check) {
      super(parent, titleId);
      this.useEvent = (player, ability) -> true;
      this.setMaxProgress((float)count);
      if (check != null) {
         this.useEvent = check;
      }

   }

   public boolean checkAbility(Player player, IAbility ability) {
      return this.useEvent.test(player, ability);
   }

   @FunctionalInterface
   public interface ICheckAbilityUse {
      boolean test(Player var1, IAbility var2);

      default ICheckAbilityUse and(ICheckAbilityUse check) {
         return (player, ability) -> this.test(player, ability) && check.test(player, ability);
      }

      default ICheckAbilityUse or(ICheckAbilityUse check) {
         return (player, ability) -> this.test(player, ability) || check.test(player, ability);
      }
   }
}
