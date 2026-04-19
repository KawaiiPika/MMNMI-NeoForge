package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SUpdateAbilityNBTPacket {
   private int entityId;
   private ResourceLocation abilityId;
   private CompoundTag nbtData;

   public SUpdateAbilityNBTPacket() {
   }

   public SUpdateAbilityNBTPacket(LivingEntity entity, IAbility ability) {
      this.entityId = entity.m_19879_();
      this.abilityId = ability.getCore().getRegistryKey();
      this.nbtData = new CompoundTag();
      ability.save(this.nbtData);
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130085_(this.abilityId);
      buffer.m_130079_(this.nbtData);
   }

   public static SUpdateAbilityNBTPacket decode(FriendlyByteBuf buffer) {
      SUpdateAbilityNBTPacket msg = new SUpdateAbilityNBTPacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.m_130281_();
      msg.nbtData = buffer.m_130260_();
      return msg;
   }

   public static void handle(SUpdateAbilityNBTPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateAbilityNBTPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateAbilityNBTPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity living) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(living).orElse((Object)null);
            if (props != null) {
               AbilityCore<?> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(message.abilityId);
               if (core != null) {
                  IAbility ability = props.getEquippedOrPassiveAbility(core);
                  if (ability != null) {
                     ability.load(message.nbtData);
                  }
               }
            }
         }
      }
   }
}
