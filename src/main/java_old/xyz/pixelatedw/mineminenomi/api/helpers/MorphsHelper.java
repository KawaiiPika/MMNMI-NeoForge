package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Map;
import java.util.Optional;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.renderers.morphs.MorphRenderer;

public class MorphsHelper {
   public static <T extends LivingEntity> @Nullable EntityRenderer<T> getMorphRenderer(T living, EntityRenderer<? extends Entity> original) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(living).orElse((Object)null);
      if (props == null) {
         return null;
      } else {
         Minecraft mc = Minecraft.m_91087_();
         boolean isFirstPerson = living == mc.f_91074_ && mc.f_91066_.m_92176_() == CameraType.FIRST_PERSON;
         boolean hasItemInHand = !living.m_21205_().m_41619_();
         if (isFirstPerson && hasItemInHand) {
            return null;
         } else {
            MorphInfo morphInfo = (MorphInfo)props.getCurrentMorph().orElse((Object)null);
            if (morphInfo == null) {
               return null;
            } else {
               EntityRenderer<? extends LivingEntity> renderer = (EntityRenderer)ModMorphs.Client.MORPH_RENDERERS.get(morphInfo);
               if (renderer instanceof MorphRenderer) {
                  MorphRenderer morphRenderer = (MorphRenderer)renderer;
                  morphRenderer.setOriginalRenderer(original);
               }

               return renderer;
            }
         }
      }
   }

   public static float getMorphEyeHeight(LivingEntity entity) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return -1.0F;
      } else {
         Optional<MorphInfo> morph = props.getCurrentMorph();
         if (morph.isPresent()) {
            float eyeHeigth = ((MorphInfo)morph.get()).getEyeHeight(entity);
            if (eyeHeigth > 0.0F) {
               return eyeHeigth;
            }
         } else {
            for(MorphInfo info : props.getActiveMorphs()) {
               if (info.isPartial()) {
                  float eyeHeigth = info.getEyeHeight(entity);
                  if (eyeHeigth > 0.0F) {
                     return eyeHeigth;
                  }
               }
            }
         }

         return -1.0F;
      }
   }

   public static @Nullable EntityDimensions getMorphDimensions(LivingEntity living, Pose pose) {
      IDevilFruit props = (IDevilFruit)DevilFruitCapability.get(living).orElse((Object)null);
      if (props == null) {
         return null;
      } else {
         Optional<MorphInfo> morph = props.getCurrentMorph();
         if (morph.isPresent()) {
            Map<Pose, EntityDimensions> poses = ((MorphInfo)morph.get()).getSizes();
            if (poses != null && poses.containsKey(living.m_20089_()) && poses.get(living.m_20089_()) != null) {
               return (EntityDimensions)poses.get(living.m_20089_());
            }
         } else {
            for(MorphInfo info : props.getActiveMorphs()) {
               if (info.isPartial()) {
                  Map<Pose, EntityDimensions> poses = info.getSizes();
                  if (poses != null && poses.containsKey(living.m_20089_()) && poses.get(living.m_20089_()) != null) {
                     return (EntityDimensions)poses.get(living.m_20089_());
                  }
               }
            }
         }

         return null;
      }
   }

   /** @deprecated */
   @Deprecated(
      forRemoval = true
   )
   public static @Nullable MorphInfo getZoanInfo(LivingEntity entity) {
      IDevilFruit devilFruitProps = (IDevilFruit)DevilFruitCapability.get(entity).orElse((Object)null);
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (devilFruitProps != null && abilityProps != null) {
         if (devilFruitProps.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) && ((MorphInfo)ModMorphs.YOMI_SKELETON.get()).isActive(entity)) {
            return (MorphInfo)ModMorphs.YOMI_SKELETON.get();
         } else {
            Optional<MorphInfo> morph = devilFruitProps.getCurrentMorph();
            if (morph.isPresent()) {
               return (MorphInfo)morph.get();
            } else {
               for(IAbility ability : abilityProps.getEquippedAbilities()) {
                  if (ability.hasComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get())) {
                     MorphComponent comp = (MorphComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get()).get();
                     boolean isMorphed = comp.isMorphed();
                     if (isMorphed) {
                        return comp.getMorphInfo();
                     }
                  }
               }

               return null;
            }
         }
      } else {
         return null;
      }
   }
}
