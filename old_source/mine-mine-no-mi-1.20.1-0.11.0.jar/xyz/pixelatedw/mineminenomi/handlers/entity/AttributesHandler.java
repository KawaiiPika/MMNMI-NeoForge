package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.abilities.haki.HaoshokuHakiInfusionAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;

public class AttributesHandler {
   private static final float MAX_PROTECTION_THRESHOLD = 0.95F;
   private static final float PROTECTION_ARMOR_PERCENTAGE = 0.4F;
   private static final float PROTECTION_TOUGHNESS_PERCENTAGE = 0.6F;
   private static final float PLOT_ARMOR_REDUCTION = 0.4F;
   private static final float CHALLENGE_PLOT_ARMOR_REDUCTION = 0.8F;
   private static final int JUMP_TICK_DELAY = 1;
   private static final int MAX_JUMP_TICKS = 9;

   public static float applyDamageBonuses(DamageSource damageSource, LivingEntity target, float originalDamage) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(damageSource);
      ItemStack heldItem = ItemStack.f_41583_;
      LivingEntity attacker = null;
      Entity sourceEntity = damageSource.m_7639_();
      Entity directEntity = damageSource.m_7640_();
      boolean shouldApplyMeleeHardening = false;
      boolean shouldApplyMeleeImbuing = false;
      if (directEntity instanceof LivingEntity directAttacker) {
         attacker = directAttacker;
         heldItem = directAttacker.m_21205_();
         shouldApplyMeleeHardening = heldItem.m_41619_() || heldItem.m_204117_(ModTags.Items.HARDENING_DAMAGE_BONUS);
         shouldApplyMeleeImbuing = heldItem.m_204117_(ModTags.Items.IMBUING_DAMAGE_BONUS);
      }

      if (sourceEntity instanceof LivingEntity sourceAttacker) {
         attacker = sourceAttacker;
         heldItem = sourceAttacker.m_21205_();
      }

      float typeBonus = 0.0F;
      float hakiBonus = 0.0F;
      if (attacker == null) {
         return originalDamage;
      } else {
         boolean isAbilitySource = damageSource.m_276093_(ModDamageTypes.ABILITY);
         boolean isDirectSource = sourceHandler.hasType(SourceType.FIST) && heldItem.m_41619_();
         if (sourceHandler.hasType(SourceType.INDIRECT)) {
            isDirectSource = false;
         }

         AttributeInstance attackDamageAttr = attacker.m_21051_(Attributes.f_22281_);
         AttributeInstance punchDamageAttr = attacker.m_21051_((Attribute)ModAttributes.PUNCH_DAMAGE.get());
         float heldItemDamage = ItemsHelper.getItemDamage(attacker.m_21205_()) + 1.0F;
         boolean hasSlashBonus = sourceHandler.hasType(SourceType.SLASH) && ItemsHelper.isSword(heldItem);
         boolean hasBluntBonus = sourceHandler.hasType(SourceType.BLUNT) && ItemsHelper.isBlunt(heldItem);
         boolean hasFistBonus = sourceHandler.hasType(SourceType.FIST) && heldItem.m_41619_();
         if (isAbilitySource) {
            if (!hasSlashBonus && !hasBluntBonus) {
               if (hasFistBonus && punchDamageAttr != null) {
                  typeBonus = (float)punchDamageAttr.m_22135_();
               }
            } else {
               typeBonus = heldItemDamage;
            }
         } else if (isDirectSource && hasFistBonus && punchDamageAttr != null) {
            typeBonus = (float)punchDamageAttr.m_22135_();
         }

         boolean hasHardeningActive = HakiHelper.hasHardeningActive(attacker, false, false);
         boolean hasImbuingActive = HakiHelper.hasImbuingActive(attacker, false, false);
         boolean hasAdvancedBusoActive = HakiHelper.hasAdvancedBusoActive(attacker);
         boolean hasSpecialBonus = sourceHandler.hasHakiNature(SourceHakiNature.SPECIAL);
         boolean hasHardeningBonus = (shouldApplyMeleeHardening || hasSpecialBonus || sourceHandler.hasHakiNature(SourceHakiNature.HARDENING)) && hasHardeningActive;
         boolean hasImbuingBonus = (shouldApplyMeleeImbuing || hasSpecialBonus || sourceHandler.hasHakiNature(SourceHakiNature.IMBUING)) && hasImbuingActive;
         boolean hasAdvancedBusoBonus = (shouldApplyMeleeHardening || shouldApplyMeleeImbuing || hasSpecialBonus || sourceHandler.hasHakiNature(SourceHakiNature.HARDENING) || sourceHandler.hasHakiNature(SourceHakiNature.IMBUING)) && hasAdvancedBusoActive;
         boolean hasInfusion = !sourceHandler.hasHakiNature(SourceHakiNature.UNKNOWN) && HakiHelper.hasInfusionActive(attacker);
         if (hasAdvancedBusoBonus) {
            hakiBonus = (float)((double)hakiBonus + HakiHelper.getAdvancedBusoshokuHakiDamageBoost(attacker, originalDamage));
         } else if (hasHardeningBonus || hasImbuingBonus) {
            hakiBonus += (float)HakiHelper.getBasicBusoshokuHakiDamageBoost(attacker, originalDamage);
         }

         if (hasInfusion) {
            hakiBonus = (float)((double)hakiBonus + HaoshokuHakiInfusionAbility.getDamageBoost(attacker, originalDamage));
         }

         originalDamage += typeBonus + hakiBonus;
         return originalDamage;
      }
   }

   public static float getDamageAfterProtection(DamageSource source, LivingEntity target) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      AttributeInstance toughnessAttribute = target.m_21051_((Attribute)ModAttributes.TOUGHNESS.get());
      AttributeInstance armorAttribute = target.m_21051_(Attributes.f_22284_);
      double toughness = (double)0.0F;
      double armor = (double)0.0F;
      float armorDamage = 0.0F;
      if (toughnessAttribute != null) {
         toughness = toughnessAttribute.m_22135_() / ((RangedAttribute)toughnessAttribute.m_22099_()).m_147362_();
      }

      if (armorAttribute != null) {
         armor = armorAttribute.m_22135_() / ((RangedAttribute)armorAttribute.m_22099_()).m_147362_();
      }

      float finalDamage = 0.0F;
      float globalPiercing = 0.0F;
      if (source.m_269533_(DamageTypeTags.f_268490_)) {
         globalPiercing = 1.0F;
      } else {
         globalPiercing = sourceHandler.getGlobalPiercing();
      }

      toughness *= (double)0.6F;
      armor *= (double)0.4F;

      for(Map.Entry<IDamageSourceHandler.NuDamageValue, Float> entry : sourceHandler.getDamageValues()) {
         float piercing = Mth.m_14036_(sourceHandler.getAbilityPiercing(((IDamageSourceHandler.NuDamageValue)entry.getKey()).ability()) + globalPiercing, 0.0F, 1.0F);
         float nuDamage = (Float)entry.getValue();
         nuDamage = applyDamageBonuses(source, target, nuDamage);
         if (((IDamageSourceHandler.NuDamageValue)entry.getKey()).ability() != null) {
            nuDamage *= 0.4F;
         }

         armorDamage += (nuDamage - nuDamage * 0.72F) * (1.0F - piercing);
         double armorAfterPiercing = armor * ((double)1.0F - (double)piercing);
         double totalDef = Math.min(toughness + armorAfterPiercing, (double)0.95F);
         float totalReduction = (float)((double)nuDamage * totalDef);
         nuDamage -= totalReduction;
         finalDamage += nuDamage;
      }

      if (target instanceof OPBossEntity && NuWorld.isChallengeDimension(target.m_9236_())) {
         finalDamage *= 0.8F;
      }

      if (target instanceof Player targetPlayer) {
         targetPlayer.m_150109_().m_150072_(source, armorDamage, Inventory.f_150068_);
      }

      return finalDamage;
   }

   public static void handleHurtMath(DamageSource source, LivingEntity target, float amount) {
      amount = Math.max(amount, 0.1F);
      float finalAmount = Math.max(amount - target.m_6103_(), 0.0F);
      target.m_7911_(target.m_6103_() - (amount - finalAmount));
      float absorbedAmount = amount - finalAmount;
      if (absorbedAmount > 0.0F && absorbedAmount < 3.4F) {
         if (target instanceof Player && FGCommand.SHOW_DEBUG_DAMAGE) {
            WyDebug.debug(String.format("Absorbsion Damaged by %f", absorbedAmount));
         }

         Entity entity = source.m_7639_();
         if (entity instanceof ServerPlayer) {
            ServerPlayer serverplayer = (ServerPlayer)entity;
            serverplayer.m_36222_(Stats.f_12929_, Math.round(absorbedAmount * 10.0F));
         }
      }

      if (source.m_7639_() instanceof Player && FGCommand.SHOW_DEBUG_DAMAGE) {
         WyDebug.debug(String.format("%s got hit with %f", target.m_7755_().getString(), finalAmount));
      }

      finalAmount = ForgeHooks.onLivingDamage(target, source, finalAmount);
      if (finalAmount != 0.0F) {
         target.m_21231_().m_289194_(source, finalAmount);
         target.m_21153_(target.m_21223_() - finalAmount);
         target.m_7911_(target.m_6103_() - finalAmount);
         target.m_146850_(GameEvent.f_223706_);
      }

   }

   public static float applyBreakSpeed(Player player, float originalSpeed) {
      AttributeInstance attributeInstance = player.m_21051_((Attribute)ModAttributes.MINING_SPEED.get());
      return attributeInstance != null ? (float)((double)originalSpeed * attributeInstance.m_22135_()) : originalSpeed;
   }

   public static float applyFallResistance(LivingEntity entity, float distance) {
      AttributeInstance attributeInstance = entity.m_21051_((Attribute)ModAttributes.FALL_RESISTANCE.get());
      return attributeInstance != null ? distance - (float)attributeInstance.m_22135_() : distance;
   }

   public static double velocityToHeight(double velocity, double gravity, double drag) {
      double yPos;
      for(yPos = (double)0.0F; velocity >= (double)0.0F; velocity = (velocity - gravity) * drag) {
         yPos += velocity;
      }

      return yPos;
   }

   public static double simulateHeightWithVelocityBoost(double estimatedVelocity, double initialVelocity, double gravity, double drag, int maxTicks, int tickDelay) {
      double yPos = (double)0.0F;

      for(int i = 0; i < 10000; ++i) {
         yPos += initialVelocity;
         initialVelocity = (initialVelocity - gravity) * drag;
         if (i >= tickDelay && i < tickDelay + maxTicks) {
            initialVelocity += estimatedVelocity * Math.pow(drag, (double)(i - tickDelay));
         }

         if (initialVelocity < (double)0.0F) {
            break;
         }
      }

      return yPos;
   }

   public static double getVelocityForHeight(double initialVelocity, double gravity, double drag, int maxTicks, int tickDelay, double desiredMove) {
      double lo = (double)-10.0F;
      double hi = (double)10.0F;

      for(int i = 0; i < 50; ++i) {
         double mid = (double)0.5F * (lo + hi);
         double midHeight = simulateHeightWithVelocityBoost(mid, initialVelocity, gravity, drag, maxTicks, tickDelay);
         if (Math.abs(midHeight - desiredMove) < 6.0E-6) {
            return mid;
         }

         if (midHeight < desiredMove) {
            lo = mid;
         } else {
            hi = mid;
         }
      }

      return (double)0.5F * (lo + hi);
   }

   public static void applyJumpForce(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      if (props != null) {
         if (props.isJumping()) {
            AttributeInstance jumpHeight = entity.m_21051_((Attribute)ModAttributes.JUMP_HEIGHT.get());
            if (jumpHeight != null && props.getJumpTicks() >= 1 && props.getJumpTicks() < 10) {
               double gravityValue = entity.m_21133_((Attribute)ForgeMod.ENTITY_GRAVITY.get());
               double jumpPower = (double)((ILivingEntityMixin)entity).invokeGetJumpPower();
               double baseHeight = velocityToHeight(jumpPower, gravityValue, 0.98);
               double jumpHeightValue = jumpHeight.m_22135_() - (double)1.0F - baseHeight;
               if (jumpHeightValue > (double)0.0F) {
                  double adjustedMove = jumpHeightValue + baseHeight;
                  double velocity = getVelocityForHeight(jumpPower, gravityValue, 0.98, 9, 1, adjustedMove);
                  double tickVelocity = velocity * Math.pow(0.98, (double)(props.getJumpTicks() - 1));
                  double yRot = (double)entity.m_146908_() * Math.PI / (double)180.0F;
                  double leapPower = (entity.m_20184_().f_82480_ + tickVelocity) * 0.2;
                  double velocityX = entity.m_20142_() && tickVelocity > (double)0.0F ? -Math.sin(yRot) * leapPower : (double)0.0F;
                  double velocityZ = entity.m_20142_() && tickVelocity > (double)0.0F ? Math.cos(yRot) * leapPower : (double)0.0F;
                  entity.m_5997_(velocityX, tickVelocity, velocityZ);
               }
            }

            props.alterJumpTicks(1);
         } else {
            props.setJumpTicks(10);
         }

         if (entity.m_20096_()) {
            props.setJumpTicks(0);
         }

      }
   }

   public static void applyJumpHeight(LivingEntity entity) {
      AttributeInstance attributeInstance = entity.m_21051_((Attribute)ModAttributes.JUMP_HEIGHT.get());
      if (attributeInstance != null) {
         double value = attributeInstance.m_22135_();
         if (value <= (double)0.0F) {
            AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }

   public static float applyExtraHeal(LivingEntity entity, float healAmount) {
      AttributeInstance attributeInstance = entity.m_21051_((Attribute)ModAttributes.REGEN_RATE.get());
      if (attributeInstance != null) {
         float value = (float)attributeInstance.m_22135_();
         if (value != 1.0F) {
            return healAmount * value;
         }
      }

      return healAmount;
   }
}
