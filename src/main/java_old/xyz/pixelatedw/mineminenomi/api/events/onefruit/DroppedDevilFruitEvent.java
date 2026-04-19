package xyz.pixelatedw.mineminenomi.api.events.onefruit;

import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

public class DroppedDevilFruitEvent extends EntityEvent {
   private final Item devilFruit;
   private final String reason;
   private final BlockPos location;
   private final ResourceLocation dimensionid;

   public DroppedDevilFruitEvent(@Nullable LivingEntity entity, @Nonnull Item devilFruit, @Nullable ResourceLocation dimensionId, @Nullable BlockPos location, String reason) {
      super(entity);
      this.devilFruit = devilFruit;
      this.location = location;
      this.dimensionid = dimensionId;
      this.reason = reason;
   }

   public Item getItem() {
      return this.devilFruit;
   }

   public BlockPos getLocation() {
      return this.location;
   }

   public Level getDimension() {
      return this.dimensionid == null ? null : ServerLifecycleHooks.getCurrentServer().m_129880_(ResourceKey.m_135785_(Registries.f_256858_, this.dimensionid));
   }

   public String getReason() {
      return this.reason;
   }
}
