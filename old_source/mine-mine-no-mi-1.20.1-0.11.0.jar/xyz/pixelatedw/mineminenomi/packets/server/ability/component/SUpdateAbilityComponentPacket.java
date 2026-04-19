package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SUpdateAbilityComponentPacket {
   private int entityId;
   private int abilityId;
   private ResourceLocation componentKey;
   private CompoundTag data;

   public SUpdateAbilityComponentPacket() {
   }

   public SUpdateAbilityComponentPacket(LivingEntity entity, IAbility ability, AbilityComponent<? extends IAbility> component) {
      this.entityId = entity.m_19879_();
      this.abilityId = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
      this.componentKey = component.getKey().getId();
      this.data = component.save();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityId);
      buffer.m_130085_(this.componentKey);
      buffer.m_130079_(this.data);
   }

   public static SUpdateAbilityComponentPacket decode(FriendlyByteBuf buffer) {
      SUpdateAbilityComponentPacket msg = new SUpdateAbilityComponentPacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.readInt();
      msg.componentKey = buffer.m_130281_();
      msg.data = buffer.m_130260_();
      return msg;
   }

   public static void handle(SUpdateAbilityComponentPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SUpdateAbilityComponentPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SUpdateAbilityComponentPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         Entity target = mc.f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity living) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(living).orElse((Object)null);
            if (props != null) {
               IAbility ability = props.getEquippedAbility(message.abilityId);
               if (ability != null) {
                  ability.getComponent((AbilityComponentKey)((IForgeRegistry)WyRegistry.ABILITY_COMPONENTS.get()).getValue(message.componentKey)).ifPresent((comp) -> comp.load(message.data));
               }
            }
         }
      }
   }
}
