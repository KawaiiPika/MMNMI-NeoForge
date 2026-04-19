package xyz.pixelatedw.mineminenomi.abilities.doku;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DokuGumoAbility extends Ability {
   private static final UUID VENOM_RANGE_BONUS_UUID = UUID.fromString("77d2625c-c112-4ebf-9f1e-01311c66968b");
   private static final Component VENOM_NAME;
   private static final ResourceLocation NORMAL_ICON;
   private static final ResourceLocation VENOM_ICON;
   private static final int COOLDOWN = 600;
   private static final int HOLD_TIME = 400;
   private static final int RANGE = 10;
   public static final RegistryObject<AbilityCore<DokuGumoAbility>> INSTANCE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AltModeComponent<Mode> altModeComponent;

   public DokuGumoAbility(AbilityCore<DokuGumoAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, DokuGumoAbility.Mode.NORMAL, true)).addChangeModeEvent(this::onAltModeChange);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.altModeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 400.0F);
   }

   private void duringContinuityEvent(LivingEntity player, IAbility ability) {
      int power = 0;
      int duration = 100;
      boolean hasVenomDemon = false;
      int poisonIntensity = 0;
      if (((MorphInfo)ModMorphs.VENOM_DEMON.get()).isActive(player)) {
         hasVenomDemon = true;
         power += 2;
         duration *= 2;
         this.rangeComponent.getBonusManager().addBonus(VENOM_RANGE_BONUS_UUID, "Venom Range Bonus", BonusOperation.MUL, 2.0F);
         poisonIntensity = 1;
      }

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(player, 10.0F)) {
         if (!target.m_21023_(MobEffects.f_19610_)) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, duration, power));
         }

         if (!target.m_21023_((MobEffect)ModEffects.DOKU_POISON.get())) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOKU_POISON.get(), duration, poisonIntensity));
         }

         if (!target.m_21023_(MobEffects.f_19613_)) {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, duration, power));
         }
      }

      if (this.continuousComponent.getContinueTime() % 2.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DOKU_GUMO.get(), player, player.m_20185_(), player.m_20186_(), player.m_20189_(), hasVenomDemon ? DokuHelper.VENOM_DETAILS : null);
      }

   }

   private void endContinuityEvent(LivingEntity player, IAbility ability) {
      this.cooldownComponent.startCooldown(player, 600.0F);
   }

   public void setNormalMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, DokuGumoAbility.Mode.NORMAL);
   }

   public void setVenomMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, DokuGumoAbility.Mode.VENOM);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == DokuGumoAbility.Mode.VENOM) {
         this.setDisplayName(VENOM_NAME);
         this.setDisplayIcon(VENOM_ICON);
      } else if (mode == DokuGumoAbility.Mode.NORMAL) {
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         this.setDisplayIcon(NORMAL_ICON);
      }

      return true;
   }

   static {
      VENOM_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "doku_gumo_venom", "Venom Doku Gumo"));
      NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/doku_gumo.png");
      VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/doku_gumo_venom.png");
      INSTANCE = ModRegistry.registerAbility("doku_gumo", "Doku Gumo", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a dense cloud of poisonous smoke, which moves along with the user and poisons and blinds everyone inside.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DokuGumoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F), ContinuousComponent.getTooltip(400.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).setSourceElement(SourceElement.POISON).build("mineminenomi");
      });
   }

   private static enum Mode {
      NORMAL,
      VENOM;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{NORMAL, VENOM};
      }
   }
}
