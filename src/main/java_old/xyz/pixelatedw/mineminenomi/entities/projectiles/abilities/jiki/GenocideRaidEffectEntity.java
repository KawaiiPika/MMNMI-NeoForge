package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.abilities.jiki.JikiHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class GenocideRaidEffectEntity extends Entity implements IEntityAdditionalSpawnData {
   private LivingEntity owner;
   private LivingEntity target;
   private List<ItemStack> magneticItems = new ArrayList();

   public GenocideRaidEffectEntity(EntityType<? extends GenocideRaidEffectEntity> type, Level world) {
      super(type, world);
      this.m_20242_(true);
   }

   public GenocideRaidEffectEntity(Level level) {
      super((EntityType)ModProjectiles.GENOCIDE_RAID_EFFECT.get(), level);
      this.m_20242_(true);
   }

   protected void m_8097_() {
   }

   public void m_8119_() {
      if (!this.m_9236_().f_46443_) {
         if (this.f_19797_ > 100) {
            this.m_6074_();
            return;
         }

         if (this.owner == null) {
            this.m_146870_();
            return;
         }

         if (this.target == null || !this.target.m_6084_()) {
            this.m_6074_();
            return;
         }

         this.m_6021_(this.target.m_20185_(), this.target.m_20186_() + (double)1.0F, this.target.m_20189_());
      }

      super.m_8119_();
   }

   public void m_6074_() {
      if (this.owner != null) {
         ItemStack stack = (ItemStack)this.magneticItems.get(0);
         ItemsHelper.itemBreakParticles(this.m_9236_(), this.m_20182_(), 20, stack);
         this.m_9236_().m_5594_((Player)null, this.m_20183_(), SoundEvents.f_12018_, this.m_5720_(), 0.5F, 1.0F);
         JikiHelper.dropComponentItems(this.m_9236_(), this.m_20182_(), this.magneticItems);
      }

      super.m_6074_();
   }

   protected void m_7378_(CompoundTag pCompound) {
   }

   protected void m_7380_(CompoundTag pCompound) {
   }

   public void setOwner(LivingEntity owner) {
      this.owner = owner;
   }

   public void setTarget(LivingEntity target) {
      this.target = target;
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public LivingEntity getOwner() {
      return this.owner;
   }

   public void setItemsUsed(List<ItemStack> items) {
      Collections.shuffle(items);
      this.magneticItems = items;
   }

   public List<ItemStack> getItemsUsed() {
      return this.magneticItems;
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeInt(this.target != null ? this.target.m_19879_() : -1);
      buffer.writeInt(this.magneticItems.size());

      for(ItemStack stack : this.magneticItems) {
         buffer.writeItemStack(stack, true);
      }

   }

   public void readSpawnData(FriendlyByteBuf buffer) {
      int targetId = buffer.readInt();
      if (targetId >= 0) {
         Entity target = this.m_9236_().m_6815_(targetId);
         if (target != null && target instanceof LivingEntity) {
            this.target = (LivingEntity)target;
         }
      }

      int size = buffer.readInt();
      this.magneticItems.clear();

      for(int i = 0; i < size; ++i) {
         ItemStack stack = buffer.m_130267_();
         this.magneticItems.add(stack);
      }

   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
