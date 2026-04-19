package xyz.pixelatedw.mineminenomi.data.entity.quest;

import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class QuestCapability implements ICapabilitySerializable<CompoundTag> {
   public static final Capability<IQuestData> INSTANCE = CapabilityManager.get(new CapabilityToken<IQuestData>() {
   });
   private final IQuestData instance;

   public QuestCapability(Player owner) {
      this.instance = new QuestDataBase(owner);
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

   public static LazyOptional<IQuestData> getLazy(Player entity) {
      return entity.getCapability(INSTANCE, (Direction)null);
   }

   public static Optional<IQuestData> get(Player entity) {
      return getLazy(entity).resolve();
   }
}
