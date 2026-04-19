package xyz.pixelatedw.mineminenomi.handlers.ability;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.awt.Color;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.Event.Result;
import xyz.pixelatedw.mineminenomi.abilities.RideableAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.IFirstPersonHandReplacer;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class MorphsHandler {
   public static <T extends LivingEntity, M extends EntityModel<T>> void preRenderHook(T entity, M model, LivingEntityRenderer<T, M> renderer, PoseStack matrixStack, float ageInTicks, float yaw, float partialTicks) {
      IDevilFruit fruitProps = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (fruitProps != null) {
         Optional<MorphInfo> morph = fruitProps.getCurrentMorph();
         if (morph.isPresent()) {
            ((MorphInfo)morph.get()).preRenderCallback(entity, renderer, matrixStack, partialTicks);
         }

         for(MorphInfo morphInfo : fruitProps.getActiveMorphs()) {
            if (morphInfo.isPartial()) {
               morphInfo.preRenderCallback(entity, renderer, matrixStack, partialTicks);
            }
         }

      }
   }

   public static <T extends LivingEntity, M extends EntityModel<T>> void postRenderHook(T entity, M model, LivingEntityRenderer<T, M> renderer, PoseStack matrixStack, float ageInTicks, float yaw, float partialTicks) {
      IDevilFruit fruitProps = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (fruitProps != null) {
         Optional<MorphInfo> morph = fruitProps.getCurrentMorph();
         if (morph.isPresent()) {
            ((MorphInfo)morph.get()).postRenderCallback(entity, renderer, matrixStack, partialTicks);
         }

         for(MorphInfo morphInfo : fruitProps.getActiveMorphs()) {
            if (morphInfo.isPartial()) {
               morphInfo.postRenderCallback(entity, renderer, matrixStack, partialTicks);
            }
         }

      }
   }

   public static Event.Result renderLimbWithOverlay(PoseStack matrixStack, MultiBufferSource buffer, ItemStack stack, int combinedLight, float swingProgress, float equipProgress, LocalPlayer player) {
      EntityRenderDispatcher renderDispatcher = Minecraft.m_91087_().m_91290_();
      EntityRenderer<? super LocalPlayer> renderer = renderDispatcher.m_114382_(player);
      Optional<AbilityOverlay> overlay = Optional.empty();
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (abilityData != null) {
         for(IAbility ability : abilityData.getEquippedAbilities()) {
            Optional<SkinOverlayComponent> comp = ability.<SkinOverlayComponent>getComponent((AbilityComponentKey)ModAbilityComponents.SKIN_OVERLAY.get());
            if (comp.isPresent()) {
               Optional<AbilityOverlay> opt = ((SkinOverlayComponent)comp.get()).getShownOverlay(AbilityOverlay.OverlayPart.values());
               if (opt.isPresent()) {
                  overlay = opt;
               }
            }
         }
      }

      ResourceLocation texture = null;
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      float alpha = 1.0F;
      if (overlay.isPresent()) {
         texture = ((AbilityOverlay)overlay.get()).getTexture();
         Color color = ((AbilityOverlay)overlay.get()).getColor();
         red = (float)color.getRed() / 255.0F;
         green = (float)color.getGreen() / 255.0F;
         blue = (float)color.getBlue() / 255.0F;
         alpha = (float)color.getAlpha() / 255.0F;
      }

      boolean isRightHanded = player.m_5737_() != HumanoidArm.LEFT;
      boolean isLeg = (Boolean)EntityStatsCapability.get(player).map(IEntityStats::isBlackLeg).orElse(false);
      matrixStack.m_85836_();
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (props != null && props.getActiveMorphs().size() > 0) {
         MorphInfo[] infos = (MorphInfo[])props.getActiveMorphs().toArray(new MorphInfo[0]);

         for(int i = props.getActiveMorphs().size() - 1; i >= 0; --i) {
            MorphInfo morph = infos[i];
            EntityModel<? extends LivingEntity> partialMorph = (EntityModel)ModMorphs.Client.PARTIAL_MORPHS_RENDERERS.get(morph);
            if (partialMorph != null && partialMorph instanceof IFirstPersonHandReplacer) {
               IFirstPersonHandReplacer replacer = (IFirstPersonHandReplacer)partialMorph;
               VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(morph.getTexture(player)));
               translateLimb(matrixStack, swingProgress, equipProgress, isRightHanded, isLeg);
               replacer.renderFirstPersonArm(matrixStack, vertex, combinedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F, player.m_5737_(), isLeg);
               matrixStack.m_85849_();
               return Result.DENY;
            }

            if (renderer instanceof MorphRenderer) {
               MorphRenderer morphRenderer = (MorphRenderer)renderer;
               translateLimb(matrixStack, swingProgress, equipProgress, isRightHanded, isLeg);
               morphRenderer.renderFirstPersonLimb(player, stack, matrixStack, buffer, combinedLight, player.m_5737_(), overlay, swingProgress, equipProgress, isLeg);
               matrixStack.m_85849_();
               return Result.DENY;
            }
         }
      }

      if (renderer instanceof PlayerRenderer playerRenderer) {
         PlayerModel<AbstractClientPlayer> model = (PlayerModel)playerRenderer.m_7200_();
         RendererHelper.resetModelToDefaultPivots(model);
         translateLimb(matrixStack, swingProgress, equipProgress, isRightHanded, isLeg);
         ModelPart part = model.f_102811_;
         ModelPart part2 = model.f_103375_;
         VertexConsumer playerVertex = buffer.m_6299_(RenderType.m_110452_(renderer.m_5478_(player)));
         if (isLeg) {
            part = isRightHanded ? model.f_102813_ : model.f_102814_;
            part2 = isRightHanded ? model.f_103377_ : model.f_103376_;
         } else {
            part = isRightHanded ? model.f_102811_ : model.f_102812_;
            part2 = isRightHanded ? model.f_103375_ : model.f_103374_;
         }

         part.f_104205_ = 0.1F;
         part2.f_104205_ = 0.1F;
         part.m_104306_(matrixStack, playerVertex, combinedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
         part2.m_104306_(matrixStack, playerVertex, combinedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
         if (overlay.isPresent()) {
            renderLimbOverlay(matrixStack, buffer, part, (AbilityOverlay)overlay.get(), texture, combinedLight, red, green, blue, alpha, isLeg);
         }

         matrixStack.m_85849_();
         return Result.DENY;
      } else {
         return Result.DEFAULT;
      }
   }

   private static void renderLimbOverlay(PoseStack matrixStack, MultiBufferSource buffer, @Nonnull ModelPart model, @Nonnull AbilityOverlay overlay, @Nullable ResourceLocation texture, int combinedLight, float red, float green, float blue, float alpha, boolean isLeg) {
      RenderType var10000;
      switch (overlay.getRenderType()) {
         case NORMAL -> var10000 = texture != null ? RenderType.m_110473_(texture) : ModRenderTypes.TRANSPARENT_COLOR;
         case ENERGY -> var10000 = ModRenderTypes.ENERGY;
         default -> throw new IncompatibleClassChangeError();
      }

      RenderType type = var10000;
      VertexConsumer vertex = buffer.m_6299_(type);
      if (isLeg) {
         matrixStack.m_252880_(0.0F, -0.01F, 0.0F);
      }

      model.f_233553_ = 1.15F;
      model.f_233554_ = 1.05F;
      model.f_233555_ = 1.15F;
      model.m_104306_(matrixStack, vertex, combinedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
   }

   private static void translateLimb(PoseStack matrixStack, float swingProgress, float equipProgress, boolean isRightHanded, boolean isLeg) {
      float f = isRightHanded ? 1.0F : -1.0F;
      if (isLeg) {
         float f1 = Mth.m_14116_(swingProgress) / 1.55F;
         float f2 = -0.3F * Mth.m_14031_(f1 * (float)Math.PI);
         float f3 = 0.4F * Mth.m_14031_(f1 * ((float)Math.PI * 2F));
         float f4 = -0.4F * Mth.m_14031_(swingProgress * (float)Math.PI);
         matrixStack.m_252880_(f * (f2 + 0.65F), f3 + -0.05F + equipProgress * -0.6F, f4 + -0.4F);
         matrixStack.m_252781_(Axis.f_252393_.m_252977_(f * 85.0F));
         float f6 = Mth.m_14031_(f1 * (float)Math.PI);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(f * f6 * 70.0F));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(f * f6 * -100.0F * f));
         matrixStack.m_252880_(f * -1.0F, 3.6F, 3.5F);
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(f * 120.0F));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(200.0F));
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(f * -135.0F));
         matrixStack.m_252880_(f * 5.6F, 0.0F, 0.0F);
      } else {
         float f1 = Mth.m_14116_(swingProgress);
         float f2 = -0.3F * Mth.m_14031_(f1 * (float)Math.PI);
         float f3 = 0.4F * Mth.m_14031_(f1 * ((float)Math.PI * 2F));
         float f4 = -0.4F * Mth.m_14031_(swingProgress * (float)Math.PI);
         matrixStack.m_252880_(f * (f2 + 0.64F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.72F);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(f * 45.0F));
         float f5 = Mth.m_14031_(swingProgress * swingProgress * (float)Math.PI);
         float f6 = Mth.m_14031_(f1 * (float)Math.PI);
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(f * f6 * 70.0F));
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(f * f5 * -20.0F));
         matrixStack.m_252880_(f * -1.0F, 3.6F, 3.5F);
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(f * 120.0F));
         matrixStack.m_252781_(Axis.f_252529_.m_252977_(200.0F));
         matrixStack.m_252781_(Axis.f_252436_.m_252977_(f * -135.0F));
         matrixStack.m_252880_(f * 5.6F, 0.0F, 0.0F);
      }

   }

   public static double getMorphCameraZoom(Player player) {
      IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (fruitData == null) {
         return (double)0.0F;
      } else {
         Optional<MorphInfo> morph = fruitData.getCurrentMorph();
         if (morph.isEmpty()) {
            for(MorphInfo info : fruitData.getActiveMorphs()) {
               if (info.isPartial()) {
                  return info.getCameraZoom(player);
               }
            }

            return (double)0.0F;
         } else {
            return !fruitData.hasMorphInQueue((MorphInfo)morph.get()) ? (double)0.0F : ((MorphInfo)morph.get()).getCameraZoom(player);
         }
      }
   }

   public static void updateMorphCameraOffset(Player player, Vec3 offset) {
      IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
      if (fruitData != null) {
         Optional<MorphInfo> morph = fruitData.getCurrentMorph();
         double height = (double)0.0F;
         if (morph.isEmpty()) {
            boolean hasMorph = false;

            for(MorphInfo info : fruitData.getActiveMorphs()) {
               if (info.isPartial()) {
                  hasMorph = true;
                  height = info.getCameraHeight(player);
                  break;
               }
            }

            if (!hasMorph) {
               return;
            }
         } else {
            if (!fruitData.hasMorphInQueue((MorphInfo)morph.get())) {
               return;
            }

            height = ((MorphInfo)morph.get()).getCameraHeight(player);
         }

         offset.m_82520_((double)0.0F, height, (double)0.0F);
      }
   }

   public static class Common {
      public static void startRidingZoan(Player player, Player mount) {
         IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(mount).orElse((Object)null);
         if (abilityProps != null) {
            Objects.requireNonNull(RideableAbility.class);
            boolean hasRideable = abilityProps.getPassiveAbilities(RideableAbility.class::isInstance).stream().filter((abl) -> abl.canUse(mount).isSuccess()).count() > 0L;
            if (hasRideable) {
               player.m_20329_(mount);
               ((ServerLevel)player.m_9236_()).m_7726_().m_8394_(player, new ClientboundSetPassengersPacket(mount));
            }

         }
      }
   }
}
