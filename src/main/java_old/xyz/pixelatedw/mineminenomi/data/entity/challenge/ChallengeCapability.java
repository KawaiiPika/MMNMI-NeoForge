package xyz.pixelatedw.mineminenomi.data.entity.challenge;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ChallengeCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IChallengeData> INSTANCE = CapabilityManager.get(new CapabilityToken<IChallengeData>() {
   });
   private final IChallengeData instance;

   public ChallengeCapability(Player owner) {
      this.instance = new ChallengeDataBase(owner);
   }

   public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
      return INSTANCE.orEmpty(cap, LazyOptional.of(() -> this.instance));
   }

   public CompoundTag serializeNBT() {
      return (CompoundTag)this.instance.serializeNBT();
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.instance.deserializeNBT(nbt);
   }

   public static LazyOptional<IChallengeData> getLazy(Player player) {
      return player.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IChallengeData> get(Player player) {
      return getLazy(player).resolve();
   }
}
