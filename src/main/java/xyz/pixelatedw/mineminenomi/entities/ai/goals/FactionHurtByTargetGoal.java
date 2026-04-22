package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import net.minecraft.resources.ResourceLocation;

public class FactionHurtByTargetGoal extends HurtByTargetGoal {
   @Nullable
   private Predicate<Entity> factionPredicate;

   public FactionHurtByTargetGoal(PathfinderMob entity, @Nullable Predicate<Entity> factionPredicate, Class<?>... exclude) {
      super(entity, exclude);
      this.factionPredicate = factionPredicate;
   }

   @Override
   public boolean canUse() {
      return this.factionPredicate != null && !this.factionPredicate.test(this.mob.getLastHurtByMob()) ? false : super.canUse();
   }

   @Override
   protected void alertOther(Mob other, LivingEntity target) {
      PlayerStats selfStats = this.mob.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
      PlayerStats otherStats = other.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
      if (selfStats != null && otherStats != null) {
          ResourceLocation selfFactionLoc = selfStats.getBasic().identity().faction().orElse(null);
          ResourceLocation otherFactionLoc = otherStats.getBasic().identity().faction().orElse(null);

          if (selfFactionLoc != null && otherFactionLoc != null && selfFactionLoc.equals(otherFactionLoc)) {
             super.alertOther(other, target);
          }
      }
   }

   @Override
   protected boolean canAttack(@Nullable LivingEntity potentialTarget, TargetingConditions targetPredicate) {
      return this.factionPredicate != null && this.factionPredicate.test(potentialTarget) ? super.canAttack(potentialTarget, targetPredicate) : false;
   }
}
