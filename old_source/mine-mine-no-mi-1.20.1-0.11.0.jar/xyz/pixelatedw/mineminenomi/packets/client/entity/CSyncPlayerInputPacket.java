package xyz.pixelatedw.mineminenomi.packets.client.entity;

import io.netty.handler.codec.EncoderException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.function.Supplier;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class CSyncPlayerInputPacket {
   private float xxa;
   private float zza;
   private boolean isJumping;

   public CSyncPlayerInputPacket() {
   }

   public CSyncPlayerInputPacket(LocalPlayer player) {
      this.xxa = player.f_20900_;
      this.zza = player.f_20902_;
      this.isJumping = player.f_108618_.f_108572_;
   }

   public void encode(FriendlyByteBuf buffer) {
      BitSet bitSet = new BitSet(5);
      bitSet.set(0, this.xxa > 0.0F);
      bitSet.set(1, this.xxa < 0.0F);
      bitSet.set(2, this.zza > 0.0F);
      bitSet.set(3, this.zza < 0.0F);
      bitSet.set(4, this.isJumping);
      writeFixedBitSet(buffer, bitSet, 5);
   }

   public static CSyncPlayerInputPacket decode(FriendlyByteBuf buffer) {
      CSyncPlayerInputPacket message = new CSyncPlayerInputPacket();
      BitSet bitSet = readFixedBitSet(buffer, 5);
      int xxaBits = (bitSet.get(0) ? 1 : 0) | (bitSet.get(1) ? 2 : 0);
      int zzaBits = (bitSet.get(2) ? 1 : 0) | (bitSet.get(3) ? 2 : 0);
      message.xxa = xxaBits == 2 ? -1.0F : (float)xxaBits;
      message.zza = zzaBits == 2 ? -1.0F : (float)zzaBits;
      message.isJumping = bitSet.get(4);
      return message;
   }

   public static void handle(CSyncPlayerInputPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayer entity = ((NetworkEvent.Context)ctx.get()).getSender();
            entity.f_20900_ = message.xxa;
            entity.f_20902_ = message.zza;
            entity.m_6862_(message.isJumping);
            EntityStatsCapability.get(entity).ifPresent((props) -> {
               props.setLeftImpulse(message.xxa);
               props.setForwardImpulse(message.zza);
               props.setJumping(message.isJumping);
            });
         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static BitSet readBitSet(FriendlyByteBuf buffer) {
      return BitSet.valueOf(buffer.m_130105_((long[])null));
   }

   public static void writeBitSet(FriendlyByteBuf buffer, BitSet bits) {
      buffer.m_130091_(bits.toLongArray());
   }

   public static BitSet readFixedBitSet(FriendlyByteBuf buffer, int toWrite) {
      byte[] abyte = new byte[-Math.floorDiv(-toWrite, 8)];
      buffer.readBytes(abyte);
      return BitSet.valueOf(abyte);
   }

   public static void writeFixedBitSet(FriendlyByteBuf buffer, BitSet bits, int toWrite) {
      if (bits.length() > toWrite) {
         int var10002 = bits.length();
         throw new EncoderException("BitSet is larger than expected size (" + var10002 + ">" + toWrite + ")");
      } else {
         byte[] abyte = bits.toByteArray();
         buffer.writeBytes(Arrays.copyOf(abyte, -Math.floorDiv(-toWrite, 8)));
      }
   }
}
