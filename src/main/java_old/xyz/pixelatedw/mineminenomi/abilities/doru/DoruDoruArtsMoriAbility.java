package xyz.pixelatedw.mineminenomi.abilities.doru;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.ChampFightProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.DoruDoruArtsMoriProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DoruDoruArtsMoriAbility extends Ability {
   private static final Component CHAMP_FIGHT_NAME = Component.m_237115_(ModRegistry.registerAbilityName("champ_fight", "Champ Fight"));
   private static final ResourceLocation CHAMP_FIGHT_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/champ_fight.png");
   private static final float COOLDOWN = 80.0F;
   public static final RegistryObject<AbilityCore<DoruDoruArtsMoriAbility>> INSTANCE = ModRegistry.registerAbility("doru_doru_arts_mori", "Doru Doru Arts: Mori", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user fires a harpoon made of wax at the opponent.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DoruDoruArtsMoriAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.WAX).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(100, this::repeaterTriggerEvent).addStopEvent(100, this::repeaterStopEvent);
   private final AltModeComponent<Mode> altModeComponent;
   private final ProjectileComponent projectileComponent;
   private final AnimationComponent animationComponent;

   public DoruDoruArtsMoriAbility(AbilityCore<DoruDoruArtsMoriAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, DoruDoruArtsMoriAbility.Mode.MORI)).addChangeModeEvent(100, this::changeModeEvent);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.animationComponent = new AnimationComponent(this);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.repeaterComponent, this.altModeComponent, this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.repeaterComponent.stop(entity);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.altModeComponent.getCurrentMode() == DoruDoruArtsMoriAbility.Mode.CHAMP_FIGHT) {
            this.repeaterComponent.start(entity, 25, 1);
            this.animationComponent.start(entity, ModAnimations.AIM_SNIPER);
         } else if (this.altModeComponent.getCurrentMode() == DoruDoruArtsMoriAbility.Mode.MORI) {
            this.repeaterComponent.start(entity, 1, 0);
            this.animationComponent.start(entity, ModAnimations.AIM_SNIPER, 7);
         }

      }
   }

   private void repeaterTriggerEvent(LivingEntity entity, IAbility ability) {
      if (((Mode)this.altModeComponent.getCurrentMode()).equals(DoruDoruArtsMoriAbility.Mode.CHAMP_FIGHT)) {
         this.projectileComponent.shootWithSpread(entity, 2.0F, 1.0F, 2);
      } else {
         this.projectileComponent.shoot(entity, 3.0F, 0.0F);
      }

   }

   private void repeaterStopEvent(LivingEntity entity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() == DoruDoruArtsMoriAbility.Mode.CHAMP_FIGHT) {
         this.animationComponent.stop(entity);
      }

      this.continuousComponent.stopContinuity(entity);
      this.cooldownComponent.startCooldown(entity, 80.0F);
   }

   private boolean changeModeEvent(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode.equals(DoruDoruArtsMoriAbility.Mode.CHAMP_FIGHT)) {
         this.setDisplayIcon(CHAMP_FIGHT_ICON);
         this.setDisplayName(CHAMP_FIGHT_NAME);
      } else {
         this.setDisplayIcon((AbilityCore)INSTANCE.get());
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
      }

      return true;
   }

   public void changeToChampFight(LivingEntity entity) {
      this.altModeComponent.setMode(entity, DoruDoruArtsMoriAbility.Mode.CHAMP_FIGHT);
   }

   public void changeToDefault(LivingEntity entity) {
      this.altModeComponent.setMode(entity, DoruDoruArtsMoriAbility.Mode.MORI);
   }

   private NuProjectileEntity createProjectile(LivingEntity entity) {
      if (((Mode)this.altModeComponent.getCurrentMode()).equals(DoruDoruArtsMoriAbility.Mode.CHAMP_FIGHT)) {
         ChampFightProjectile proj = new ChampFightProjectile(entity.m_9236_(), entity, this);
         return proj;
      } else {
         DoruDoruArtsMoriProjectile proj = new DoruDoruArtsMoriProjectile(entity.m_9236_(), entity, this);
         return proj;
      }
   }

   public static enum Mode {
      MORI,
      CHAMP_FIGHT;

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{MORI, CHAMP_FIGHT};
      }
   }
}
