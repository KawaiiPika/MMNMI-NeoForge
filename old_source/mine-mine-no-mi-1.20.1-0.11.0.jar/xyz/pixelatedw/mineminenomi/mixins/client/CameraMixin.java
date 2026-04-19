package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraOffsetEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraZoomEvent;

@Mixin({Camera.class})
public class CameraMixin {
   @Shadow
   public BlockGetter f_90550_;
   @Shadow
   public Entity f_90551_;
   @Shadow
   public Vec3 f_90552_;
   @Shadow
   @Final
   public final Vector3f f_90554_ = new Vector3f(0.0F, 0.0F, 1.0F);

   @Inject(
      method = {"getMaxZoom"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getMaxZoom(double startingDistance, CallbackInfoReturnable<Double> callback) {
      Minecraft mc = Minecraft.m_91087_();
      SetCameraZoomEvent event = new SetCameraZoomEvent(mc.f_91074_, startingDistance);
      MinecraftForge.EVENT_BUS.post(event);
      if (event.getResult() == Result.ALLOW) {
         startingDistance = event.getZoom();

         for(int i = 0; i < 8; ++i) {
            float f = (float)((i & 1) * 2 - 1);
            float f1 = (float)((i >> 1 & 1) * 2 - 1);
            float f2 = (float)((i >> 2 & 1) * 2 - 1);
            f *= 0.1F;
            f1 *= 0.1F;
            f2 *= 0.1F;
            Vec3 vector3d = this.f_90552_.m_82520_((double)f, (double)f1, (double)f2);
            Vec3 vector3d1 = new Vec3(this.f_90552_.f_82479_ - (double)this.f_90554_.x() * startingDistance + (double)f + (double)f2, this.f_90552_.f_82480_ - (double)this.f_90554_.y() * startingDistance + (double)f1, this.f_90552_.f_82481_ - (double)this.f_90554_.z() * startingDistance + (double)f2);
            HitResult raytraceresult = this.f_90550_.m_45547_(new ClipContext(vector3d, vector3d1, Block.VISUAL, Fluid.NONE, this.f_90551_));
            if (raytraceresult.m_6662_() != Type.MISS) {
               double distance = raytraceresult.m_82450_().m_82554_(this.f_90552_);
               if (distance < startingDistance) {
                  startingDistance = distance;
               }
            }
         }

         callback.setReturnValue(startingDistance);
      }

   }

   @Inject(
      method = {"setPosition"},
      at = {@At("TAIL")}
   )
   public void setPosition(double x, double y, double z, CallbackInfo callback) {
      Minecraft mc = Minecraft.m_91087_();
      SetCameraOffsetEvent event = new SetCameraOffsetEvent(mc.f_91074_, new Vec3(x, y, z));
      MinecraftForge.EVENT_BUS.post(event);
      if (event.getResult() == Result.ALLOW) {
         this.f_90552_ = event.getCameraPosition();
      }

   }
}
