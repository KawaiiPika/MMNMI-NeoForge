package xyz.pixelatedw.mineminenomi.packets.server;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SSpawnParticleEffectPacket {
   private int spawnerId;
   private ResourceLocation id;
   private double posX;
   private double posY;
   private double posZ;
   private CompoundTag nbt = new CompoundTag();

   public SSpawnParticleEffectPacket() {
   }

   public SSpawnParticleEffectPacket(ParticleEffect<?> effect, Entity entity, double posX, double posY, double posZ, @Nullable ParticleEffect.Details details) {
      this.id = ((IForgeRegistry)WyRegistry.PARTICLE_EFFECTS.get()).getKey(effect);
      this.spawnerId = entity.m_19879_();
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      if (details != null) {
         details.save(this.nbt);
      }

   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.m_130085_(this.id);
      buffer.writeInt(this.spawnerId);
      buffer.writeDouble(this.posX);
      buffer.writeDouble(this.posY);
      buffer.writeDouble(this.posZ);
      buffer.m_130079_(this.nbt);
   }

   public static SSpawnParticleEffectPacket decode(FriendlyByteBuf buffer) {
      SSpawnParticleEffectPacket msg = new SSpawnParticleEffectPacket();
      msg.id = buffer.m_130281_();
      msg.spawnerId = buffer.readInt();
      msg.posX = buffer.readDouble();
      msg.posY = buffer.readDouble();
      msg.posZ = buffer.readDouble();
      msg.nbt = buffer.m_130260_();
      return msg;
   }

   public static void handle(SSpawnParticleEffectPacket message, Supplier<NetworkEvent.Context> ctx) {
      if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> SSpawnParticleEffectPacket.ClientHandler.handle(message));
      }

      ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
   }

   public static class ClientHandler {
      @OnlyIn(Dist.CLIENT)
      public static void handle(SSpawnParticleEffectPacket message) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null) {
            Entity spawner = player.m_9236_().m_6815_(message.spawnerId);
            ParticleEffect<ParticleEffect.Details> effect = (ParticleEffect)((IForgeRegistry)WyRegistry.PARTICLE_EFFECTS.get()).getValue(message.id);
            if (effect != null && spawner != null) {
               ParticleEffect.Details details = effect.createDetails();
               if (message.nbt != null) {
                  details.load(message.nbt);
               }

               if (effect.canParticlesSpawn(player.m_9236_())) {
                  effect.spawn(spawner, spawner.m_9236_(), message.posX, message.posY, message.posZ, details);
               }

            }
         }
      }
   }
}
