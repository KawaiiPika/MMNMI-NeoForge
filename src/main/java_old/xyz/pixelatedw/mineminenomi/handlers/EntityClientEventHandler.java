package xyz.pixelatedw.mineminenomi.handlers;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import xyz.pixelatedw.mineminenomi.abilities.doa.DoaHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.handlers.ability.KukuPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MaguPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.CameraHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.ItemsHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@EventBusSubscriber(
   modid = "mineminenomi",
   value = {Dist.CLIENT}
)
public class EntityClientEventHandler {
   @SubscribeEvent
   public static void onClientTick(TickEvent.ClientTickEvent event) {
      if (event.phase != Phase.START) {
         Minecraft mc = Minecraft.m_91087_();
         LocalPlayer player = mc.f_91074_;
         if (player != null && player.f_108618_ != null) {
            TPSDelta.INSTANCE.tick();
            InputHandler.handleNewPlayerInput(player);
            if (player.m_21023_((MobEffect)ModEffects.SILENT.get()) && player.f_19797_ % 20 == 0) {
               mc.m_91106_().m_120405_();
            }

         }
      }
   }

   @SubscribeEvent
   public static void onPlayerJoins(EntityJoinLevelEvent event) {
      InputHandler.resetCachedKeybinds();
   }

   @SubscribeEvent
   public static void onPlayerLeaves(PlayerEvent.PlayerLoggedOutEvent event) {
      InputHandler.resetVanillaKeybinds();
   }

   @SubscribeEvent
   public static void onPlayerCameraSetup(ViewportEvent.ComputeCameraAngles event) {
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      if (player.m_21023_((MobEffect)ModEffects.DOOR_HEAD.get())) {
         event.setYaw((float)(player.f_19797_ * 10) % 360.0F);
      }

      float[] cameraLock = CameraHandler.cameraLock(mc, player);
      if (cameraLock != null) {
         event.setPitch(cameraLock[0]);
         event.setYaw(cameraLock[1]);
      }

   }

   @SubscribeEvent
   public static void onEntityPreRendered(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
      LivingEntity entity = event.getEntity();
      boolean isInvisible = EffectsHandler.Client.isInvisible(entity);
      if (isInvisible) {
         event.setCanceled(true);
      } else {
         if (entity.m_21023_((MobEffect)ModEffects.DOOR_HEAD.get())) {
            entity.f_20883_ = 0.0F;
            entity.f_20884_ = 0.0F;
         }

      }
   }

   @SubscribeEvent
   public static void onEntityPostRendered(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
      LivingEntity entity = event.getEntity();
      if (entity.m_21023_((MobEffect)ModEffects.DOOR_HEAD.get())) {
         entity.f_20885_ += 10.0F;
         entity.f_20886_ += 10.0F;
         entity.f_20883_ = 0.0F;
         entity.f_20884_ = 0.0F;
      }

   }

   @SubscribeEvent
   public static void onFirstPersonViewRendered(TickEvent.RenderTickEvent event) {
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      if (player != null && player.m_6084_()) {
         if (DoaHelper.isInsideDoor(player)) {
            RendererHelper.drawColourOnScreen(2817966, 50, (double)0.0F, (double)0.0F, (double)mc.m_91268_().m_85445_(), (double)mc.m_91268_().m_85446_(), (double)500.0F);
         }

      }
   }

   @SubscribeEvent
   public static void onEntityHandRendered(RenderHandEvent event) {
      Minecraft mc = Minecraft.m_91087_();
      LocalPlayer player = mc.f_91074_;
      ItemStack heldStack = event.getItemStack();
      PoseStack matrixStack = event.getPoseStack();
      int combinedLight = event.getPackedLight();
      float swingProgress = event.getSwingProgress();
      float equipProgress = event.getEquipProgress();
      boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
      boolean isHandEmpty = heldStack.m_41619_();
      if (isMainHand) {
         if (player.m_21023_((MobEffect)ModEffects.NO_HANDS.get())) {
            event.setCanceled(true);
            return;
         }

         if (isHandEmpty) {
            Event.Result result = MorphsHandler.renderLimbWithOverlay(matrixStack, event.getMultiBufferSource(), heldStack, combinedLight, swingProgress, equipProgress, player);
            if (result == Result.DENY) {
               event.setCanceled(true);
            }
         }
      }

   }

   @SubscribeEvent
   public static void onTooltip(ItemTooltipEvent event) {
      Player player = event.getEntity();
      if (player != null) {
         if (!event.getItemStack().m_41619_()) {
            ItemStack itemStack = event.getItemStack();
            List<Component> tooltip = event.getToolTip();
            ItemsHandler.kairosekiTooltip(itemStack, tooltip);
            ItemsHandler.dyeableTooltip(itemStack, tooltip);
            ItemsHandler.weatherBallChargesTooltip(itemStack, tooltip);
            if (RandomizedFruitsHandler.Client.HAS_RANDOMIZED_FRUIT) {
               ItemsHandler.handleRandomizedFruitsTooltip(itemStack, tooltip);
            }

            KukuPassivesHandler.addKukuEdibleTooltips(player, itemStack, tooltip);
            RandomizedFruitsHandler.Client.handleHiddenFruitName(player, itemStack, tooltip);
         }
      }
   }

   @SubscribeEvent
   public static void onScreenEffectRender(RenderBlockScreenEffectEvent event) {
      Player player = event.getPlayer();
      RenderBlockScreenEffectEvent.OverlayType overlay = event.getOverlayType();
      if (MaguPassivesHandler.canSeeThroughFire(player, overlay)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onFogRender(ViewportEvent.RenderFog event) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (MaguPassivesHandler.canSeeInsideLava(player)) {
         event.setCanceled(true);
         event.setFarPlaneDistance(40.0F);
      }
   }

   @SubscribeEvent
   public static void onSoundPlayed(PlaySoundEvent event) {
      Minecraft mc = Minecraft.m_91087_();
      if (mc.f_91073_ != null) {
         BlockPos soundPos = BlockPos.m_274561_(event.getSound().m_7772_(), event.getSound().m_7780_(), event.getSound().m_7778_());
         if (mc.f_91074_.m_21023_((MobEffect)ModEffects.SILENT.get()) && soundPos.m_123314_(mc.f_91074_.m_20183_(), (double)30.0F)) {
            event.setSound((SoundInstance)null);
         }

      }
   }
}
