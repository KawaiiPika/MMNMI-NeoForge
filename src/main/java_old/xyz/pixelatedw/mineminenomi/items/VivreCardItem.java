package xyz.pixelatedw.mineminenomi.items;

import com.google.common.base.Strings;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;

public class VivreCardItem extends Item {
   public VivreCardItem() {
      super((new Item.Properties()).m_41487_(1));
   }

   public boolean onDroppedByPlayer(ItemStack itemStack, Player player) {
      VivreCardEntity vivreCard = new VivreCardEntity(player.m_9236_());
      if (itemStack.m_41783_() == null) {
         return true;
      } else {
         LivingEntity owner = (LivingEntity)((ServerLevel)player.m_9236_()).m_8791_(UUID.fromString(itemStack.m_41783_().m_128461_("ownerUUID")));
         if (owner == null) {
            return true;
         } else {
            vivreCard.m_7678_(player.m_20185_(), player.m_20186_(), player.m_20189_(), player.m_146908_(), player.m_146909_());
            float f8 = Mth.m_14031_(player.m_146909_() * ((float)Math.PI / 180F));
            float f2 = Mth.m_14089_(player.m_146909_() * ((float)Math.PI / 180F));
            float f3 = Mth.m_14031_(player.m_146908_() * ((float)Math.PI / 180F));
            float f4 = Mth.m_14089_(player.m_146908_() * ((float)Math.PI / 180F));
            float f5 = player.m_217043_().m_188501_() * ((float)Math.PI * 2F);
            float f6 = 0.02F * player.m_217043_().m_188501_();
            vivreCard.m_20334_((double)(-f3 * f2 * 0.3F) + Math.cos((double)f5) * (double)f6, (double)(-f8 * 0.3F + 0.1F + (player.m_217043_().m_188501_() - player.m_217043_().m_188501_()) * 0.1F), (double)(f4 * f2 * 0.3F) + Math.sin((double)f5) * (double)f6);
            vivreCard.setOwner(owner.m_20148_());
            player.m_9236_().m_7967_(vivreCard);
            itemStack.m_41764_(0);
            return false;
         }
      }
   }

   public void m_6883_(ItemStack itemStack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      if (!world.f_46443_) {
         if (itemStack.m_41783_() != null) {
            String uuidString = itemStack.m_41783_().m_128461_("ownerUUID");
            if (Strings.isNullOrEmpty(uuidString)) {
               return;
            }

            LivingEntity owner = (LivingEntity)((ServerLevel)world).m_8791_(UUID.fromString(uuidString));
            if (owner != null && owner.m_21223_() <= 0.0F) {
               itemStack.m_41764_(0);
            }
         } else if (itemStack.m_41783_() == null || itemStack.m_41783_().m_128456_()) {
            this.setOwner(itemStack, (LivingEntity)entity);
         }

      }
   }

   public void setOwner(ItemStack itemStack, LivingEntity entity) {
      itemStack.m_41784_().m_128359_("ownerUUID", entity.m_20148_().toString());
      itemStack.m_41784_().m_128359_("ownerUsername", entity.m_5446_().getString());
      String itemName = itemStack.m_41786_().getString();
      String var10001 = String.valueOf(ChatFormatting.RESET);
      itemStack.m_41714_(Component.m_237113_(var10001 + entity.m_5446_().getString() + "'s " + itemName));
   }
}
