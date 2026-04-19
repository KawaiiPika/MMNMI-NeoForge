package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SSetPauseTickPacket {
   private int entityId;
   private ResourceLocation abilityKey;
   private boolean pauseFlag;

   public SSetPauseTickPacket() {
   }

   public SSetPauseTickPacket(LivingEntity entity, AbilityCore<?> core, boolean pauseFlag) {
      this.entityId = entity.m_19879_();
      this.abilityKey = core.getRegistryKey();
      this.pauseFlag = pauseFlag;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130085_(this.abilityKey);
      buffer.writeBoolean(this.pauseFlag);
   }

   public static SSetPauseTickPacket decode(FriendlyByteBuf buffer) {
      SSetPauseTickPacket msg = new SSetPauseTickPacket();
      msg.entityId = buffer.readInt();
      msg.abilityKey = buffer.m_130281_();
      msg.pauseFlag = buffer.readBoolean();
      return msg;
   }

   public static void handle(SSetPauseTickPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSetPauseTickPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSetPauseTickPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity) {
            AbilityCore<?> core = (AbilityCore)((IForgeRegistry)WyRegistry.ABILITIES.get()).getValue(message.abilityKey);
            if (core != null && core.getType() == AbilityType.PASSIVE) {
               LivingEntity entity = (LivingEntity)target;
               IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
               if (props != null) {
                  IAbility ability = props.getPassiveAbility(core);
                  if (ability != null) {
                     ability.getComponent((AbilityComponentKey)ModAbilityComponents.PAUSE_TICK.get()).ifPresent((c) -> c.setPause(entity, message.pauseFlag));
                  }
               }
            }
         }
      }
   }
}
