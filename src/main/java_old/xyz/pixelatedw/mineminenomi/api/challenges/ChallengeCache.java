package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class ChallengeCache {
   private CompoundTag playerData;

   public static ChallengeCache from(LivingEntity entity) {
      ChallengeCache cache = new ChallengeCache();
      cache.playerData = entity.m_20240_(new CompoundTag());
      return cache;
   }

   public static ChallengeCache from(CompoundTag nbt) {
      ChallengeCache cache = new ChallengeCache();
      cache.load(nbt);
      return cache;
   }

   public void restore(LivingEntity entity) {
      entity.m_20258_(this.playerData);
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128365_("playerData", this.playerData);
      return nbt;
   }

   public void load(CompoundTag nbt) {
      this.playerData = nbt.m_128469_("playerData");
   }
}
