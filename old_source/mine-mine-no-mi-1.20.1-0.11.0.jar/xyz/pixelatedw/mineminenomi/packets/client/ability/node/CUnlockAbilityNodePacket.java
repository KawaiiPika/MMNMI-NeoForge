package xyz.pixelatedw.mineminenomi.packets.client.ability.node;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUnlockAbilityNodePacket;

public class CUnlockAbilityNodePacket {
   private ResourceLocation coreKey;
   private int index;

   public CUnlockAbilityNodePacket() {
   }

   public CUnlockAbilityNodePacket(AbilityCore<? extends IAbility> core, int index) {
      this.coreKey = core.getRegistryKey();
      this.index = index;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.coreKey);
      buffer.writeInt(this.index);
   }

   public static CUnlockAbilityNodePacket decode(FriendlyByteBuf buffer) {
      CUnlockAbilityNodePacket msg = new CUnlockAbilityNodePacket();
      msg.coreKey = buffer.m_130281_();
      msg.index = buffer.readInt();
      return msg;
   }

   public static void handle(CUnlockAbilityNodePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            try {
               ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
               IAbilityData props = (IAbilityData)AbilityCapability.getLazy(player).orElse((Object)null);
               if (props == null) {
                  return;
               }

               AbilityCore<? extends IAbility> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(message.coreKey);
               if (core == null) {
                  return;
               }

               AbilityNode node = props.getNode(core, message.index);
               if (node == null) {
                  return;
               }

               if (node.canUnlock(player)) {
                  node.unlockNode(player);
                  ModNetwork.sendTo(new SUnlockAbilityNodePacket(core, message.index), player);
               }
            } catch (Exception e) {
               e.printStackTrace();
            }

         });
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }
}
