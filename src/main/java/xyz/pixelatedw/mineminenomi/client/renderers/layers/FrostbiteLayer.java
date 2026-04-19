package xyz.pixelatedw.mineminenomi.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class FrostbiteLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public FrostbiteLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        MobEffectInstance frostbite = entity.getEffect(ModEffects.FROSTBITE);
        if (frostbite != null) {
            ResourceLocation texture = getFrostbiteTexture(frostbite.getAmplifier());
            VertexConsumer vertex = buffer.getBuffer(RenderType.entityTranslucent(texture));
            this.getParentModel().renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }
        
        if (entity.hasEffect(ModEffects.FROZEN)) {
            VertexConsumer vertex = buffer.getBuffer(RenderType.entityTranslucent(ModResources.FROSTBITE_5));
            this.getParentModel().renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    private ResourceLocation getFrostbiteTexture(int amplifier) {
        if (amplifier < 2) return ModResources.FROSTBITE_1;
        if (amplifier < 4) return ModResources.FROSTBITE_2;
        if (amplifier < 6) return ModResources.FROSTBITE_3;
        if (amplifier < 8) return ModResources.FROSTBITE_4;
        return ModResources.FROSTBITE_5;
    }
}
