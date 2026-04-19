package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;

public class SBonusManagerUpdatePacket {
   private int entityId;
   private int abilityId;
   private ResourceLocation componentKey;
   private Map<UUID, BonusManager.BonusValue> bonuses;

   public SBonusManagerUpdatePacket() {
   }

   public SBonusManagerUpdatePacket(LivingEntity entity, IAbility ability, AbilityComponent<?> comp, Map<UUID, BonusManager.BonusValue> bonuses) {
      this.entityId = entity.m_19879_();
      this.abilityId = (Integer)AbilityCapability.get(entity).map((props) -> props.getEquippedAbilitySlot(ability)).orElse(-1);
      this.componentKey = comp.getKey().getId();
      this.bonuses = bonuses;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityId);
      buffer.m_130085_(this.componentKey);
      int size = this.bonuses.size();
      buffer.writeInt(size);
      this.bonuses.forEach((id, val) -> {
         buffer.m_130077_(id);
         int nameLen = val.getName().length();
         buffer.writeInt(nameLen);
         buffer.m_130072_((String)null, nameLen);
         buffer.writeInt(val.getType().ordinal());
         buffer.writeFloat(val.getValue());
      });
   }

   public static SBonusManagerUpdatePacket decode(FriendlyByteBuf buffer) {
      SBonusManagerUpdatePacket msg = new SBonusManagerUpdatePacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.readInt();
      msg.componentKey = buffer.m_130281_();
      int size = buffer.readInt();
      msg.bonuses = new HashMap();

      for(int i = 0; i < size; ++i) {
         UUID id = buffer.m_130259_();
         int nameLen = buffer.readInt();
         String name = buffer.m_130136_(nameLen);
         BonusOperation op = BonusOperation.values()[buffer.readInt()];
         float value = buffer.readFloat();
         msg.bonuses.put(id, new BonusManager.BonusValue(id, name, op, value));
      }

      return msg;
   }

   public static void handle(SBonusManagerUpdatePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SBonusManagerUpdatePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   private static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SBonusManagerUpdatePacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity entity) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
            if (props != null) {
               IAbility abl = props.getEquippedAbility(message.abilityId);
               if (abl != null) {
                  abl.getComponent((AbilityComponentKey)((IForgeRegistry)WyRegistry.ABILITY_COMPONENTS.get()).getValue(message.componentKey)).ifPresent((comp) -> comp.getBonusManagers().forEachRemaining((manager) -> manager.setBonusMap(message.bonuses)));
               }
            }
         }
      }
   }
}
