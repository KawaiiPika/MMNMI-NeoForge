package xyz.pixelatedw.mineminenomi.handlers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraOffsetEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraZoomEvent;
import xyz.pixelatedw.mineminenomi.handlers.ability.ComponentsHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.VehicleHandler;

@EventBusSubscriber(
   modid = "mineminenomi",
   value = {Dist.CLIENT}
)
public class InputEventHandler {
   @SubscribeEvent
   public static void onMouseInput(InputEvent.MouseButton.Post event) {
      AbstractClientPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null && event.getAction() != 0) {
         InputConstants.Key input = Type.MOUSE.m_84895_(event.getButton());
         InputHandler.checkKeybindings(player, input, event.getAction(), event.getButton(), 0);
      }
   }

   @SubscribeEvent
   public static void onKeyInput(InputEvent.Key event) {
      AbstractClientPlayer player = Minecraft.m_91087_().f_91074_;
      if (player != null && event.getAction() != 0) {
         InputConstants.Key input = InputConstants.m_84827_(event.getKey(), event.getScanCode());
         InputHandler.checkKeybindings(player, input, event.getAction(), event.getKey(), 1);
      }
   }

   @SubscribeEvent
   public static void onMouseClicked(InputEvent.InteractionKeyMappingTriggered event) {
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      boolean canBlockKey = InputHandler.canBlockHandSwingInput(player, event.getHand());
      if (canBlockKey) {
         event.setCanceled(true);
      } else {
         if (event.shouldSwingHand() && event.isAttack() && !player.m_108637_() && (double)player.m_36403_(0.0F) >= (double)1.0F) {
            ComponentsHandler.Client.triggerSwingComponents(player);
         }

      }
   }

   @SubscribeEvent
   public static void onCameraAnglesChanged(ViewportEvent.ComputeCameraAngles event) {
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      boolean isFirstPerson = mc.f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      float dizzyRoll = EffectsHandler.Client.getDizzyCameraRoll(player);
      if (isFirstPerson && (double)dizzyRoll != (double)0.0F) {
         event.setRoll((float)Math.sin((double)(player.f_19797_ % 100)));
      }

   }

   @SubscribeEvent
   public static void onCameraZoom(SetCameraZoomEvent event) {
      Player player = event.getEntity();
      if (player != null) {
         double zoom = (double)0.0F;
         zoom += MorphsHandler.getMorphCameraZoom(player);
         zoom += VehicleHandler.getStrikerCameraZoom(player);
         if (zoom != (double)0.0F) {
            event.setZoom(zoom);
            event.setResult(Result.ALLOW);
         }
      }
   }

   @SubscribeEvent
   public static void onCameraOffset(SetCameraOffsetEvent event) {
      Player player = event.getEntity();
      if (player != null) {
         Vec3 offset = Vec3.f_82478_;
         MorphsHandler.updateMorphCameraOffset(player, offset);
         if (offset.m_82553_() != (double)0.0F) {
            event.setCameraPosition(event.getCameraPosition().m_82549_(offset));
            event.setResult(Result.ALLOW);
         }
      }
   }
}
