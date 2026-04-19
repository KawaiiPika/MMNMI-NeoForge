package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class BlocksHandler {
   public static boolean tryBlockSuffocationForLogia(LivingEntity entity, DamageSource source) {
      if (!source.m_276093_(DamageTypes.f_268612_)) {
         return false;
      } else {
         Set<IAbility> travelAbilities = (Set)AbilityCapability.get(entity).map((props) -> props.getPassiveAbilities((a) -> a instanceof LogiaBlockBypassingAbility)).orElse(Set.of());
         if (travelAbilities.size() <= 0) {
            return false;
         } else {
            float f = entity.m_6972_(Pose.STANDING).f_20377_ * 0.8F;
            AABB aabb = AABB.m_165882_(entity.m_146892_(), (double)f, 1.0E-6, (double)f);
            BlockState viewState = (BlockState)BlockPos.m_121921_(aabb).map((statePos) -> {
               BlockState blockstate = entity.m_9236_().m_8055_(statePos);
               boolean isColliding = !blockstate.m_60795_() && blockstate.m_60828_(entity.m_9236_(), statePos) && Shapes.m_83157_(blockstate.m_60812_(entity.m_9236_(), statePos).m_83216_((double)statePos.m_123341_(), (double)statePos.m_123342_(), (double)statePos.m_123343_()), Shapes.m_83064_(aabb), BooleanOp.f_82689_);
               return isColliding ? blockstate : Blocks.f_50016_.m_49966_();
            }).filter((state) -> !state.m_60795_()).findFirst().orElse(Blocks.f_50016_.m_49966_());
            if (viewState.m_60795_()) {
               return false;
            } else {
               for(IAbility abl : travelAbilities) {
                  if (abl instanceof LogiaBlockBypassingAbility) {
                     LogiaBlockBypassingAbility travelAbility = (LogiaBlockBypassingAbility)abl;
                     if (travelAbility.canGoThrough(viewState)) {
                        return true;
                     }
                  }
               }

               return false;
            }
         }
      }
   }
}
