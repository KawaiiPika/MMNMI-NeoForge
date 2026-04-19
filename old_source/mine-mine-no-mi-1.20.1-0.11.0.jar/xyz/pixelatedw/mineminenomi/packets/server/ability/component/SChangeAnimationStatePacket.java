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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.animation.AnimationCapability;
import xyz.pixelatedw.mineminenomi.data.entity.animation.IAnimationData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class SChangeAnimationStatePacket {
   private static final ResourceLocation EMPTY = ResourceLocation.fromNamespaceAndPath("mineminenomi", "");
   private int entityId;
   private ResourceLocation abilityId;
   private int stateId;
   private int animDuration;
   private AnimationId<?> animId;

   public SChangeAnimationStatePacket() {
   }

   public SChangeAnimationStatePacket(LivingEntity entity, IAbility ability, AnimationId<?> animId, Animation.State state, int animDuration) {
      this.entityId = entity.m_19879_();
      this.abilityId = ability.getCore().getRegistryKey();
      this.animId = animId;
      this.animDuration = animDuration;
      this.stateId = state.ordinal();
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130085_(this.abilityId);
      buffer.writeInt(this.stateId);
      buffer.writeInt(this.animDuration);
      buffer.m_130085_(this.animId != null ? this.animId.getId() : EMPTY);
   }

   public static SChangeAnimationStatePacket decode(FriendlyByteBuf buffer) {
      SChangeAnimationStatePacket msg = new SChangeAnimationStatePacket();
      msg.entityId = buffer.readInt();
      msg.abilityId = buffer.m_130281_();
      msg.stateId = buffer.readInt();
      msg.animDuration = buffer.readInt();
      ResourceLocation animRes = buffer.m_130281_();
      msg.animId = animRes.equals(EMPTY) ? null : AnimationId.getRegisteredId(animRes);
      return msg;
   }

   public static void handle(SChangeAnimationStatePacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SChangeAnimationStatePacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SChangeAnimationStatePacket message) {
         Entity target = Minecraft.m_91087_().f_91073_.m_6815_(message.entityId);
         if (target != null && target instanceof LivingEntity) {
            AbilityCore<?> core = AbilityCore.get(message.abilityId);
            if (core != null) {
               LivingEntity entity = (LivingEntity)target;
               IAnimationData animProps = (IAnimationData)AnimationCapability.get(entity).orElse((Object)null);
               IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
               if (animProps != null && abilityProps != null) {
                  IAbility abl = abilityProps.getEquippedAbility(core);
                  if (abl == null) {
                     abl = abilityProps.getPassiveAbility(core);
                     if (abl == null) {
                        return;
                     }
                  }

                  Animation.State state = Animation.State.values()[message.stateId];
                  abl.getComponent((AbilityComponentKey)ModAbilityComponents.ANIMATION.get()).ifPresent((comp) -> {
                     if (state.equals(Animation.State.PLAY) && message.animId != null && !message.animId.getId().equals(SChangeAnimationStatePacket.EMPTY)) {
                        comp.start(entity, message.animId, message.animDuration);
                        animProps.startAnimation(message.animId, message.animDuration, false);
                     } else if (state.equals(Animation.State.STOP)) {
                        comp.stop(entity);
                        animProps.stopAnimation(message.animId);
                     } else if (state.equals(Animation.State.PAUSE)) {
                        if (comp.isPaused()) {
                           comp.resume(entity);
                           animProps.startAnimation(message.animId, message.animDuration, false);
                        } else {
                           comp.pause(entity);
                           animProps.stopAnimation(message.animId);
                        }
                     }

                  });
               }
            }
         }
      }
   }
}
