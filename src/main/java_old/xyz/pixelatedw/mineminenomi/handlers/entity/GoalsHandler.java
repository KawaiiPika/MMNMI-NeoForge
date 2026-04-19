package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NBTGoal;
import xyz.pixelatedw.mineminenomi.data.entity.nbtgoals.INBTGoals;
import xyz.pixelatedw.mineminenomi.data.entity.nbtgoals.NBTGoalsCapability;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

public class GoalsHandler {
   public static void parseNBTGoals(Mob mob) {
      INBTGoals goals = (INBTGoals)NBTGoalsCapability.get(mob).orElse((Object)null);
      if (goals != null) {
         for(NBTGoal goal : goals.getGoals()) {
            AbilityWrapperGoal<?, ?> wrapper = (AbilityWrapperGoal)goal.type().getWrapperFactory().apply(mob, goal.abilityId());
            mob.f_21345_.m_25352_(goal.priority(), wrapper);
         }

      }
   }

   public static void addNewIronGolemGoals(IronGolem entity) {
      entity.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(entity, Mob.class, 5, false, false, (target) -> {
         boolean var10000;
         if (target instanceof OPEntity opEntity) {
            if (opEntity.getEntityStats().isBandit() || opEntity.getEntityStats().isPirate()) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }));
   }
}
