package xyz.pixelatedw.mineminenomi.ui.events;

import com.google.common.primitives.Ints;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SScreenDataEventPacket;

public class ChallengesScreenEvent implements INBTSerializable<CompoundTag> {
   private PossibleInvites possibleInvites = null;

   public PossibleInvites getPossibleInvites() {
      return this.possibleInvites;
   }

   public void setPossibleInvites(int slot, List<Integer> list) {
      this.possibleInvites = new PossibleInvites(slot, Ints.toArray(list));
   }

   public void sendEvent(ServerPlayer player) {
      ModNetwork.sendTo(new SScreenDataEventPacket(this), player);
   }

   public CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      tag.m_128365_("possibleInvites", this.possibleInvites.serializeNBT());
      return tag;
   }

   public void deserializeNBT(CompoundTag nbt) {
      this.possibleInvites = new PossibleInvites();
      this.possibleInvites.deserializeNBT(nbt.m_128469_("possibleInvites"));
   }

   public class PossibleInvites implements INBTSerializable<CompoundTag> {
      private int slot;
      private int[] ids;

      private PossibleInvites() {
      }

      public PossibleInvites(int slot, int[] ids) {
         this.slot = slot;
         this.ids = ids;
      }

      public int getSlot() {
         return this.slot;
      }

      public int[] getIds() {
         return this.ids;
      }

      public CompoundTag serializeNBT() {
         CompoundTag tag = new CompoundTag();
         tag.m_128405_("slot", this.slot);
         tag.m_128385_("ids", this.ids);
         return tag;
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.slot = nbt.m_128451_("slot");
         this.ids = nbt.m_128465_("ids");
      }
   }
}
