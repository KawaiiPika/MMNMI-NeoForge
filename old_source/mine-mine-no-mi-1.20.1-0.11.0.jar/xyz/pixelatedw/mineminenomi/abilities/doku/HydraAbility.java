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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.HydraProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class HydraAbility extends Ability {
   private static final Component VENOM_NAME;
   private static final ResourceLocation NORMAL_ICON;
   private static final ResourceLocation VENOM_ICON;
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<HydraAbility>> INSTANCE;
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AltModeComponent<Mode> altModeComponent;

   public HydraAbility(AbilityCore<HydraAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, HydraAbility.Mode.NORMAL, true)).addChangeModeEvent(this::onAltModeChange);
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.altModeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      boolean isDemonForm = ((MorphInfo)ModMorphs.VENOM_DEMON.get()).isActive(entity);
      this.projectileComponent.shoot(entity, isDemonForm ? 4.0F : 2.0F, 0.0F);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private HydraProjectile createProjectile(LivingEntity entity) {
      boolean isDemonForm = ((MorphInfo)ModMorphs.VENOM_DEMON.get()).isActive(entity);
      HydraProjectile proj = new HydraProjectile(entity.m_9236_(), entity, this, isDemonForm);
      return proj;
   }

   public void setNormalMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, HydraAbility.Mode.NORMAL);
   }

   public void setVenomMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, HydraAbility.Mode.VENOM);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      if (mode == HydraAbility.Mode.VENOM) {
         this.setDisplayName(VENOM_NAME);
         this.setDisplayIcon(VENOM_ICON);
      } else if (mode == HydraAbility.Mode.NORMAL) {
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         this.setDisplayIcon(NORMAL_ICON);
      }

      return true;
   }

   static {
      VENOM_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "hydra_venom", "Venom Hydra"));
      NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/hydra.png");
      VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/hydra_venom.png");
      INSTANCE = ModRegistry.registerAbility("hydra", "Hydra", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Launches a dragon made out of liquid poison at the opponent", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HydraAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.POISON).build("mineminenomi");
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
