package xyz.pixelatedw.mineminenomi.data.entity.hisolookout;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Direction8;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class HisoLookoutBase implements IHisoLookoutData {
   private Animal owner;
   private Map<UUID, IHisoLookoutData.HisoLookoutTarget> lookoutSet = new HashMap();

   public IHisoLookoutData setOwner(Animal owner) {
      this.owner = owner;
      return this;
   }

   public Map<UUID, IHisoLookoutData.HisoLookoutTarget> getSeenTargets() {
      return this.lookoutSet;
   }

   public void registerSeenTarget(LivingEntity target) {
      Direction8 dir = WyHelper.getLookDirection(target);
      IHisoLookoutData.HisoLookoutTarget data = new IHisoLookoutData.HisoLookoutTarget(target.m_7755_().getString(), target.m_9236_().m_46467_(), dir);
      this.lookoutSet.put(target.m_20148_(), data);
   }

   public void removeSeenTarget(UUID id) {
      this.lookoutSet.remove(id);
   }

   public void clearSeenTargets() {
      this.lookoutSet.clear();
   }

   public CompoundTag serializeNBT() {
      return new CompoundTag();
   }

   public void deserializeNBT(CompoundTag nbt) {
   }
}
