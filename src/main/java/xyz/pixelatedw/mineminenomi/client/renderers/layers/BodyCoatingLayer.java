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
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class BodyCoatingLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public BodyCoatingLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        PlayerStats stats = PlayerStats.get(entity);
        if (stats == null) return;

        // Busoshoku Haki
        if (stats.isAbilityActive("mineminenomi:busoshoku_haki_full_body_hardening")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.BUSOSHOKU_HAKI_ARM, true);
        } else if (stats.isAbilityActive("mineminenomi:busoshoku_haki_hardening") || 
                   stats.isAbilityActive("mineminenomi:busoshoku_haki_internal_destruction") || 
                   stats.isAbilityActive("mineminenomi:busoshoku_haki_emission")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.BUSOSHOKU_HAKI_ARM, false);
        }

        // Diamond (Kira Kira no Mi)
        if (stats.isAbilityActive("mineminenomi:diamond_body")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.DIAMOND_BODY, true);
        }

        // Candy (Pero Pero no Mi)
        if (stats.isAbilityActive("mineminenomi:candy_armor")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.CANDY_ARMOR, true);
        }

        // Soap (Awa Awa no Mi)
        if (stats.isAbilityActive("mineminenomi:soap_coating")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.AWA_SOAP, true);
        }

        // Mucus (Beta Beta no Mi)
        if (stats.isAbilityActive("mineminenomi:mucus_coating")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.BETA_COATING, true);
        }

        // Poison (Doku Doku no Mi)
        if (stats.isAbilityActive("mineminenomi:poison_coating")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.DOKU_COATING, true);
        }

        // Daibutsu (Hito Hito no Mi, Model: Daibutsu)
        if (stats.isAbilityActive("mineminenomi:daibutsu_form")) {
            renderCoating(poseStack, buffer, packedLight, ModResources.BUDDHA_COATING, true);
        }

        // Frostbite (Hie Hie no Mi)
        if (stats.isAbilityActive("mineminenomi:frostbite")) {
            // Note: The original had multiple stages, here we just use the first one for simplicity or we could check a value
            renderCoating(poseStack, buffer, packedLight, ModResources.FROSTBITE_1, true);
        }
    }

    private void renderCoating(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ResourceLocation texture, boolean fullBody) {
        VertexConsumer vertex = buffer.getBuffer(RenderType.entityTranslucent(texture));
        M model = this.getParentModel();
        
        if (fullBody) {
            model.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        } else {
            // Just arms and legs for standard hardening
            model.leftArm.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
            model.rightArm.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
            model.leftLeg.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
            model.rightLeg.render(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
