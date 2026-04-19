package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.items.armors.WootzArmorItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ClimaTactItem;

public class AbilityUseConditions {
   public static Result canEnableHaki(LivingEntity entity, IAbility ability) {
      IHakiData props = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         return props.getHakiOveruse() < props.getMaxOveruse() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_HAKI);
      }
   }

   public static Result requiresOnGround(LivingEntity entity, IAbility ability) {
      return entity.m_20096_() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_ONLY_IN_GROUND);
   }

   public static Result canUseMomentumAbilities(LivingEntity entity) {
      AttributeInstance attr = entity.m_21051_((Attribute)ModAttributes.JUMP_HEIGHT.get());
      if (attr != null && attr.m_22135_() <= (double)0.0F) {
         return Result.fail(ModI18nAbilities.MESSAGE_CANT_MOVE);
      } else {
         attr = entity.m_21051_(Attributes.f_22279_);
         return attr != null && attr.m_22135_() <= (double)0.0F ? Result.fail(ModI18nAbilities.MESSAGE_CANT_MOVE) : Result.success();
      }
   }

   public static Result canUseMomentumAbilities(LivingEntity entity, IAbility ability) {
      return canUseMomentumAbilities(entity);
   }

   public static Result requiresActiveAbility(LivingEntity entity, IAbility ability, AbilityCore<?>... cores) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         for(AbilityCore<?> core : cores) {
            IAbility abl = props.getEquippedAbility(core);
            if (abl != null && (Boolean)abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).map((comp) -> comp.isContinuous()).orElse(false)) {
               return Result.success();
            }
         }

         Component failMessage = null;
         if (cores.length == 1) {
            failMessage = Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{cores[0].getLocalizedName()});
         } else if (cores.length >= 2) {
            failMessage = Component.m_237110_(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE, new Object[]{cores[0].getLocalizedName(), cores[1].getLocalizedName()});
         }

         return Result.fail(failMessage);
      }
   }

   public static Result requiresMorph(LivingEntity entity, IAbility ability, MorphInfo... morphs) {
      for(MorphInfo morph : morphs) {
         if (morph.isActive(entity)) {
            return Result.success();
         }
      }

      Component failMessage = null;
      if (morphs.length == 1) {
         failMessage = Component.m_237110_(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE, new Object[]{morphs[0].getDisplayName()});
      } else if (morphs.length >= 2) {
         failMessage = Component.m_237110_(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE, new Object[]{morphs[0].getDisplayName(), morphs[1].getDisplayName()});
      }

      return Result.fail(failMessage);
   }

   public static Result requiresTransformationSpace(LivingEntity entity, IAbility ability) {
      MorphInfo info = null;
      if (ability instanceof MorphAbility morphAbility) {
         info = morphAbility.getTransformation();
      } else if (ability.hasComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get())) {
         info = ((MorphComponent)ability.getComponent((AbilityComponentKey)ModAbilityComponents.MORPH.get()).get()).getMorphInfo();
      }

      if (info == null) {
         return Result.fail((Component)null);
      } else {
         EntityDimensions standingSize = info.getSize(entity, Pose.STANDING);
         if (standingSize == null) {
            return Result.success();
         } else {
            float height = standingSize.f_20378_;
            BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(entity.m_20182_().m_7096_(), entity.m_20182_().m_7098_(), entity.m_20182_().m_7094_());

            for(int i = 0; (float)i < height; ++i) {
               BlockState state = entity.m_9236_().m_8055_(checkPos);
               if (!state.m_60795_()) {
                  return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_SPACE);
               }

               checkPos = checkPos.m_122184_(0, 1, 0);
            }

            return Result.success();
         }
      }
   }

   public static Result requiresOneFreeHand(LivingEntity entity, IAbility ability) {
      return !entity.m_21205_().m_41619_() && !entity.m_21206_().m_41619_() ? Result.fail(ModI18nAbilities.MESSAGE_NEED_ONE_FREE_HAND) : Result.success();
   }

   public static Result requiresEmptyHand(LivingEntity entity, IAbility ability) {
      Result hasLimbs = requiresHands(entity, ability);
      if (hasLimbs.isFail()) {
         return hasLimbs;
      } else {
         return !entity.m_21205_().m_41619_() ? Result.fail(ModI18nAbilities.MESSAGE_NEED_FIST) : Result.success();
      }
   }

   public static Result requiresHands(LivingEntity entity, IAbility ability) {
      return entity.m_21023_((MobEffect)ModEffects.NO_HANDS.get()) ? Result.fail(ModI18nAbilities.MESSAGE_NO_LIMBS) : Result.success();
   }

   public static Result requiresClimaTact(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      return stack.m_41720_() instanceof ClimaTactItem ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_CLIMA_TACT);
   }

   public static Result requiresBluntWeapon(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      boolean hasBluntInHand = ItemsHelper.isBlunt(stack);
      return hasBluntInHand ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_BLUNT);
   }

   public static Result requiresSword(LivingEntity entity, IAbility ability) {
      ItemStack stack = entity.m_21205_();
      boolean hasSwordInHand = ItemsHelper.isSword(stack);
      if (hasSwordInHand) {
         return Result.success();
      } else {
         Optional<ContinuousComponent> sparClaw = AbilityCapability.get(entity).filter(Objects::nonNull).map((data) -> (Ability)data.getEquippedAbility((AbilityCore)SparClawAbility.INSTANCE.get())).map((abl) -> abl.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get())).filter(Optional::isPresent).map(Optional::get);
         boolean hasSparClawActive = sparClaw.isPresent() && ((ContinuousComponent)sparClaw.get()).isContinuous();
         return hasSparClawActive && stack.m_41619_() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_SWORD);
      }
   }

   public static Result requiresMeleeWeapon(LivingEntity entity, IAbility ability) {
      if (requiresSword(entity, ability).isSuccess()) {
         return Result.success();
      } else {
         return requiresBluntWeapon(entity, ability).isSuccess() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_MELEE_WEAPON);
      }
   }

   public static Result requiresMedicBag(LivingEntity entity, IAbility ability) {
      boolean hasMedicBag = ItemsHelper.hasItemInSlot(entity, EquipmentSlot.CHEST, (Item)ModArmors.MEDIC_BAG.get());
      return hasMedicBag ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_MEDIC_BAG);
   }

   public static Result requiresAirTime(LivingEntity entity, IAbility ability) {
      return entity.m_20096_() ? Result.fail(ModI18nAbilities.MESSAGE_NEED_AIR_TIME) : Result.success();
   }

   public static Result requiresDryUser(LivingEntity entity, IAbility ability) {
      return !entity.m_20070_() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_ONLY_WHEN_DRY);
   }

   public static Result requiresWootzArmor(LivingEntity entity, IAbility ability) {
      return WootzArmorItem.hasArmorEquipped(entity) ? Result.success() : Result.fail((Component)null);
   }

   public static Result requiresInAir(LivingEntity entity, IAbility ability) {
      return !entity.m_20096_() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_ONLY_IN_AIR);
   }
}
