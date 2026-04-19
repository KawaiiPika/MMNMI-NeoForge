package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;

public interface NodeUnlockAction {
   void onUnlock(LivingEntity var1);

   void onLock(LivingEntity var1);

   default NodeUnlockAction andThen(final NodeUnlockAction after) {
      Objects.requireNonNull(after);
      return new NodeUnlockAction() {
         public void onUnlock(LivingEntity entity) {
            NodeUnlockAction.this.onUnlock(entity);
            after.onUnlock(entity);
         }

         public void onLock(LivingEntity entity) {
            NodeUnlockAction.this.onLock(entity);
            after.onLock(entity);
         }
      };
   }
}
