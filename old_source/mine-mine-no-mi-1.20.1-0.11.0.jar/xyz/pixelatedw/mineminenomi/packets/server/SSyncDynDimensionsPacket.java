package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class SSyncDynDimensionsPacket {
   private Set<ResourceKey<Level>> addedDims;
   private Set<ResourceKey<Level>> removedDims;

   public SSyncDynDimensionsPacket() {
   }

   public SSyncDynDimensionsPacket(Set<ResourceKey<Level>> addedDims, Set<ResourceKey<Level>> removedDims) {
      this.addedDims = addedDims;
      this.removedDims = removedDims;
   }

   public void encode(FriendlyByteBuf buf) {
      buf.m_130130_(this.addedDims.size());
      this.addedDims.forEach((key) -> buf.m_130085_(key.m_135782_()));
      buf.m_130130_(this.removedDims.size());
      this.removedDims.forEach((key) -> buf.m_130085_(key.m_135782_()));
   }

   public static SSyncDynDimensionsPacket decode(FriendlyByteBuf buf) {
      SSyncDynDimensionsPacket msg = new SSyncDynDimensionsPacket();
      Set<ResourceKey<Level>> addedDims = new HashSet();
      Set<ResourceKey<Level>> removedDims = new HashSet();
      int addedSize = buf.m_130242_();

      for(int i = 0; i < addedSize; ++i) {
         ResourceLocation dim = buf.m_130281_();
         addedDims.add(ResourceKey.m_135785_(Registries.f_256858_, dim));
      }

      int removedSize = buf.m_130242_();

      for(int i = 0; i < removedSize; ++i) {
         ResourceLocation dim = buf.m_130281_();
         removedDims.add(ResourceKey.m_135785_(Registries.f_256858_, dim));
      }

      msg.addedDims = addedDims;
      msg.removedDims = removedDims;
      return msg;
   }

   public static void handle(SSyncDynDimensionsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSyncDynDimensionsPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSyncDynDimensionsPacket msg) {
         LocalPlayer player = Minecraft.m_91087_().f_91074_;
         if (player != null) {
            Set<ResourceKey<Level>> levels = player.f_108617_.m_105151_();
            levels.addAll(msg.addedDims);
            Set var10000 = msg.removedDims;
            Objects.requireNonNull(levels);
            var10000.forEach(levels::remove);
         }
      }
   }
}
