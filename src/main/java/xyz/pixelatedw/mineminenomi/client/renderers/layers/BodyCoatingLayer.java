package xyz.pixelatedw.mineminenomi.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IOverlayProvider;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypes;

public class BodyCoatingLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public BodyCoatingLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;

        for (String abilityId : stats.getActiveAbilities()) {
            Ability ability = xyz.pixelatedw.mineminenomi.init.ModAbilities.getAbility(abilityId);
            if (ability instanceof IOverlayProvider provider) {
                AbilityOverlay overlay = provider.getOverlay(entity);
                if (overlay != null) {
                    renderOverlay(poseStack, buffer, packedLight, overlay, stats);
                }
            }
        }
    }

    private void renderOverlay(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbilityOverlay overlay, PlayerStats statsData) {
        VertexConsumer vertex;
        if (overlay.getTexture() != null) {
            boolean isBody = overlay.getOverlayPart() == AbilityOverlay.OverlayPart.BODY;
            RenderType renderType = isBody ? ModRenderTypes.getAbilityBody(overlay.getTexture()) : ModRenderTypes.getAbilityHand(overlay.getTexture());
            vertex = buffer.getBuffer(renderType);
        } else {
            vertex = buffer.getBuffer(overlay.getRenderType() == AbilityOverlay.RenderType.ENERGY ? ModRenderTypes.ENERGY : ModRenderTypes.TRANSPARENT_COLOR);
        }

        float red = overlay.getColor().getRed() / 255.0F;
        float green = overlay.getColor().getGreen() / 255.0F;
        float blue = overlay.getColor().getBlue() / 255.0F;
        float alpha = overlay.getColor().getAlpha() / 255.0F;

        M model = this.getParentModel();
        
        switch (overlay.getOverlayPart()) {
            case BODY:
                model.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                break;
            case LIMB:
                model.leftArm.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                model.rightArm.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                model.leftLeg.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                model.rightLeg.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                break;
            case ARM:
                model.leftArm.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                model.rightArm.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                break;
            case LEG:
                model.leftLeg.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                model.rightLeg.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                break;
            case HEAD:
                model.head.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, ((int)(alpha * 255) << 24) | ((int)(red * 255) << 16) | ((int)(green * 255) << 8) | (int)(blue * 255));
                break;
        }
    }
}
