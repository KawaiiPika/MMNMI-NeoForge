package xyz.pixelatedw.mineminenomi.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.client.render.ModRenderTypeBuffers;
import xyz.pixelatedw.mineminenomi.client.render.buffers.HakiAuraBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final Map<Integer, List<AbilityDisplayData>> ABILITIES_TO_SHOW = new HashMap<>();

    public AuraLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        // Aura Rendering (Kenbunshoku Haki)
        if (entity != player) {
            PlayerStats playerStats = PlayerStats.get(player);
            if (playerStats != null && playerStats.isKenbunshokuActive()) {
                double doriki = playerStats.getDoriki();
                float kenExp = playerStats.getKenbunshokuHakiExp();
                boolean hasGoro = playerStats.getDevilFruit().map(df -> df.getPath().contains("goro_goro")).orElse(false);

                double dorikiPower = doriki / 1000.0;
                float hakiPower = kenExp / 2.0f;
                double finalPower = (dorikiPower + hakiPower) * 1.06 * (hasGoro ? 4.0 : 1.0);

                if (entity.distanceToSqr(player) <= finalPower * finalPower) {
                    matrixStack.pushPose();
                    
                    int rgb = 0x5555FF; // Default blue-ish
                    if (entity instanceof Animal) {
                        rgb = 0x55FF55; // Green for animals
                    } else if (entity instanceof Monster) {
                        rgb = 0xFF0000; // Red for monsters
                    } else if (entity instanceof Player) {
                        rgb = 0x00FFFF; // Cyan for players
                    }

                    int red = (rgb >> 16 & 255);
                    int green = (rgb >> 8 & 255);
                    int blue = (rgb & 255);

                    HakiAuraBuffer layerBuffer = ModRenderTypeBuffers.getInstance().getHakiAuraBuffer();
                    if (layerBuffer != null) {
                        layerBuffer.setColor(red, green, blue, (int)(0.4f * 255));
                        VertexConsumer vertex = layerBuffer.getBuffer(ModRenderTypes.getAuraRenderType());
                        matrixStack.scale(1.02f, 1.02f, 1.02f);
                        this.getParentModel().renderToBuffer(matrixStack, vertex, packedLight, OverlayTexture.NO_OVERLAY);
                    }
                    
                    matrixStack.popPose();
                }
            }
        }

        // Ability Icons Rendering
        List<AbilityDisplayData> abilities = ABILITIES_TO_SHOW.get(entity.getId());
        if (abilities != null && !abilities.isEmpty()) {
            float heightOffset = 0.0f;
            long currentTime = System.currentTimeMillis();
            
            for (int i = 0; i < abilities.size(); i++) {
                AbilityDisplayData data = abilities.get(i);
                long elapsed = currentTime - data.startTime;
                
                if (elapsed > 4000) {
                    abilities.remove(i--);
                    continue;
                }
                
                float alpha = 1.0f;
                if (elapsed <= 500) alpha = elapsed / 500.0f;
                else if (elapsed >= 3500) alpha = 1.0f - (elapsed - 3500) / 500.0f;

                matrixStack.pushPose();
                // Move above head, considering eye height
                float yPos = -entity.getEyeHeight() - 1.0f - heightOffset;
                matrixStack.translate(0.5, yPos, 0); 
                matrixStack.scale(0.5f, 0.5f, 0.5f);
                
                // Simplified icon drawing
                RendererHelper.drawIcon(data.ability.getTexture(), matrixStack, 0, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, alpha, false);
                
                matrixStack.popPose();
                heightOffset += 0.6f;
            }
        }
    }

    public static void addAbilityIcon(LivingEntity entity, Ability ability) {
        if (entity.level().isClientSide) {
            ABILITIES_TO_SHOW.putIfAbsent(entity.getId(), new ArrayList<>());
            List<AbilityDisplayData> list = ABILITIES_TO_SHOW.get(entity.getId());
            
            boolean found = false;
            for (AbilityDisplayData data : list) {
                if (data.ability == ability) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                list.add(0, new AbilityDisplayData(ability, System.currentTimeMillis()));
            }
        }
    }

    private record AbilityDisplayData(Ability ability, long startTime) {}
}
