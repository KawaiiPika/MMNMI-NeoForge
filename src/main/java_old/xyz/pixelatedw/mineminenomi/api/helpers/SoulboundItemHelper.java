package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class SoulboundItemHelper {
   @Nullable
   public static Pair<UUID, LivingEntity> getOwner(Level world, ItemStack itemStack) {
      UUID uuid = itemStack.m_41784_().m_128342_("ownerUUID");
      return ImmutablePair.of(uuid, (LivingEntity)((ServerLevel)world).m_8791_(uuid));
   }

   @Nullable
   public static UUID getOwnerUUID(Level world, ItemStack itemStack) {
      CompoundTag tag = itemStack.m_41784_();
      return !tag.m_128403_("ownerUUID") ? null : tag.m_128342_("ownerUUID");
   }

   public static void setOwner(ItemStack itemStack, LivingEntity owner) {
      itemStack.m_41784_().m_128362_("ownerUUID", owner.m_20148_());
   }
}
