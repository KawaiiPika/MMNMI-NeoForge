package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class VenomDemonAbility extends MorphAbility {
   private static final int COOLDOWN = 2000;
   private static final int HOLD_TIME = 1200;
   public static final RegistryObject<AbilityCore<VenomDemonAbility>> INSTANCE = ModRegistry.registerAbility("venom_demon", "Venom Demon", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user coats themselves in layers of strong corrosive venom, becoming a Venom Demon and leaving a highly poisonous trail. Also enhances all Posion abilities.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, VenomDemonAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(2000.0F), ContinuousComponent.getTooltip(), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier ATTACK_MODIFIER;
   private static final AbilityAttributeModifier REACH_MODIFIER;
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED;
   private static final AbilityAttributeModifier STEP_ASSIST;
   private static final AbilityAttributeModifier JUMP_HEIGHT;
   private static final AbilityAttributeModifier KNOCKBACK_RESISTANCE;
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addOnHitEvent(this::hitTriggerEvent).addTryHitEvent(this::tryHitEvent);

   public VenomDemonAbility(AbilityCore<VenomDemonAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.hitTriggerComponent});
      this.statsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ForgeMod.BLOCK_REACH, REACH_MODIFIER);
      this.statsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, ATTACK_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED);
      this.statsComponent.addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION, STEP_ASSIST);
      this.statsComponent.addAttributeModifier(ModAttributes.FALL_RESISTANCE, STEP_ASSIST);
      this.statsComponent.addAttributeModifier(ModAttributes.JUMP_HEIGHT, JUMP_HEIGHT);
      this.statsComponent.addAttributeModifier(Attributes.f_22278_, KNOCKBACK_RESISTANCE);
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(80, this::earlyEndContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   public void startContinuityEvent(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         HydraAbility hydra = (HydraAbility)props.getEquippedAbility((AbilityCore)HydraAbility.INSTANCE.get());
         if (hydra != null) {
            hydra.setVenomMode(entity);
         }

         ChloroBallAbility chloroBall = (ChloroBallAbility)props.getEquippedAbility((AbilityCore)ChloroBallAbility.INSTANCE.get());
         if (chloroBall != null) {
            chloroBall.setVenomMode(entity);
         }

         DokuGumoAbility dokuGumo = (DokuGumoAbility)props.getEquippedAbility((AbilityCore)DokuGumoAbility.INSTANCE.get());
         if (dokuGumo != null) {
            dokuGumo.setVenomMode(entity);
         }

         VenomRoadAbility venomRoad = (VenomRoadAbility)props.getEquippedAbility((AbilityCore)VenomRoadAbility.INSTANCE.get());
         if (venomRoad != null) {
            venomRoad.setVenomMode(entity);
         }

         DokuFuguAbility dokuFugu = (DokuFuguAbility)props.getEquippedAbility((AbilityCore)DokuFuguAbility.INSTANCE.get());
         if (dokuFugu != null) {
            AbilityHelper.emergencyStopAbility(entity, dokuFugu);
         }

      }
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!AbilityHelper.isWeakenedByKairosekiOrWater(entity)) {
            BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

            for(int x = -1; x < 1; ++x) {
               for(int z = -1; z < 1; ++z) {
                  mutpos.m_122169_(entity.m_20185_() + (double)x, entity.m_20186_(), entity.m_20189_() + (double)z);
                  if (!entity.m_9236_().m_8055_(mutpos.m_7495_()).m_60795_()) {
                     NuWorld.setBlockState((Entity)entity, mutpos, ((Block)ModBlocks.DEMON_POISON.get()).m_49966_(), 3, DefaultProtectionRules.AIR_FOLIAGE);
                  }
               }
            }
         }

         if (this.continuousComponent.getContinueTime() % 2.0F == 0.0F) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.VENOM_DEMON.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

      }
   }

   private void earlyEndContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 2000.0F);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         HydraAbility hydra = (HydraAbility)props.getEquippedAbility((AbilityCore)HydraAbility.INSTANCE.get());
         if (hydra != null) {
            hydra.setNormalMode(entity);
         }

         ChloroBallAbility chloroBall = (ChloroBallAbility)props.getEquippedAbility((AbilityCore)ChloroBallAbility.INSTANCE.get());
         if (chloroBall != null) {
            chloroBall.setNormalMode(entity);
         }

         DokuGumoAbility dokuGumo = (DokuGumoAbility)props.getEquippedAbility((AbilityCore)DokuGumoAbility.INSTANCE.get());
         if (dokuGumo != null) {
            dokuGumo.setNormalMode(entity);
         }

         VenomRoadAbility venomRoad = (VenomRoadAbility)props.getEquippedAbility((AbilityCore)VenomRoadAbility.INSTANCE.get());
         if (venomRoad != null) {
            venomRoad.setNormalMode(entity);
         }

      }
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.VENOM_DEMON.get();
   }

   public float getContinuityHoldTime() {
      return 1200.0F;
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return this.continuousComponent.isContinuous() && entity.m_21205_().m_41619_() ? HitTriggerComponent.HitResult.HIT : HitTriggerComponent.HitResult.PASS;
   }

   public boolean hitTriggerEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOKU_POISON.get(), 100, 1));
      return true;
   }

   static {
      ATTACK_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_STRENGTH_UUID, INSTANCE, "Venom Demon Attack Modifier", (double)8.0F, Operation.ADDITION);
      REACH_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_REACH_UUID, INSTANCE, "Venom Demon Reach Modifier", (double)3.0F, Operation.ADDITION);
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Venom Demon Speed Modifier", 0.02, Operation.ADDITION);
      ATTACK_SPEED = new AbilityAttributeModifier(AttributeHelper.MORPH_ATTACK_SPEED_UUID, INSTANCE, "Venom Demon Attack Speed Modifier", 0.15, Operation.ADDITION);
      STEP_ASSIST = new AbilityAttributeModifier(AttributeHelper.MORPH_STEP_HEIGHT_UUID, INSTANCE, "Venom Demon Step assist Modifier", (double)1.5F, Operation.ADDITION);
      JUMP_HEIGHT = new AbilityAttributeModifier(AttributeHelper.MORPH_JUMP_BOOST_UUID, INSTANCE, "Venom Demon Jump Height Modifier", (double)2.0F, Operation.ADDITION);
      KNOCKBACK_RESISTANCE = new AbilityAttributeModifier(AttributeHelper.MORPH_KNOCKBACK_RESISTANCE_UUID, INSTANCE, "Venom Demon Knockback Resistance Modifier", (double)1.0F, Operation.ADDITION);
   }
}
