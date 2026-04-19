package xyz.pixelatedw.mineminenomi.api.events.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.Nullable;

public class UpdateCombatStateEvent extends LivingEvent {
   private @Nullable LivingEntity attacker;
   private boolean state;

   public UpdateCombatStateEvent(LivingEntity entity, LivingEntity attacker, boolean state) {
      super(entity);
      this.attacker = attacker;
      this.state = state;
   }

   public @Nullable LivingEntity getLastAttacker() {
      return this.attacker;
   }

   public boolean getCurrentInCombatState() {
      return this.state;
   }
}
