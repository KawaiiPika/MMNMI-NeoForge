package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.projectileextra.IProjectileExtras;
import xyz.pixelatedw.mineminenomi.data.entity.projectileextra.ProjectileExtrasCapability;

public class SSyncProjectileExtrasPacket {
   private int entityId;
   private CompoundTag data;

   public SSyncProjectileExtrasPacket() {
   }

   public SSyncProjectileExtrasPacket(Projectile projectile) {
      this(projectile, ProjectileExtrasCapability.get(projectile));
   }

   public SSyncProjectileExtrasPacket(Projectile projectile, Optional<IProjectileExtras> lazyOptional) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)lazyOptional.map((data) -> (CompoundTag)data.serializeNBT()).orElse(new CompoundTag());
      this.entityId = projectile.m_19879_();
   }

   public SSyncProjectileExtrasPacket(Projectile projectile, IProjectileExtras props) {
      this.data = new CompoundTag();
      this.data = (CompoundTag)props.serializeNBT();
      this.entityId = projectile.m_19879_();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.data);
   }

   public static SSyncProjectileExtrasPacket decode(FriendlyByteBuf buffer) {
      SSyncProjectileExtrasPacket msg = new SSyncProjectileExtrasPacket();
      msg.entityId = buffer.readInt();
      msg.data = buffer.m_130260_();
      return msg;
   }

   public static void handle(SSyncProjectileExtrasPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncProjectileExtrasPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncProjectileExtrasPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof Projectile proj) {
            IProjectileExtras props = (IProjectileExtras)ProjectileExtrasCapability.get(proj).orElse((Object)null);
            if (props != null) {
               ProjectileExtrasCapability.get(proj).ifPresent((p) -> p.deserializeNBT(message.data));
            }
         }
      }
   }
}
