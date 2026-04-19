package xyz.pixelatedw.mineminenomi.api.damagesources;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class TargetPredicate {
   public static final TargetPredicate EVERYTHING = (new TargetPredicate()).selector(Predicates.alwaysTrue()).testVanilla(TargetingConditions.m_148353_());
   public static final TargetPredicate DEFAULT_AREA_CHECK = (new TargetPredicate()).testEnemyFaction();
   public static final TargetPredicate DEFAULT_LINE_CHECK = (new TargetPredicate()).testEnemyFaction().testAdvancedInView();
   private TargetingConditions vanillaPredicate;
   private boolean testSimpleInView;
   private boolean testAdvancedInView;
   private float fov;
   private boolean testEnemy;
   private boolean testFriendly;
   private Predicate<Entity> selector;

   public TargetPredicate() {
      this.vanillaPredicate = TargetingConditions.f_26872_;
   }

   public TargetPredicate testVanilla(TargetingConditions entityPredicate) {
      this.vanillaPredicate = entityPredicate;
      return this;
   }

   public TargetPredicate testSimpleInView() {
      this.testSimpleInView = true;
      return this;
   }

   public TargetPredicate testAdvancedInView() {
      this.testAdvancedInView = true;
      this.fov = 60.0F;
      return this;
   }

   public TargetPredicate testAdvancedInView(float fov) {
      this.testAdvancedInView = true;
      this.fov = fov;
      return this;
   }

   public TargetPredicate testEnemyFaction() {
      this.testEnemy = true;
      return this;
   }

   public TargetPredicate testFriendlyFaction() {
      this.testFriendly = true;
      return this;
   }

   public TargetPredicate selector(@Nullable Predicate<Entity> customPredicate) {
      this.selector = customPredicate;
      return this;
   }

   public boolean test(@Nullable LivingEntity entity, Entity target) {
      if (target instanceof LivingEntity living) {
         if (!this.vanillaPredicate.m_26885_(entity, living)) {
            return false;
         }
      }

      if (this.selector != null && !this.selector.test(target)) {
         return false;
      } else if (this.testEnemy && !ModEntityPredicates.getEnemyFactions(entity).test(target)) {
         return false;
      } else if (this.testFriendly && !ModEntityPredicates.getFriendlyFactions(entity).test(target)) {
         return false;
      } else if (this.testSimpleInView && !entity.m_142582_(target)) {
         return false;
      } else {
         return !this.testAdvancedInView || entity.m_142582_(target) && TargetHelper.isEntityInView(entity, target, this.fov);
      }
   }
}
