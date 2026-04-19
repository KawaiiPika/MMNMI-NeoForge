package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SSetOverlayLayersPacket {
   private int entityId;
   private int abilityId;
   private Set<Integer> layers;

   public SSetOverlayLayersPacket() {
   }

   public SSetOverlayLayersPacket(LivingEntity entity, IAbility ability, Set<Integer> layers) {
      this.entityId = entity.m_19879_();
      this.abilityId = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
      this.layers = layers;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityId);
      buffer.writeInt(this.layers.size());

      for(int hash : this.layers) {
         buffer.writeInt(hash);
      }

   }

   public static SSetOverlayLayersPacket decode(FriendlyByteBuf buffer) {
      SSetOverlayLayersPacket msg = new SSetOverlayLayersPacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.readInt();
      int size = buffer.readInt();
      msg.layers = new HashSet();

      for(int i = 0; i < size; ++i) {
         msg.layers.add(buffer.readInt());
      }

      return msg;
   }

   public static void handle(SSetOverlayLayersPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetOverlayLayersPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   private static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetOverlayLayersPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity entity) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
            if (props != null) {
               IAbility abl = props.getEquippedAbility(message.abilityId);
               if (abl != null) {
                  abl.getComponent((AbilityComponentKey)ModAbilityComponents.SKIN_OVERLAY.get()).ifPresent((c) -> c.setShownOverlays(message.layers));
               }
            }
         }
      }
   }
}
