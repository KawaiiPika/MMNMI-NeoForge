package xyz.pixelatedw.mineminenomi.data.entity.hisolookout;

import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Direction8;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHisoLookoutData extends INBTSerializable<CompoundTag> {
   IHisoLookoutData setOwner(Animal var1);

   Map<UUID, HisoLookoutTarget> getSeenTargets();

   void registerSeenTarget(LivingEntity var1);

   void removeSeenTarget(UUID var1);

   void clearSeenTargets();

   public static record HisoLookoutTarget(String username, long time, Direction8 direction) {
      public long passedMinutes(long currentGameTime) {
         return (currentGameTime - this.time) / 20L / 60L;
      }
   }
}
