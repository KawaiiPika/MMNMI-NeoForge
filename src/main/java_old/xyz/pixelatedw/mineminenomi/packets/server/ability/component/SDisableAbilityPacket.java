package xyz.pixelatedw.mineminenomi.packets.server.ability.component;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DisableComponent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SDisableAbilityPacket {
   private int entityId;
   private Set<ResourceLocation> abilityIds = new HashSet();
   private int disableTicks;
   private boolean state;

   public SDisableAbilityPacket() {
   }

   public SDisableAbilityPacket(int entityId, Set<IAbility> abilities, int disableTicks, boolean state) {
      this.entityId = entityId;
      this.abilityIds = (Set)abilities.stream().map((a) -> a.getCore().getRegistryKey()).collect(Collectors.toSet());
      this.disableTicks = disableTicks;
      this.state = state;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.abilityIds.size());

      for(ResourceLocation rs : this.abilityIds) {
         buffer.m_130085_(rs);
      }

      buffer.writeInt(this.disableTicks);
      buffer.writeBoolean(this.state);
   }

   public static SDisableAbilityPacket decode(FriendlyByteBuf buffer) {
      SDisableAbilityPacket msg = new SDisableAbilityPacket();
      msg.entityId = buffer.readInt();
      int abilities = buffer.readInt();

      for(int i = 0; i < abilities; ++i) {
         msg.abilityIds.add(buffer.m_130281_());
      }

      msg.disableTicks = buffer.readInt();
      msg.state = buffer.readBoolean();
      return msg;
   }

   public static void handle(SDisableAbilityPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SDisableAbilityPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   private static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SDisableAbilityPacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof Player player) {
            IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
            if (props != null) {
               Set<IAbility> passiveAbilities = props.getPassiveAbilities((a) -> a.hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) && message.abilityIds.contains(a.getCore().getRegistryKey()));
               Set<IAbility> equippedAbilities = props.<IAbility>getEquippedAbilities((a) -> a.hasComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()) && message.abilityIds.contains(a.getCore().getRegistryKey()));
               if (message.state) {
                  passiveAbilities.stream().map((a) -> (DisableComponent)a.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).forEach((c) -> c.startDisable(player, (float)message.disableTicks));
                  equippedAbilities.stream().map((a) -> (DisableComponent)a.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).forEach((c) -> c.startDisable(player, (float)message.disableTicks));
               } else {
                  passiveAbilities.stream().map((a) -> (DisableComponent)a.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).forEach((c) -> c.stopDisable(player));
                  equippedAbilities.stream().map((a) -> (DisableComponent)a.getComponent((AbilityComponentKey)ModAbilityComponents.DISABLE.get()).get()).forEach((c) -> c.stopDisable(player));
               }

            }
         }
      }
   }
}
