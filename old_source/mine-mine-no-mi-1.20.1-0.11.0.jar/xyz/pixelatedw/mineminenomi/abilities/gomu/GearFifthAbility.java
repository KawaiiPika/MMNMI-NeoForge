package xyz.pixelatedw.mineminenomi.abilities.gomu;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.MorphAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleDrumsOfLiberationSoundPacket;

public class GearFifthAbility extends MorphAbility {
   public static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 200;
   private static final float MAX_COOLDOWN = 800.0F;
   private static final Color COLOR = WyHelper.hexToRGB("#FFFFFF30");
   public static final RegistryObject<AbilityCore<GearFifthAbility>> INSTANCE = ModRegistry.registerAbility("gear_fifth", "Gear Fifth", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The absolute peak bringing joy and freedom to those around them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GearFifthAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 800.0F), ContinuousComponent.getTooltip(1200.0F), ChangeStatsComponent.getTooltip()).setUnlockCheck(GearFifthAbility::canUnlock).build("mineminenomi");
   });
   private static final AbilityOverlay OVERLAY;
   private static final AbilityAttributeModifier GRAVITY_MODIFIER;
   private static final AbilityAttributeModifier SPEED_MODIFIER;
   private final SkinOverlayComponent skinOverlayComponent;
   private boolean playJumpSound;

   public GearFifthAbility(AbilityCore<? extends GearFifthAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.addComponents(new AbilityComponent[]{this.skinOverlayComponent});
      this.statsComponent.addAttributeModifier((Attribute)ForgeMod.ENTITY_GRAVITY.get(), GRAVITY_MODIFIER);
      this.statsComponent.addAttributeModifier(Attributes.f_22279_, SPEED_MODIFIER);
      this.continuousComponent.addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
      this.addCanUseCheck(GomuHelper.canUseGearCheck((AbilityCore)INSTANCE.get()));
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.showAll(entity);
      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(new SToggleDrumsOfLiberationSoundPacket(entity, true), entity);
      }

      this.playJumpSound = false;
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchFifthGear(entity);
         }

      }
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_20096_() && !this.playJumpSound) {
         this.playJumpSound = true;
      } else if (!entity.m_20096_() && this.playJumpSound) {
         SoundEvent sfx = (SoundEvent)ModSounds.BOUNCE_1.get();
         if (entity.m_217043_().m_188499_()) {
            sfx = (SoundEvent)ModSounds.BOUNCE_2.get();
         }

         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), sfx, SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 2.0F);
         this.playJumpSound = false;
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.skinOverlayComponent.hideAll(entity);
      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(new SToggleDrumsOfLiberationSoundPacket(entity, false), entity);
      }

      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props != null) {
         GomuGomuNoPistolAbility pistol = (GomuGomuNoPistolAbility)props.getEquippedAbility((AbilityCore)GomuGomuNoPistolAbility.INSTANCE.get());
         if (pistol != null) {
            pistol.switchNoGear(entity);
         }

         float cooldown = Math.max(200.0F, this.continuousComponent.getContinueTime());
         this.cooldownComponent.startCooldown(entity, cooldown);
      }
   }

   public float getContinuityHoldTime() {
      return 1200.0F;
   }

   public MorphInfo getTransformation() {
      return (MorphInfo)ModMorphs.GEAR_FIFTH.get();
   }

   private static boolean canUnlock(LivingEntity user) {
      return (Boolean)DevilFruitCapability.get(user).map((props) -> props.hasAwakenedFruit()).orElse(false);
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setColor(COLOR).setRenderType(AbilityOverlay.RenderType.ENERGY).build();
      GRAVITY_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_GRAVITY_UUID, INSTANCE, "Nika Jump Modifier", (double)-0.04F, Operation.ADDITION);
      SPEED_MODIFIER = new AbilityAttributeModifier(AttributeHelper.MORPH_MOVEMENT_SPEED_UUID, INSTANCE, "Nika Speed Modifier", (double)1.15F, Operation.MULTIPLY_BASE);
   }
}
