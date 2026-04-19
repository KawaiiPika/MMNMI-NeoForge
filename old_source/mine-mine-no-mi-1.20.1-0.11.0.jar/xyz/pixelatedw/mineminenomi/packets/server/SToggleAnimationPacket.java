package xyz.pixelatedw.mineminenomi.packets.server;

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
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.data.entity.animation.AnimationCapability;
import xyz.pixelatedw.mineminenomi.data.entity.animation.IAnimationData;

public class SToggleAnimationPacket {
   private static final ResourceLocation EMPTY = ResourceLocation.fromNamespaceAndPath("mineminenomi", "");
   private int entityId;
   private AnimationId<?> animId;
   private int duration;
   private int stateId;
   private boolean force;

   public SToggleAnimationPacket() {
   }

   public static SToggleAnimationPacket playAnimation(LivingEntity entity, AnimationId<?> animId, int duration, boolean force) {
      SToggleAnimationPacket packet = new SToggleAnimationPacket(entity, animId, Animation.State.PLAY, duration, force);
      return packet;
   }

   public static SToggleAnimationPacket stopAnimation(LivingEntity entity, AnimationId<?> animId) {
      SToggleAnimationPacket packet = new SToggleAnimationPacket(entity, animId, Animation.State.STOP, 0, false);
      return packet;
   }

   public SToggleAnimationPacket(LivingEntity entity, @Nullable AnimationId<?> animId, Animation.State state, int duration, boolean force) {
      this.entityId = entity.m_19879_();
      this.animId = animId;
      this.duration = duration;
      this.stateId = state.ordinal();
      this.force = force;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeInt(this.duration);
      buffer.m_130085_(this.animId != null ? this.animId.getId() : EMPTY);
      buffer.writeInt(this.stateId);
      buffer.writeBoolean(this.force);
   }

   public static SToggleAnimationPacket decode(FriendlyByteBuf buffer) {
      SToggleAnimationPacket msg = new SToggleAnimationPacket();
      msg.entityId = buffer.readInt();
      msg.duration = buffer.readInt();
      ResourceLocation animRes = buffer.m_130281_();
      msg.animId = animRes.equals(EMPTY) ? null : AnimationId.getRegisteredId(animRes);
      msg.stateId = buffer.readInt();
      msg.force = buffer.readBoolean();
      return msg;
   }

   public static void handle(SToggleAnimationPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SToggleAnimationPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SToggleAnimationPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         ClientLevel world = mc.f_91073_;
         Entity entity = world.m_6815_(message.entityId);
         if (entity != null && entity instanceof LivingEntity living) {
            IAnimationData animProps = (IAnimationData)AnimationCapability.get(living).orElse((Object)null);
            if (animProps != null) {
               Animation.State state = Animation.State.values()[message.stateId];
               if (state == Animation.State.PLAY) {
                  animProps.startAnimation(message.animId, message.duration, message.force);
               } else if (state == Animation.State.STOP) {
                  animProps.stopAnimation(message.animId);
               }

            }
         }
      }
   }
}
