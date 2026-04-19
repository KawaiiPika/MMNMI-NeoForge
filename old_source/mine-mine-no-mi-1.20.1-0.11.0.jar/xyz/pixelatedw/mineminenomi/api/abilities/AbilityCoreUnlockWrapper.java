package xyz.pixelatedw.mineminenomi.api.abilities;

import java.time.Instant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;

public class AbilityCoreUnlockWrapper<A extends IAbility> {
   private final AbilityCore<A> abilityCore;
   private final AbilityUnlock unlockedMethod;
   private final long unlockedRealTimestamp;
   private final long unlockedGameTimestamp;

   public AbilityCoreUnlockWrapper(LivingEntity entity, AbilityCore<A> abilityCore, AbilityUnlock unlockedMethod) {
      this.abilityCore = abilityCore;
      this.unlockedMethod = unlockedMethod;
      this.unlockedRealTimestamp = Instant.now().getEpochSecond();
      this.unlockedGameTimestamp = entity.m_9236_().m_46467_();
   }

   private AbilityCoreUnlockWrapper(AbilityCore<A> abilityCore, AbilityUnlock unlockedMethod, long unlockedRealTimestamp, long unlockedGameTimestamp) {
      this.abilityCore = abilityCore;
      this.unlockedMethod = unlockedMethod;
      this.unlockedRealTimestamp = unlockedRealTimestamp;
      this.unlockedGameTimestamp = unlockedGameTimestamp;
   }

   public AbilityCore<A> getAbilityCore() {
      return this.abilityCore;
   }

   public AbilityUnlock getUnlockType() {
      return this.unlockedMethod;
   }

   public long getRealTimestamp() {
      return this.unlockedRealTimestamp;
   }

   public long getGameTimestamp() {
      return this.unlockedGameTimestamp;
   }

   public CompoundTag save() {
      CompoundTag nbt = new CompoundTag();
      nbt.m_128359_("id", this.abilityCore.getRegistryKey().toString());
      nbt.m_128359_("unlockedMethod", this.unlockedMethod.name());
      nbt.m_128356_("realTimestamp", this.unlockedRealTimestamp);
      nbt.m_128356_("gameTimestamp", this.unlockedGameTimestamp);
      return nbt;
   }

   public static <A extends IAbility> AbilityCoreUnlockWrapper<A> of(CompoundTag nbt) {
      AbilityCore<A> abilityCore = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(ResourceLocation.parse(nbt.m_128461_("id")));
      AbilityUnlock unlockedMethod = AbilityUnlock.valueOf(nbt.m_128461_("unlockedMethod"));
      long unlockedRealTimestamp = nbt.m_128454_("realTimestamp");
      long unlockedGameTimestamp = nbt.m_128454_("gameTimestamp");
      AbilityCoreUnlockWrapper<A> details = new AbilityCoreUnlockWrapper<A>(abilityCore, unlockedMethod, unlockedRealTimestamp, unlockedGameTimestamp);
      return details;
   }
}
