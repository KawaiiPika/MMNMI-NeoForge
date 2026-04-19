package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.abilities.haki.KenbunshokuHakiAuraAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SlotDecorationComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypeBuffers;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.renderers.buffers.HakiAuraBuffer;

public class AuraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private static final HashMap<Integer, ArrayList<AbilityDisplayData>> ABILITIES_TO_SHOW = new HashMap();
   private static final long PHASE_IN_DURATION = 500L;
   private static final long FULL_VISIBLE_DURATION = 3000L;
   private static final long PHASE_OUT_DURATION = 500L;

   public AuraLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      Minecraft mc = Minecraft.m_91087_();
      Player player = mc.f_91074_;
      if (entity != player) {
         boolean isAuraActive = (Boolean)AbilityCapability.get(player).map((props) -> (KenbunshokuHakiAuraAbility)props.getEquippedAbility((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get())).map(Ability::isContinuous).orElse(false);
         if (isAuraActive) {
            double doriki = (Double)EntityStatsCapability.get(player).map(IEntityStats::getDoriki).orElse((double)0.0F);
            float kenExp = (Float)HakiCapability.get(player).map(IHakiData::getKenbunshokuHakiExp).orElse(0.0F);
            boolean hasGoro = DevilFruitCapability.hasDevilFruit(player, (AkumaNoMiItem)ModFruits.GORO_GORO_NO_MI.get());
            double dorikiPower = doriki / (double)1000.0F;
            float hakiPower = kenExp / 2.0F;
            double finalPower = (dorikiPower + (double)hakiPower) * 1.06 * (hasGoro ? (double)4.0F : (double)1.0F);
            if (!((double)entity.m_20270_(player) > finalPower)) {
               matrixStack.m_85836_();
               int rgb = 5592575;
               if (entity instanceof Animal) {
                  rgb = 5635925;
               } else if (entity instanceof Monster) {
                  rgb = 16711680;
               } else if (entity instanceof Player) {
                  rgb = 65535;
               }

               int val = -16777216 | rgb;
               int red = val >> 16 & 255;
               int green = val >> 8 & 255;
               int blue = val >> 0 & 255;
               HakiAuraBuffer layerBuffer = ModRenderTypeBuffers.getInstance().getHakiAuraBuffer();
               layerBuffer.setColor(red, green, blue, 127);
               VertexConsumer vertex = layerBuffer.m_6299_(ModRenderTypes.getAuraRenderType());
               this.m_117386_().m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
               layerBuffer.endBatch();
               matrixStack.m_85849_();
               if (!(kenExp < 50.0F)) {
                  ArrayList<AbilityDisplayData> abilitiesToShow = (ArrayList)ABILITIES_TO_SHOW.get(entity.m_19879_());
                  if (abilitiesToShow != null) {
                     float height = 0.0F;

                     for(int i = 0; i < abilitiesToShow.size(); ++i) {
                        AbilityDisplayData abilityDisplayData = (AbilityDisplayData)abilitiesToShow.get(i);
                        IAbility ability = abilityDisplayData.ability;
                        long elapsedTime = System.currentTimeMillis() - abilityDisplayData.startTime;
                        float iconAlpha = 0.0F;
                        Optional<SlotDecorationComponent> slotDecorationComponentOptional = ability.<SlotDecorationComponent>getComponent((AbilityComponentKey)ModAbilityComponents.SLOT_DECORATION.get());
                        if (slotDecorationComponentOptional.isPresent()) {
                           SlotDecorationComponent slotDecorationComponent = (SlotDecorationComponent)slotDecorationComponentOptional.get();
                           TexturedRectUI rect = new TexturedRectUI(ModResources.WIDGETS);
                           slotDecorationComponent.triggerPreRenderEvents(player, mc, matrixStack, buffer, rect, 1.25F, -entity.m_20192_() + 1.0F - height, partialTicks);
                           if (elapsedTime <= 500L) {
                              iconAlpha = slotDecorationComponent.getIconAlpha() / 500.0F * (float)elapsedTime;
                           } else if (elapsedTime <= 3500L) {
                              iconAlpha = slotDecorationComponent.getIconAlpha();
                           } else if (elapsedTime <= 4000L) {
                              iconAlpha = slotDecorationComponent.getIconAlpha() - (float)(elapsedTime - 3500L) / 500.0F;
                           }

                           matrixStack.m_85836_();
                           float number = slotDecorationComponent.getCurrentValue();
                           float maxNumber = slotDecorationComponent.getMaxValue();
                           float slotHeight = Mth.m_14036_(23.0F - number / maxNumber * 23.0F, 0.0F, Float.MAX_VALUE);
                           float slotAlpha = iconAlpha / slotDecorationComponent.getIconAlpha() * 1.0F;
                           float slotYPos = -entity.m_20192_() + 1.0F - height;
                           rect.reset();
                           rect.setColor(slotDecorationComponent.getSlotRed(), slotDecorationComponent.getSlotGreen(), slotDecorationComponent.getSlotBlue(), slotAlpha);
                           rect.setSize(23.0F, 23.0F);
                           rect.setScale(0.0625F, 0.0625F);
                           rect.setUV(0.0F, 0.0F);
                           rect.draw(matrixStack, 1.25F, slotYPos);
                           rect.reset();
                           rect.setColor(1.0F, 1.0F, 1.0F, slotAlpha);
                           rect.setUV(0.0F, 0.0F);
                           rect.setSize(23.0F, slotHeight);
                           rect.setScale(0.0625F, 0.0625F);
                           rect.draw(matrixStack, 1.25F, slotYPos);
                           matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F));
                           rect.reset();
                           rect.setColor(slotDecorationComponent.getSlotRed(), slotDecorationComponent.getSlotGreen(), slotDecorationComponent.getSlotBlue(), slotAlpha);
                           rect.setSize(23.0F, 23.0F);
                           rect.setScale(0.0625F, 0.0625F);
                           rect.setUV(0.0F, 0.0F);
                           rect.draw(matrixStack, -2.25F, slotYPos);
                           rect.reset();
                           rect.setColor(1.0F, 1.0F, 1.0F, slotAlpha);
                           rect.setUV(0.0F, 0.0F);
                           rect.setSize(23.0F, slotHeight);
                           rect.setScale(0.0625F, 0.0625F);
                           rect.draw(matrixStack, -2.25F, slotYPos);
                           matrixStack.m_85849_();
                           matrixStack.m_85836_();
                           RendererHelper.drawIcon(ability.getIcon(entity), matrixStack, 1.5F, -entity.m_20192_() + 1.0F - height + 0.25F, 0.0F, 1.0F, 1.0F, slotDecorationComponent.getIconRed(), slotDecorationComponent.getIconGreen(), slotDecorationComponent.getIconBlue(), iconAlpha);
                           matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F));
                           RendererHelper.drawIcon(ability.getIcon(entity), matrixStack, -2.0F, -entity.m_20192_() + 1.0F - height + 0.25F, -0.0F, 1.0F, 1.0F, slotDecorationComponent.getIconRed(), slotDecorationComponent.getIconGreen(), slotDecorationComponent.getIconBlue(), iconAlpha, false);
                           matrixStack.m_85849_();
                           ++height;
                        }

                        if (elapsedTime >= 4000L) {
                           abilitiesToShow.remove(abilityDisplayData);
                        }
                     }

                  }
               }
            }
         }
      }
   }

   public static void addAbilityIcon(LivingEntity entity, IAbility ability) {
      if (entity.m_9236_().f_46443_) {
         ABILITIES_TO_SHOW.putIfAbsent(entity.m_19879_(), new ArrayList());
         ArrayList<AbilityDisplayData> abilitiesToShow = (ArrayList)ABILITIES_TO_SHOW.get(entity.m_19879_());
         boolean abilityFound = false;

         for(int i = 0; i < abilitiesToShow.size(); ++i) {
            if (((AbilityDisplayData)abilitiesToShow.get(i)).ability.equals(ability)) {
               abilityFound = true;
            }
         }

         if (!abilityFound) {
            abilitiesToShow.add(0, new AbilityDisplayData(ability, System.currentTimeMillis()));
         }

      }
   }

   private static record AbilityDisplayData(IAbility ability, long startTime) {
   }
}
