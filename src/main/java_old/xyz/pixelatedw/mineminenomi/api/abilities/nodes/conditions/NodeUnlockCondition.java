package xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions;

import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

public interface NodeUnlockCondition {
   boolean test(LivingEntity var1);

   default NodeUnlockCondition and(final NodeUnlockCondition other) {
      Objects.requireNonNull(other);
      NodeUnlockCondition cond = new NodeUnlockCondition() {
         public boolean test(LivingEntity t) {
            return NodeUnlockCondition.this.test(t) && other.test(t);
         }

         public MutableComponent getTooltip() {
            MutableComponent comp = NodeUnlockCondition.this.getTooltip();
            Component otherTooltip = other.getTooltip();
            if (otherTooltip != null && !otherTooltip.equals(Component.m_237119_())) {
               comp.m_7220_(Component.m_237113_("\n"));
               comp.m_7220_(other.getTooltip());
            }

            return comp;
         }
      };
      return cond;
   }

   MutableComponent getTooltip();
}
