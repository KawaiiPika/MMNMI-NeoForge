package xyz.pixelatedw.mineminenomi.packets.server.ability;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import xyz.pixelatedw.mineminenomi.audio.DrumsOfLiberationTickableSound;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SToggleDrumsOfLiberationSoundPacket {
   private int entityId;
   private boolean shouldPlay;

   public SToggleDrumsOfLiberationSoundPacket() {
   }

   public SToggleDrumsOfLiberationSoundPacket(LivingEntity entity, boolean shouldPlay) {
      this.entityId = entity.m_19879_();
      this.shouldPlay = shouldPlay;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.writeBoolean(this.shouldPlay);
   }

   public static SToggleDrumsOfLiberationSoundPacket decode(FriendlyByteBuf buffer) {
      SToggleDrumsOfLiberationSoundPacket msg = new SToggleDrumsOfLiberationSoundPacket();
      msg.entityId = buffer.readInt();
      msg.shouldPlay = buffer.readBoolean();
      return msg;
   }

   public static void handle(SToggleDrumsOfLiberationSoundPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SToggleDrumsOfLiberationSoundPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SToggleDrumsOfLiberationSoundPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         Entity soundSource = mc.f_91073_.m_6815_(message.entityId);
         if (soundSource != null) {
            if (message.shouldPlay) {
               mc.m_91106_().m_120367_(new DrumsOfLiberationTickableSound((SoundEvent)ModSounds.DRUMS_OF_LIBERATION_1.get(), 0, soundSource));
               mc.m_91106_().m_120367_(new DrumsOfLiberationTickableSound((SoundEvent)ModSounds.DRUMS_OF_LIBERATION_2.get(), 1, soundSource));
            } else {
               mc.m_91106_().m_120386_(((SoundEvent)ModSounds.DRUMS_OF_LIBERATION_1.get()).m_11660_(), SoundSource.PLAYERS);
               mc.m_91106_().m_120386_(((SoundEvent)ModSounds.DRUMS_OF_LIBERATION_2.get()).m_11660_(), SoundSource.PLAYERS);
            }

         }
      }
   }
}
