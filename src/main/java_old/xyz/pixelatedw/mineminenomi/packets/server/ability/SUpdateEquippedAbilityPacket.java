package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SUpdateEquippedAbilityPacket {
   private int entityId;
   private int abilityId;
   private CompoundTag nbtData;

   public SUpdateEquippedAbilityPacket() {
   }

   public SUpdateEquippedAbilityPacket(LivingEntity entity, IAbility ability) {
      this.entityId = entity.m_19879_();
      this.abilityId = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
      this.nbtData = new CompoundTag();
      ability.save(this.nbtData);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityId);
      buffer.m_130079_(this.nbtData);
   }

   public static SUpdateEquippedAbilityPacket decode(FriendlyByteBuf buffer) {
      SUpdateEquippedAbilityPacket msg = new SUpdateEquippedAbilityPacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.readInt();
      msg.nbtData = buffer.m_130260_();
      return msg;
   }

   public static void handle(SUpdateEquippedAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateEquippedAbilityPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateEquippedAbilityPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity living) {
            if (message.abilityId >= 0) {
               IAbilityData props = (IAbilityData)AbilityCapability.get(living).orElse((Object)null);
               if (props == null) {
                  return;
               }

               IAbility ability = props.getEquippedAbility(message.abilityId);
               if (ability == null) {
                  return;
               }

               ability.load(message.nbtData);
               return;
            }
         }

      }
   }
}
