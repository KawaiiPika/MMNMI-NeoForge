package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.awt.Color;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GearSecondAbility extends Ability {
   private static final int HOLD_TIME = 1000;
   private static final int MIN_COOLDOWN = 20;
   private static final float MAX_COOLDOWN = 666.6667F;
   public static final RegistryObject<AbilityCore<GearSecondAbility>> INSTANCE = ModRegistry.registerAbility("gear_second", "Gear Second", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("By speding up their blood flow, the user gains strength, speed and mobility.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GearSecondAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 666.6667F), ContinuousComponent.getTooltip(1000.0F), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private static final AbilityAttributeModifier JUMP_HEIGHT;
   private static final AbilityAttributeModifier STRENGTH_MODIFIER;
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private static final AbilityAttributeModifier STEP_HEIGHT;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private final SkinOverlayComponent skinOverlayComponent;
   private boolean prevSprintValue;

   public GearSecondAbility(AbilityCore<GearSecondAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.prevSprintValue = false;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.changeStatsComponent, this.skinOverlayComponent});
      this.changeStatsComponent.addAttributeModifier((Attribute)ModAttributes.JUMP_HEIGHT.get(), JUMP_HEIGHT);
      this.changeStatsComponent.addAttributeModifier((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get(), STEP_HEIGHT);
      this.changeStatsComponent.addAttributeModifier(ForgeMod.ENTITY_REACH, ATTACK_SPEED_MODIFIER);
      this.changeStatsComponent.addAttributeModifier(ModAttributes.PUNCH_DAMAGE, STRENGTH_MODIFIER);
      this.addCanUseCheck(GomuHelper.canUseGearCheck((AbilityCore)INSTANCE.get()));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1000.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.applyModifiers(entity);
      this.skinOverlayComponent.showAll(entity);
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchSecondGear(entity);
         }

         GomuGomuNoGatlingAbility gatling = (GomuGomuNoGatlingAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoGatlingAbility.INSTANCE.get());
         if (gatling != null) {
            gatling.switchSecondGear(entity);
         }

         GomuGomuNoBazookaAbility bazooka = (GomuGomuNoBazookaAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoBazookaAbility.INSTANCE.get());
         if (bazooka != null) {
            bazooka.switchSecondGear(entity);
         }

         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GEAR_SECOND_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         this.prevSprintValue = entity.m_20142_();
      }
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() % 10.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GEAR_SECOND.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      if (!AbilityUseConditions.canUseMomentumAbilities(entity).isFail()) {
         if (entity.m_20142_()) {
            if (!this.prevSprintValue) {
               entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.TELEPORT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            }

            float maxSpeed = 1.75F;
            Vec3 vec = entity.m_20154_();
            if (entity.m_20096_()) {
               double var10001 = vec.f_82479_ * (double)maxSpeed;
               double var10003 = vec.f_82481_ * (double)maxSpeed;
               AbilityHelper.setDeltaMovement(entity, var10001, entity.m_20184_().f_82480_, var10003);
            } else {
               double var5 = vec.f_82479_ * (double)maxSpeed * (double)0.5F;
               double var6 = vec.f_82481_ * (double)maxSpeed;
               AbilityHelper.setDeltaMovement(entity, var5, entity.m_20184_().f_82480_, var6 * (double)0.5F);
            }

            this.prevSprintValue = entity.m_20142_();
         } else {
            this.prevSprintValue = false;
         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.changeStatsComponent.removeModifiers(entity);
      this.skinOverlayComponent.hideAll(entity);
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchNoGear(entity);
         }

         GomuGomuNoGatlingAbility gatling = (GomuGomuNoGatlingAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoGatlingAbility.INSTANCE.get());
         if (gatling != null) {
            gatling.switchNoGear(entity);
         }

         GomuGomuNoBazookaAbility bazooka = (GomuGomuNoBazookaAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoBazookaAbility.INSTANCE.get());
         if (bazooka != null) {
            bazooka.switchNoGear(entity);
         }

         float cooldown = Math.max(20.0F, this.continuousComponent.getContinueTime() / 1.5F);
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setOverlayPart(AbilityOverlay.OverlayPart.BODY).setColor(new Color(232, 54, 54, 74)).build();
      JUMP_HEIGHT = new AbilityAttributeModifier(UUID.fromString("a44a9644-369a-4e18-88d9-323727d3d85b"), INSTANCE, "Gear Second Jump Modifier", (double)5.0F, Operation.ADDITION);
      STRENGTH_MODIFIER = new AbilityAttributeModifier(UUID.fromString("a2337b58-7e6d-4361-a8ca-943feee4f906"), INSTANCE, "Gear Second Attack Damage Modifier", (double)4.0F, Operation.ADDITION);
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("c495cf01-f3ff-4933-9805-5bb1ed9d27b0"), INSTANCE, "Gear Second Attack Speed Modifier", (double)4.0F, Operation.ADDITION);
      STEP_HEIGHT = new AbilityAttributeModifier(UUID.fromString("eab680cd-a6dc-438a-99d8-46f9eb53a950"), INSTANCE, "Gear Second Step Height Modifier", (double)1.0F, Operation.ADDITION);
   }
}
