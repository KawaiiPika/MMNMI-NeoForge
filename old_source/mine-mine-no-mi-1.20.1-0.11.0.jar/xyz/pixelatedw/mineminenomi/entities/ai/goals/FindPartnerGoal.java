package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.IChallengeBoss;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.entities.ai.IGoalPartner;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class FindPartnerGoal<P extends Mob & IGoalPartner<T>, T extends Mob & IGoalPartner<P>> extends TickedGoal<T> {
   private static final int COOLDOWN = 100;
   private Predicate<Entity> partnerTest;
   private InProgressChallenge challenge;
   private boolean setPartnerBack;

   public FindPartnerGoal(T entity, EntityType<P> partnerType) {
      super(entity);
      this.partnerTest = (target) -> target.m_6095_().equals(partnerType);
   }

   public FindPartnerGoal<P, T> setPartnerBack(boolean flag) {
      this.setPartnerBack = flag;
      return this;
   }

   public boolean m_8036_() {
      if (((IGoalPartner)this.entity).getPartner() != null && ((Mob)((IGoalPartner)this.entity).getPartner()).m_6084_()) {
         return false;
      } else {
         Mob var2 = this.entity;
         if (var2 instanceof IChallengeBoss) {
            IChallengeBoss challengeBoss = (IChallengeBoss)var2;
            this.challenge = challengeBoss.getChallengeInfo().getInProgressChallengeData();
         }

         return this.hasTimePassedSinceLastEnd(100.0F);
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      if (this.challenge != null) {
         Optional<LivingEntity> optTarget = this.challenge.getEnemies().stream().filter(this.partnerTest).findFirst();
         if (optTarget.isPresent()) {
            this.setPartner((Mob)optTarget.get());
         }
      } else {
         List<LivingEntity> list = WyHelper.<LivingEntity>getNearbyEntities(this.entity.m_20182_(), this.entity.m_20193_(), (double)20.0F, (double)20.0F, (double)20.0F, this.partnerTest, LivingEntity.class);
         list.remove(this.entity);
         this.setPartner((Mob)list.stream().findAny().orElse((Object)null));
      }

   }

   private void setPartner(P partner) {
      if (partner != null && partner.m_6084_()) {
         ((IGoalPartner)this.entity).setPartner(partner);
         if (this.setPartnerBack) {
            ((IGoalPartner)partner).setPartner(this.entity);
         }
      }

   }
}
