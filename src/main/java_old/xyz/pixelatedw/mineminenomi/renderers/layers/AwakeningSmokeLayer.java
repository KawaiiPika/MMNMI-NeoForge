package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFifthAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.models.entities.AwakeningSmokeModel;

public class AwakeningSmokeLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation SMOKE_0 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/awakening_smoke/smoke_0.png");
   private static final ResourceLocation SMOKE_1 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/awakening_smoke/smoke_1.png");
   private static final ResourceLocation SMOKE_2 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/awakening_smoke/smoke_2.png");
   private static final ResourceLocation SMOKE_3 = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/awakening_smoke/smoke_3.png");
   private static final ResourceLocation[] SMOKE_ANIM;
   private static final float SCALE = 1.3F;
   private AwakeningSmokeModel model;

   public AwakeningSmokeLayer(EntityRendererProvider.Context ctx, RenderLayerParent<T, M> renderer) {
      super(renderer);
      this.model = new AwakeningSmokeModel(ctx.m_174023_(AwakeningSmokeModel.LAYER_LOCATION));
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (this.canRender(entity)) {
         float speed = 1000.0F;
         float anim = (float)Util.m_137550_() % speed / (speed / (float)SMOKE_ANIM.length);
         matrixStack.m_85836_();
         matrixStack.m_85841_(1.3F, 1.3F, 1.3F);
         VertexConsumer ivb = buffer.m_6299_(RenderType.m_110473_(SMOKE_ANIM[(int)Math.floor((double)anim)]));
         this.model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         this.model.m_7695_(matrixStack, ivb, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStack.m_85849_();
      }
   }

   public boolean canRender(T entity) {
      return (Boolean)AbilityCapability.get(entity).map((props) -> (GearFifthAbility)props.getEquippedAbility((AbilityCore)GearFifthAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
   }

   static {
      SMOKE_ANIM = new ResourceLocation[]{SMOKE_0, SMOKE_1, SMOKE_2, SMOKE_3};
   }
}
