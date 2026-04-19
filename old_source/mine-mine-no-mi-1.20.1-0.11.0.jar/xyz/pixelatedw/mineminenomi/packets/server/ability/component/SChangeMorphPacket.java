package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SChangeMorphPacket {
   private int entityId;
   private int abilityId;
   private MorphInfo morphInfo;
   private boolean isEnabled;

   public SChangeMorphPacket() {
   }

   public SChangeMorphPacket(LivingEntity entity, IAbility ability, MorphInfo morphInfo, boolean enabled) {
      this.entityId = entity.m_19879_();
      this.abilityId = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
      this.morphInfo = morphInfo;
      this.isEnabled = enabled;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityId);
      boolean hasMorph = this.morphInfo != null;
      buffer.writeBoolean(hasMorph);
      if (hasMorph) {
         buffer.m_130085_(this.morphInfo.getKey());
      }

      buffer.writeBoolean(this.isEnabled);
   }

   public static SChangeMorphPacket decode(FriendlyByteBuf buffer) {
      SChangeMorphPacket msg = new SChangeMorphPacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.readInt();
      boolean hasMorph = buffer.readBoolean();
      if (hasMorph) {
         msg.morphInfo = (MorphInfo)((IForgeRegistry)WyRegistry.MORPHS.get()).getValue(buffer.m_130281_());
      }

      msg.isEnabled = buffer.readBoolean();
      return msg;
   }

   public static void handle(SChangeMorphPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SChangeMorphPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SChangeMorphPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity entity) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
            if (props != null) {
               IAbility abl = props.getEquippedAbility(message.abilityId);
               if (abl != null) {
                  abl.getComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get()).ifPresent((comp) -> {
                     if (message.isEnabled) {
                        comp.startMorph(entity, message.morphInfo);
                     } else {
                        comp.stopMorph(entity);
                     }

                     message.morphInfo.updateMorphSize(entity);
                  });
               }
            }
         }
      }
   }
}
