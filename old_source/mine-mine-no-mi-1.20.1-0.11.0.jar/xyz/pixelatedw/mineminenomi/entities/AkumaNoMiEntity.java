package xyz.pixelatedw.mineminenomi.entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class AkumaNoMiEntity extends ItemEntity {
   private int playerCounter;

   public AkumaNoMiEntity(EntityType type, Level world) {
      super(type, world);
      this.playerCounter = 0;
   }

   public AkumaNoMiEntity(Level world) {
      super((EntityType)ModEntities.DEVIL_FRUIT_ITEM.get(), world);
      this.playerCounter = 0;
   }

   public AkumaNoMiEntity(Level world, double x, double y, double z, ItemStack stack) {
      this(world);
      this.m_32045_(stack);
      this.lifespan = stack.m_41720_() == null ? 6000 : stack.getEntityLifespan(world);
      this.m_6034_(x, y, z);
      this.m_146926_(this.f_19796_.m_188501_() * 360.0F);
   }

   public void m_142687_(Entity.RemovalReason reason) {
      if (this.m_32055_().m_41720_() instanceof AkumaNoMiItem && !this.m_32055_().m_41619_() && !this.m_9236_().f_46443_ && reason.m_146965_()) {
         OneFruitWorldData.get().lostOneFruit(((AkumaNoMiItem)this.m_32055_().m_41720_()).getRegistryKey(), (LivingEntity)null, "Dropped item got destroyed");
      }

      super.m_142687_(reason);
   }

   public void m_6123_(Player player) {
      super.m_6123_(player);
      if (!player.m_9236_().f_46443_ && !this.m_32063_()) {
         this.playerCounter = -1;
      }

   }

   protected void m_20157_() {
   }

   public void m_6457_(ServerPlayer player) {
      ++this.playerCounter;
   }

   public void m_6452_(ServerPlayer player) {
      if (this.playerCounter > 0 && this.playerCounter - 1 <= 0) {
         if (!(this.m_32055_().m_41720_() instanceof AkumaNoMiItem)) {
            return;
         }

         if (ServerConfig.hasChunkUnloadingRealisticLogic()) {
            OneFruitPerWorldHandler.unloadRealistic((AkumaNoMiItem)this.m_32055_().m_41720_());
         }

         if (ServerConfig.hasChunkUnloadingInstantLogic()) {
            OneFruitPerWorldHandler.unloadInstant((AkumaNoMiItem)this.m_32055_().m_41720_());
         }
      }

   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
