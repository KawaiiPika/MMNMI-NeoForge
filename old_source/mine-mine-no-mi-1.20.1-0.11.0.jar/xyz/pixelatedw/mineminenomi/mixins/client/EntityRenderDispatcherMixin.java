package xyz.pixelatedw.mineminenomi.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

@Mixin({EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
   @Shadow
   public Map<EntityType<?>, EntityRenderer<?>> f_114362_;
   @Shadow
   private Map<String, EntityRenderer<? extends Player>> f_114363_;

   @Inject(
      method = {"renderShadow"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void mineminenomi$renderShadow(PoseStack matrixStack, MultiBufferSource buffer, Entity entity, float weight, float partialTicks, LevelReader world, float size, CallbackInfo callback) {
      if (entity instanceof LivingEntity living) {
         boolean hasShadow = (Boolean)EntityStatsCapability.get(living).map((props) -> props.hasShadow()).orElse(false);
         if (!hasShadow) {
            callback.cancel();
            return;
         }
      }

   }

   @Inject(
      method = {"getRenderer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public <T extends Entity> void mineminenomi$getRenderer(T entity, CallbackInfoReturnable<EntityRenderer<? super T>> cir) {
      if (entity instanceof LivingEntity living) {
         EntityRenderDispatcher dispatcher = (EntityRenderDispatcher)this;
         EntityRenderer<? super T> defaultRenderer = this.<T>getOriginalRenderer(entity);
         EntityRenderer<T> renderer = MorphsHelper.<T>getMorphRenderer(living, defaultRenderer);
         if (renderer != null) {
            cir.setReturnValue(renderer);
         }
      }

   }

   private <T extends Entity> EntityRenderer<? super T> getOriginalRenderer(T entity) {
      if (entity instanceof AbstractClientPlayer player) {
         String s = player.m_108564_();
         EntityRenderer<? extends Player> entityrenderer = (EntityRenderer)this.f_114363_.get(s);
         return entityrenderer != null ? entityrenderer : (EntityRenderer)this.f_114363_.get("default");
      } else {
         return (EntityRenderer)this.f_114362_.get(entity.m_6095_());
      }
   }
}
