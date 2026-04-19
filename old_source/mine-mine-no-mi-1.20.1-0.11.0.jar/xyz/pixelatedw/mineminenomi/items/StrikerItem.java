package xyz.pixelatedw.mineminenomi.items;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class StrikerItem extends Item {
   private static final Predicate<Entity> ENTITY_PREDICATE;

   public StrikerItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      ItemStack heldItem = player.m_21120_(InteractionHand.MAIN_HAND);
      HitResult hitResult = m_41435_(level, player, Fluid.ANY);
      if (hitResult.m_6662_() == Type.MISS) {
         return InteractionResultHolder.m_19098_(heldItem);
      } else {
         Vec3 viewVec = player.m_20252_(1.0F);
         double scale = (double)5.0F;
         List<Entity> list = level.m_6249_(player, player.m_20191_().m_82369_(viewVec.m_82490_(scale)).m_82400_((double)1.0F), ENTITY_PREDICATE);
         if (!list.isEmpty()) {
            Vec3 vector3d1 = player.m_20299_(1.0F);

            for(Entity entity : list) {
               AABB axisalignedbb = entity.m_20191_().m_82400_((double)entity.m_6143_());
               if (axisalignedbb.m_82390_(vector3d1)) {
                  return InteractionResultHolder.m_19098_(heldItem);
               }
            }
         }

         if (hitResult.m_6662_() == Type.BLOCK) {
            StrikerEntity striker = new StrikerEntity(level);
            striker.m_6034_(hitResult.m_82450_().f_82479_, hitResult.m_82450_().f_82480_, hitResult.m_82450_().f_82481_);
            striker.m_146922_(player.m_146908_());
            if (!level.m_45756_(striker, striker.m_20191_().m_82400_(-0.1))) {
               return InteractionResultHolder.m_19100_(heldItem);
            } else {
               if (!level.f_46443_) {
                  level.m_7967_(striker);
                  if (!player.m_150110_().f_35937_) {
                     heldItem.m_41774_(1);
                  }
               }

               player.m_36246_(Stats.f_12982_.m_12902_(this));
               return InteractionResultHolder.m_19092_(heldItem, level.m_5776_());
            }
         } else {
            return InteractionResultHolder.m_19098_(heldItem);
         }
      }
   }

   static {
      ENTITY_PREDICATE = EntitySelector.f_20408_.and(Entity::m_6087_);
   }
}
