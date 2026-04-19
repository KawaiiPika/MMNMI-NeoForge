package xyz.pixelatedw.mineminenomi.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFourthAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.morph.HumanoidMorphModel;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.morph.RacialRenderFeature;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.models.entities.mobs.animals.DugongModel;

public class BodyCoatingLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public BodyCoatingLayer(RenderLayerParent<T, M> renderer) {
      super(renderer);
   }

   public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      IEntityStats statsData = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (statsData != null && abilityData != null && fruitData != null) {
         GearFourthAbility g4Ability = (GearFourthAbility)abilityData.getEquippedAbility((AbilityCore)GearFourthAbility.INSTANCE.get());
         boolean hasG4 = g4Ability != null && g4Ability.isContinuous();
         VertexConsumer vertex = null;
         Optional<AbilityOverlay> overlay = Optional.empty();
         float red = 1.0F;
         float green = 1.0F;
         float blue = 1.0F;
         float alpha = 1.0F;

         for(IAbility ability : abilityData.getEquippedAbilities()) {
            Optional<SkinOverlayComponent> comp = ability.<SkinOverlayComponent>getComponent((AbilityComponentKey)ModAbilityComponents.SKIN_OVERLAY.get());
            if (!comp.isEmpty()) {
               overlay = ((SkinOverlayComponent)comp.get()).getShownOverlay(AbilityOverlay.OverlayPart.LIMB, AbilityOverlay.OverlayPart.LEG, AbilityOverlay.OverlayPart.ARM);
               if (overlay.isPresent() && this.m_117386_() instanceof ArmedModel) {
                  red = (float)((AbilityOverlay)overlay.get()).getColor().getRed() / 255.0F;
                  green = (float)((AbilityOverlay)overlay.get()).getColor().getGreen() / 255.0F;
                  blue = (float)((AbilityOverlay)overlay.get()).getColor().getBlue() / 255.0F;
                  alpha = (float)((AbilityOverlay)overlay.get()).getColor().getAlpha() / 255.0F;
                  if (((AbilityOverlay)overlay.get()).getTexture() != null) {
                     vertex = buffer.m_6299_(ModRenderTypes.getAbilityHand(((AbilityOverlay)overlay.get()).getTexture()));
                  } else {
                     vertex = ((AbilityOverlay)overlay.get()).getRenderType().equals(AbilityOverlay.RenderType.ENERGY) ? buffer.m_6299_(ModRenderTypes.ENERGY) : buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
                  }

                  boolean isLeftHanded = entity.m_5737_() == HumanoidArm.LEFT;
                  ModelPart limbToRender = null;
                  boolean useLegModel = ((AbilityOverlay)overlay.get()).getOverlayPart().equals(AbilityOverlay.OverlayPart.LEG) || statsData.isBlackLeg() && ((AbilityOverlay)overlay.get()).getOverlayPart().equals(AbilityOverlay.OverlayPart.LIMB);
                  boolean useArmModel = ((AbilityOverlay)overlay.get()).getOverlayPart().equals(AbilityOverlay.OverlayPart.ARM) || !statsData.isBlackLeg() && ((AbilityOverlay)overlay.get()).getOverlayPart().equals(AbilityOverlay.OverlayPart.LIMB);
                  if (useLegModel) {
                     if (this.m_117386_() instanceof HumanoidMorphModel) {
                        break;
                     }

                     EntityModel var32 = this.m_117386_();
                     if (var32 instanceof PlayerModel) {
                        PlayerModel playerModel = (PlayerModel)var32;
                        limbToRender = isLeftHanded ? playerModel.f_103376_ : playerModel.f_103377_;
                     } else {
                        var32 = this.m_117386_();
                        if (var32 instanceof HumanoidModel) {
                           HumanoidModel humanoidModel = (HumanoidModel)var32;
                           limbToRender = isLeftHanded ? humanoidModel.f_102814_ : humanoidModel.f_102813_;
                        } else {
                           var32 = this.m_117386_();
                           if (var32 instanceof DugongModel) {
                              DugongModel dugongModel = (DugongModel)var32;
                              limbToRender = dugongModel.tailBase;
                           }
                        }
                     }
                  } else if (useArmModel) {
                     if (this.m_117386_() instanceof HumanoidMorphModel) {
                        break;
                     }

                     EntityModel var59 = this.m_117386_();
                     if (var59 instanceof PlayerModel) {
                        PlayerModel playerModel = (PlayerModel)var59;
                        limbToRender = isLeftHanded ? playerModel.f_103374_ : playerModel.f_103375_;
                     } else {
                        var59 = this.m_117386_();
                        if (var59 instanceof HumanoidModel) {
                           HumanoidModel humanoidModel = (HumanoidModel)var59;
                           limbToRender = isLeftHanded ? humanoidModel.f_102812_ : humanoidModel.f_102811_;
                        } else {
                           var59 = this.m_117386_();
                           if (var59 instanceof DugongModel) {
                              DugongModel dugongModel = (DugongModel)var59;
                              limbToRender = isLeftHanded ? dugongModel.leftArm : dugongModel.rightArm;
                           }
                        }
                     }
                  }

                  if (limbToRender != null) {
                     limbToRender.m_104306_(poseStack, vertex, packedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
                  }
               }

               overlay = ((SkinOverlayComponent)comp.get()).getShownOverlay(AbilityOverlay.OverlayPart.BODY);
               if (overlay.isPresent()) {
                  red = (float)((AbilityOverlay)overlay.get()).getColor().getRed() / 255.0F;
                  green = (float)((AbilityOverlay)overlay.get()).getColor().getGreen() / 255.0F;
                  blue = (float)((AbilityOverlay)overlay.get()).getColor().getBlue() / 255.0F;
                  alpha = (float)((AbilityOverlay)overlay.get()).getColor().getAlpha() / 255.0F;
                  if (hasG4 && ability instanceof BusoshokuHakiFullBodyHardeningAbility) {
                     alpha = 0.0F;
                  }

                  boolean equalDepthTest = false;
                  boolean culling = false;
                  Optional<MorphInfo> info = fruitData.getCurrentMorph();
                  if (info.isPresent()) {
                     equalDepthTest = ((MorphInfo)info.get()).hasEqualDepthTest();
                     culling = ((MorphInfo)info.get()).hasCulling();
                  }

                  if (((AbilityOverlay)overlay.get()).getTexture() != null) {
                     vertex = buffer.m_6299_(ModRenderTypes.getAbilityBody(((AbilityOverlay)overlay.get()).getTexture(), equalDepthTest, culling));
                  } else {
                     vertex = ((AbilityOverlay)overlay.get()).getRenderType().equals(AbilityOverlay.RenderType.ENERGY) ? buffer.m_6299_(ModRenderTypes.ENERGY) : buffer.m_6299_(ModRenderTypes.TRANSPARENT_COLOR);
                  }

                  this.m_117386_().m_7695_(poseStack, vertex, packedLight, OverlayTexture.f_118083_, red, green, blue, alpha);

                  for(MorphInfo morph : fruitData.getActiveMorphs()) {
                     if (morph.isPartial()) {
                        EntityModel<? extends LivingEntity> partialModel = (EntityModel)ModMorphs.Client.PARTIAL_MORPHS_RENDERERS.get(morph);
                        if (partialModel != null) {
                           partialModel.m_7695_(poseStack, vertex, packedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
                           break;
                        }
                     }
                  }

                  Race race = (Race)statsData.getRace().orElse((Object)null);
                  if (race != null && race.hasRenderFeatures()) {
                     RacialRenderFeature feature = (RacialRenderFeature)ModMorphs.Client.RACIAL_RENDERERS.get(race);
                     if (race.hasSubRaces() && statsData.getSubRace().isPresent()) {
                        Race subRace = (Race)statsData.getSubRace().get();
                        feature = (RacialRenderFeature)ModMorphs.Client.RACIAL_RENDERERS.get(subRace);
                     }

                     if (feature != null) {
                        feature.getModel().m_7695_(poseStack, vertex, packedLight, OverlayTexture.f_118083_, red, green, blue, alpha);
                     }
                  }
               }
            }
         }

      }
   }
}
