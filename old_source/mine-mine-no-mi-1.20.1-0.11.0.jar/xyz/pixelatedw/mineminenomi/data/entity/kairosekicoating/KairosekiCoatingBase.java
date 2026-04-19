package xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SKairosekiCoatingPacket;

public class KairosekiCoatingBase implements IKairosekiCoating {
   public static final int MAX_COATING_AMOUNT = 5;
   private Entity owner;
   private int coatingLevel;

   public KairosekiCoatingBase(Entity owner) {
      this.owner = owner;
   }

   public boolean isFullyCoated() {
      return this.coatingLevel >= 5;
   }

   public int getCoatingLevel() {
      return this.coatingLevel;
   }

   public boolean addCoatingLevel(int levels) {
      if (levels > 0 && this.isFullyCoated()) {
         return false;
      } else if (levels < 0 && this.getCoatingLevel() == 0) {
         return false;
      } else {
         this.setCoatingLevel(this.coatingLevel + levels);
         return true;
      }
   }

   public void setCoatingLevel(int level) {
      this.coatingLevel = Mth.m_14045_(level, 0, 5);
      if (this.owner != null && !this.owner.m_9236_().f_46443_) {
         ModNetwork.sendToAllTracking(new SKairosekiCoatingPacket(this.owner.m_19879_(), this.coatingLevel), this.owner);
      }

   }

   public CompoundTag serializeNBT() {
      CompoundTag props = new CompoundTag();
      props.m_128405_("coatingLevel", this.getCoatingLevel());
      return props;
   }

   public void deserializeNBT(CompoundTag props) {
      this.setCoatingLevel(props.m_128451_("coatingLevel"));
   }
}
