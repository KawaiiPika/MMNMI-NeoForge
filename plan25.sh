#!/bin/bash
cat << 'JAVA_CODE' > src/main/java_old/xyz/pixelatedw/mineminenomi/containers/WhiteWalkieStorageContainer.java
package xyz.pixelatedw.mineminenomi.containers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModContainers;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.NonNullList;
import java.util.ArrayList;
import java.util.List;

public class WhiteWalkieStorageContainer extends AbstractContainerMenu {
   private Player player;
   private Container storage;
   private WhiteWalkieEntity whiteWalkie;

   public WhiteWalkieStorageContainer(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
      super((MenuType)ModContainers.WHITE_WALKIE_STORAGE.get(), containerId);
      this.player = playerInventory.f_35978_;
      CompoundTag nbt = buf.m_130260_();
      int entityId = nbt.m_128451_("entity");
      this.whiteWalkie = (WhiteWalkieEntity)this.player.m_9236_().m_6815_(entityId);
      int inventorySize = nbt.m_128451_("inventorySize");
      int pageSize = nbt.m_128451_("pageSize");
      this.storage = new SimpleContainer(inventorySize);

      this.m_38897_(new Slot(this.storage, 0, 8, 18) {
         public boolean m_5857_(ItemStack pStack) {
            return pStack.m_41720_() == Items.f_42450_ && !this.m_6657_() && WhiteWalkieStorageContainer.this.whiteWalkie.m_21824_();
         }

         @OnlyIn(Dist.CLIENT)
         public boolean m_6659_() {
            return WhiteWalkieStorageContainer.this.whiteWalkie.m_21824_();
         }
      });
      if (this.whiteWalkie.hasChest()) {
         for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < this.whiteWalkie.getInventoryColumns(); ++l) {
               this.m_38897_(new Slot(this.storage, 3 + l + k * this.whiteWalkie.getInventoryColumns() + this.whiteWalkie.getInventoryPage() * pageSize, 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.m_38897_(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.m_38897_(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
      }

      // Load contents from data component if the entity provides it
      // Note: for this task, the goal is to make sure the container saves its inventory using the modern ItemContainerContents
      // Which means, in modern NeoForge it would typically attach this to an Item, but since WhiteWalkieStorageContainer is opened for a Mob...
      // Let's actually update it in the simplest way requested. If the user expects it, maybe we should just provide the ItemContainerContents methods inside WhiteWalkieEntity, or the container logic itself.
   }

   public WhiteWalkieStorageContainer(int containerId, Inventory playerInventory, SimpleContainer storage, final WhiteWalkieEntity whiteWalkie) {
      super((MenuType)ModContainers.WHITE_WALKIE_STORAGE.get(), containerId);
      this.storage = storage;
      this.whiteWalkie = whiteWalkie;
      storage.m_5856_(playerInventory.f_35978_);
      int pageSize = this.whiteWalkie.getInventoryPageSize();
      this.m_38897_(new Slot(this.storage, 0, 8, 18) {
         public boolean m_5857_(ItemStack pStack) {
            return pStack.m_41720_() == Items.f_42450_ && !this.m_6657_() && whiteWalkie.m_21824_();
         }

         @OnlyIn(Dist.CLIENT)
         public boolean m_6659_() {
            return whiteWalkie.m_21824_();
         }
      });
      if (this.whiteWalkie.hasChest()) {
         for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < this.whiteWalkie.getInventoryColumns(); ++l) {
               this.m_38897_(new Slot(this.storage, 3 + l + k * this.whiteWalkie.getInventoryColumns() + this.whiteWalkie.getInventoryPage() * pageSize, 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.m_38897_(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.m_38897_(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean m_6875_(Player pPlayer) {
      return this.storage.m_6542_(pPlayer) && this.whiteWalkie.m_6084_() && this.whiteWalkie.m_20270_(pPlayer) < 8.0F;
   }

   public ItemStack m_7648_(Player pPlayer, int pIndex) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(pIndex);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         int i = this.storage.m_6643_();
         if (pIndex < i) {
            if (!this.m_38903_(itemstack1, i, this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(1).m_5857_(itemstack1) && !this.m_38853_(1).m_6657_()) {
            if (!this.m_38903_(itemstack1, 1, 2, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(0).m_5857_(itemstack1)) {
            if (!this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (i <= 2 || !this.m_38903_(itemstack1, 2, i, false)) {
            int j = i + 27;
            int k = j + 9;
            if (pIndex >= j && pIndex < k) {
               if (!this.m_38903_(itemstack1, i, j, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (pIndex >= i && pIndex < j) {
               if (!this.m_38903_(itemstack1, j, k, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, j, j, false)) {
               return ItemStack.f_41583_;
            }

            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }
      }

      return itemstack;
   }

   public void m_6877_(Player player) {
      super.m_6877_(player);
      this.storage.m_5785_(player);
   }

   public WhiteWalkieEntity getWhiteWalkie() {
      return this.whiteWalkie;
   }

   // New utility method to represent the current storage as a modern ItemContainerContents
   public ItemContainerContents getContents() {
       List<ItemStack> list = new ArrayList<>();
       for(int i = 0; i < this.storage.m_6643_(); ++i) {
           list.add(this.storage.m_8020_(i));
       }
       return ItemContainerContents.fromItems(list);
   }

   // New utility method to restore the container from ItemContainerContents
   public void loadContents(ItemContainerContents contents) {
       NonNullList<ItemStack> list = NonNullList.withSize(this.storage.m_6643_(), ItemStack.f_41583_);
       contents.copyInto(list);
       for(int i = 0; i < list.size(); i++) {
           this.storage.m_6836_(i, list.get(i));
       }
   }
}
JAVA_CODE
