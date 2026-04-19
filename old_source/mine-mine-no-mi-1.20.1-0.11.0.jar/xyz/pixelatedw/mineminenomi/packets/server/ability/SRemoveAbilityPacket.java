package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class SRemoveAbilityPacket {
   private int entityId;
   private int slot;

   public SRemoveAbilityPacket() {
   }

   public SRemoveAbilityPacket(int entityId, int slot) {
      this.entityId = entityId;
      this.slot = slot;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.slot);
   }

   public static SRemoveAbilityPacket decode(FriendlyByteBuf buffer) {
      SRemoveAbilityPacket msg = new SRemoveAbilityPacket();
      msg.entityId = buffer.readInt();
      msg.slot = buffer.readInt();
      return msg;
   }

   public static void handle(SRemoveAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SRemoveAbilityPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SRemoveAbilityPacket message) {
         try {
            ClientLevel world = Minecraft.m_91087_().f_91073_;
            Entity target = world.m_6815_(message.entityId);
            if (target != null && target instanceof LivingEntity living) {
               AbilityCapability.get(living).ifPresent((props) -> props.setEquippedAbility(message.slot, (IAbility)null));
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}
