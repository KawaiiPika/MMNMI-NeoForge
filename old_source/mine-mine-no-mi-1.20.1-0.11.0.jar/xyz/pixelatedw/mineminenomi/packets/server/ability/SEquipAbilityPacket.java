package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;

public class SEquipAbilityPacket {
   private int entityId;
   private int slot;
   private ResourceLocation abilityId;

   public SEquipAbilityPacket() {
   }

   public SEquipAbilityPacket(int entityId, int slot, AbilityCore<?> core) {
      this.entityId = entityId;
      this.slot = slot;
      this.abilityId = core.getRegistryKey();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.slot);
      buffer.writeBoolean(this.abilityId != null);
      if (this.abilityId != null) {
         buffer.m_130085_(this.abilityId);
      }

   }

   public static SEquipAbilityPacket decode(FriendlyByteBuf buffer) {
      SEquipAbilityPacket msg = new SEquipAbilityPacket();
      msg.entityId = buffer.readInt();
      msg.slot = buffer.readInt();
      boolean hasValidId = buffer.readBoolean();
      if (hasValidId) {
         msg.abilityId = buffer.m_130281_();
      }

      return msg;
   }

   public static void handle(SEquipAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SEquipAbilityPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SEquipAbilityPacket message) {
         try {
            ClientLevel world = Minecraft.m_91087_().f_91073_;
            Entity target = world.m_6815_(message.entityId);
            if (target != null && target instanceof LivingEntity living) {
               if (message.abilityId == null) {
                  LogManager.getLogger(SEquipAbilityPacket.class).warn(target.m_5446_().getString() + " tried equipping an invalid ability!");
               } else {
                  AbilityCore<?> core = AbilityCore.get(message.abilityId);
                  IAbility ability = core.createAbility();
                  AbilityCapability.get(living).ifPresent((props) -> props.setEquippedAbility(message.slot, ability));
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}
