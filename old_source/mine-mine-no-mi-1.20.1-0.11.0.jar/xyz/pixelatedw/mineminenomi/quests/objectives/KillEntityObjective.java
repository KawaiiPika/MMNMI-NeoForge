package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IKillEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class KillEntityObjective extends Objective implements IKillEntityObjective {
   protected static final ICheckKill DEFAULT_RULE = (player, target, source) -> {
      boolean isMob = target instanceof Mob;
      AttributeInstance attackAttribute = target.m_21204_().m_22146_(Attributes.f_22281_);
      boolean isAggressive = attackAttribute != null && attackAttribute.m_22135_() > (double)0.0F;
      return isMob && isAggressive;
   };
   private ICheckKill killCheck;

   public KillEntityObjective(Quest parent, Component titleId, int count) {
      this(parent, titleId, count, DEFAULT_RULE);
   }

   public KillEntityObjective(Quest parent, Component titleId, int count, ICheckKill check) {
      super(parent, titleId);
      this.setMaxProgress((float)count);
      this.killCheck = check;
   }

   public boolean checkKill(Player player, LivingEntity target, DamageSource source) {
      return this.killCheck.test(player, target, source);
   }

   @FunctionalInterface
   public interface ICheckKill {
      boolean test(Player var1, LivingEntity var2, DamageSource var3);

      default ICheckKill and(ICheckKill check) {
         return (player, target, source) -> this.test(player, target, source) && check.test(player, target, source);
      }

      default ICheckKill or(ICheckKill check) {
         return (player, target, source) -> this.test(player, target, source) || check.test(player, target, source);
      }
   }
}
