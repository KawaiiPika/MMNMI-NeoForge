package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;

public class SUpdateCombatStatePacket {
   private boolean hasAttacker;
   private int attackerId = -1;

   public SUpdateCombatStatePacket() {
   }

   public SUpdateCombatStatePacket(@Nullable LivingEntity attacker) {
      this.hasAttacker = attacker != null;
      this.attackerId = attacker != null ? attacker.m_19879_() : -1;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.hasAttacker);
      buffer.writeInt(this.attackerId);
   }

   public static SUpdateCombatStatePacket decode(FriendlyByteBuf buffer) {
      SUpdateCombatStatePacket msg = new SUpdateCombatStatePacket();
      msg.hasAttacker = buffer.readBoolean();
      if (msg.hasAttacker) {
         msg.attackerId = buffer.readInt();
      } else {
         msg.attackerId = -1;
      }

      return msg;
   }

   public static void handle(SUpdateCombatStatePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateCombatStatePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateCombatStatePacket message) {
         LocalPlayer player = Minecraft.m_91087_().f_91074_;
         ICombatData props = (ICombatData)CombatCapability.get(player).orElse((Object)null);
         if (props != null) {
            if (message.hasAttacker && message.attackerId >= 0) {
               Entity target = player.m_9236_().m_6815_(message.attackerId);
               if (target instanceof LivingEntity) {
                  props.setInCombatCache((LivingEntity)target);
               }
            } else {
               props.setInCombatCache((LivingEntity)null);
            }

         }
      }
   }
}
