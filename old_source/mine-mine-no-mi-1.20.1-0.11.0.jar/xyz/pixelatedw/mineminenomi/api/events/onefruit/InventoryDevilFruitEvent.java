package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.EntityEvent;
import org.jetbrains.annotations.Nullable;

public class InventoryDevilFruitEvent extends EntityEvent {
   private final Item devilFruit;
   private final String reason;

   public InventoryDevilFruitEvent(@Nullable LivingEntity entity, @Nonnull Item devilFruit, String reason) {
      super(entity);
      this.devilFruit = devilFruit;
      this.reason = reason;
   }

   public Item getItem() {
      return this.devilFruit;
   }

   public String getReason() {
      return this.reason;
   }
}
