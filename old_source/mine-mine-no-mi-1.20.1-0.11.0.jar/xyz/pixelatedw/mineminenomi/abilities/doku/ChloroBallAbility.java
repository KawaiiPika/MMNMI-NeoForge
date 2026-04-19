package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ChloroBallAbility extends Ability {
   private static final Component VENOM_NAME;
   private static final ResourceLocation NORMAL_ICON;
   private static final ResourceLocation VENOM_ICON;
   private static final int COOLDOWN = 180;
   public static final RegistryObject<AbilityCore<ChloroBallAbility>> INSTANCE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::endRepeaterEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AltModeComponent<Mode> altModeComponent;

   public ChloroBallAbility(AbilityCore<ChloroBallAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, ChloroBallAbility.Mode.NORMAL, true)).addChangeModeEvent(this::onAltModeChange);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.projectileComponent, this.altModeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      boolean isDemonForm = ((MorphInfo)ModMorphs.VENOM_DEMON.get()).isActive(entity);
      if (isDemonForm) {
         this.repeaterComponent.start(entity, 1, 5);
      } else {
         this.repeaterComponent.start(entity, 1, 5);
      }

   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 2.0F, 1.0F);
   }

   private void endRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 180.0F);
   }

   private ChloroBallProjectile createProjectile(LivingEntity entity) {
      boolean isDemonForm = ((MorphInfo)ModMorphs.VENOM_DEMON.get()).isActive(entity);
      ChloroBallProjectile proj = new ChloroBallProjectile(entity.m_9236_(), entity, this, isDemonForm);
      return proj;
   }

   public void setNormalMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, ChloroBallAbility.Mode.NORMAL);
   }

   public void setVenomMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, ChloroBallAbility.Mode.VENOM);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == ChloroBallAbility.Mode.VENOM) {
         this.setDisplayName(VENOM_NAME);
         this.setDisplayIcon(VENOM_ICON);
      } else if (mode == ChloroBallAbility.Mode.NORMAL) {
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         this.setDisplayIcon(NORMAL_ICON);
      }

      return true;
   }

   static {
      VENOM_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "chloro_ball_venom", "Venom Chloro Ball"));
      NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/chloro_ball.png");
      VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/chloro_ball_venom.png");
      INSTANCE = ModRegistry.registerAbility("chloro_ball", "Chloro Ball", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user spits a bubble made of poison towards the enemy, which also leaves poison on the ground.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ChloroBallAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(180.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.POISON).build("mineminenomi");
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
