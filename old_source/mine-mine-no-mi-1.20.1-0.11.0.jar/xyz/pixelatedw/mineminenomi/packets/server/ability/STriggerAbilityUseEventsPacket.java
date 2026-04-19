package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class STriggerAbilityUseEventsPacket {
   private AbilityCore<?> ability;

   public STriggerAbilityUseEventsPacket() {
   }

   public STriggerAbilityUseEventsPacket(AbilityCore<?> ability) {
      this.ability = ability;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.ability.getRegistryKey());
   }

   public static STriggerAbilityUseEventsPacket decode(FriendlyByteBuf buffer) {
      STriggerAbilityUseEventsPacket msg = new STriggerAbilityUseEventsPacket();
      msg.ability = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(buffer.m_130281_());
      return msg;
   }

   public static void handle(STriggerAbilityUseEventsPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> STriggerAbilityUseEventsPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(STriggerAbilityUseEventsPacket message) {
         AbstractClientPlayer player = Minecraft.m_91087_().f_91074_;
         if (player != null && message.ability != null) {
            AbilityCapability.get(player).map((props) -> props.getEquippedAbility(message.ability)).ifPresent((abl) -> {
               if (abl instanceof Ability ability) {
                  ability.dispatchUseEvents(player);
               }

            });
         }
      }
   }
}
