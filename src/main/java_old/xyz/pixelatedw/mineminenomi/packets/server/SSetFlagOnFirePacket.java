package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.blocks.FlagBlock;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.FlagBlockEntity;

public class SSetFlagOnFirePacket {
   private BlockPos pos;
   private boolean value;

   public SSetFlagOnFirePacket() {
   }

   public SSetFlagOnFirePacket(BlockPos pos, boolean value) {
      this.pos = pos;
      this.value = value;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130064_(this.pos);
      buffer.writeBoolean(this.value);
   }

   public static SSetFlagOnFirePacket decode(FriendlyByteBuf buffer) {
      SSetFlagOnFirePacket msg = new SSetFlagOnFirePacket();
      msg.pos = buffer.m_130135_();
      msg.value = buffer.readBoolean();
      return msg;
   }

   public static void handle(SSetFlagOnFirePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetFlagOnFirePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetFlagOnFirePacket message) {
         ClientLevel world = Minecraft.m_91087_().f_91073_;
         BlockState state = world.m_8055_(message.pos);
         if (state.m_60734_() instanceof FlagBlock) {
            BlockEntity tileEntity = world.m_7702_(message.pos);
            if (tileEntity != null && tileEntity instanceof FlagBlockEntity) {
               FlagBlockEntity flagTile = (FlagBlockEntity)tileEntity;
               flagTile.setOnFire(message.value);
            }
         }

      }
   }
}
