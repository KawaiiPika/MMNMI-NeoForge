package xyz.pixelatedw.mineminenomi.api.enums;

import java.util.function.BiFunction;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.IExtensibleEnum;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NoAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.AlwaysActiveAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.CloseMeleeAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.DashAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.GrabAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.JumpAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.RepeaterAbilityWrapperGoal;

public enum NBTGoalType implements IExtensibleEnum {
   PASSIVE(AlwaysActiveAbilityWrapperGoal::new),
   DASH(DashAbilityWrapperGoal::new),
   GRAB(GrabAbilityWrapperGoal::new),
   JUMP(JumpAbilityWrapperGoal::new),
   PROJECTILE(ProjectileAbilityWrapperGoal::new),
   PUNCH(CloseMeleeAbilityWrapperGoal::new),
   REPEATER(RepeaterAbilityWrapperGoal::new),
   NOOP(NoAbilityWrapperGoal::new);

   private final BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> func;

   private NBTGoalType(BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> func) {
      this.func = func;
   }

   public BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> getWrapperFactory() {
      return this.func;
   }

   public static NBTGoalType create(String name, BiFunction<Mob, AbilityCore<?>, AbilityWrapperGoal<?, ?>> func) {
      throw new IllegalStateException("Enum not extended");
   }

   // $FF: synthetic method
   private static NBTGoalType[] $values() {
      return new NBTGoalType[]{PASSIVE, DASH, GRAB, JUMP, PROJECTILE, PUNCH, REPEATER, NOOP};
   }
}
