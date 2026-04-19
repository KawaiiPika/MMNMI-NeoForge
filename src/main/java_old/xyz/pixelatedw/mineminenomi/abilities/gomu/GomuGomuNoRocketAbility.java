package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoRocketProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class GomuGomuNoRocketAbility extends Ability {
   private static final Component GOMU_GOMU_NO_DAWN_ROCKET_NAME;
   private static final ResourceLocation GOMU_GOMU_NO_ROCKET_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_DAWN_ROCKET_ICON;
   private static final int COOLDOWN = 60;
   public static final RegistryObject<AbilityCore<GomuGomuNoRocketAbility>> INSTANCE;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final SwingTriggerComponent swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(this::triggerSwingEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AltModeComponent<GomuHelper.Gears> altModeComponent;
   private int airTime;

   public GomuGomuNoRocketAbility(AbilityCore<GomuGomuNoRocketAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<GomuHelper.Gears>(this, GomuHelper.Gears.class, GomuHelper.Gears.NO_GEAR, true)).addChangeModeEvent(this::altModeChangeEvent);
      this.airTime = 0;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.swingTriggerComponent, this.projectileComponent, this.altModeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.airTime = 0;
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_20096_() && this.airTime < 10) {
         AbilityHelper.slowEntityFall(entity, 10);
         ++this.airTime;
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 60.0F);
   }

   private void triggerSwingEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         float speed = ((GomuHelper.Gears)this.altModeComponent.getCurrentMode()).equals(GomuHelper.Gears.GEAR_2) ? 4.0F : 3.125F;
         this.projectileComponent.shoot(entity, speed, 0.0F);
         entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GOMU_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
         entity.m_21011_(InteractionHand.MAIN_HAND, true);
         this.continuousComponent.stopContinuity(entity);
      }
   }

   private boolean altModeChangeEvent(LivingEntity entity, IAbility ability, GomuHelper.Gears mode) {
      switch (mode) {
         case GEAR_5:
         case GEAR_4:
         case GEAR_3:
         case GEAR_2:
         case NO_GEAR:
         default:
            this.setDisplayIcon(GOMU_GOMU_NO_ROCKET_ICON);
            this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
            return true;
      }
   }

   private NuProjectileEntity createProjectile(LivingEntity entity) {
      NuProjectileEntity projectile = new GomuGomuNoRocketProjectile(entity.m_9236_(), entity, this);
      return projectile;
   }

   public void switchNoGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.NO_GEAR);
   }

   public void switchFifthGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.GEAR_5);
   }

   static {
      GOMU_GOMU_NO_DAWN_ROCKET_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_dawn_rocket", "Gomu Gomu no Dawn Rocket"));
      GOMU_GOMU_NO_ROCKET_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_rocket.png");
      GOMU_GOMU_NO_DAWN_ROCKET_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_dawn_rocket.png");
      INSTANCE = ModRegistry.registerAbility("gomu_gomu_no_rocket", "Gomu Gomu no Rocket", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Stretches towards a block, then launches the user on an arch depending on where they fist landed.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GomuGomuNoRocketAbility::new)).addDescriptionLine(desc).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.RUBBER).build("mineminenomi");
      });
   }
}
