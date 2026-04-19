package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SUnlockAbilityNodePacket {
   private ResourceLocation coreKey;
   private int index;

   public SUnlockAbilityNodePacket() {
   }

   public SUnlockAbilityNodePacket(AbilityCore<? extends IAbility> core, int index) {
      this.coreKey = core.getRegistryKey();
      this.index = index;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.coreKey);
      buffer.writeInt(this.index);
   }

   public static SUnlockAbilityNodePacket decode(FriendlyByteBuf buffer) {
      SUnlockAbilityNodePacket msg = new SUnlockAbilityNodePacket();
      msg.coreKey = buffer.m_130281_();
      msg.index = buffer.readInt();
      return msg;
   }

   public static void handle(SUnlockAbilityNodePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUnlockAbilityNodePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   @OnlyIn(Dist.CLIENT)
   public static class ClientHandler {
      public static void handle(SUnlockAbilityNodePacket message) {
         try {
            Minecraft mc = Minecraft.m_91087_();
            LocalPlayer player = mc.f_91074_;
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
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

      }
   }
}
