package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.RepeaterAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoElephantGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoJetPistolProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoKingKongGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoPistolProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class GomuGomuNoGatlingAbility extends RepeaterAbility {
   private static final Component GOMU_GOMU_NO_JET_GATLING_NAME;
   private static final Component GOMU_GOMU_NO_ELEPHANT_GATLING_NAME;
   private static final Component GOMU_GOMU_NO_KONG_GATLING_NAME;
   private static final ResourceLocation GOMU_GOMU_NO_GATLING_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_JET_GATLING_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_ELEPHANT_GATLING_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_KONG_GATLING_ICON;
   private static final int NO_GEAR_COOLDOWN = 140;
   private static final int NO_GEAR_TRIGGERS = 20;
   private static final int NO_GEAR_INTERVAL = 3;
   private static final int SECOND_GEAR_COOLDOWN = 100;
   private static final int SECOND_GEAR_TRIGGERS = 35;
   private static final int SECOND_GEAR_INTERVAL = 2;
   private static final int THIRD_GEAR_COOLDOWN = 250;
   private static final int THIRD_GEAR_TRIGGERS = 10;
   private static final int THIRD_GEAR_INTERVAL = 5;
   private static final int FOURTH_GEAR_COOLDOWN = 200;
   private static final int FOURTH_GEAR_TRIGGERS = 8;
   private static final int FOURTH_GEAR_INTERVAL = 5;
   private static final AbilityDescriptionLine.IDescriptionLine SECOND_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine THIRD_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine FOURTH_GEAR_NAME_DESC;
   public static final RegistryObject<AbilityCore<GomuGomuNoGatlingAbility>> INSTANCE;
   private final AltModeComponent<GomuHelper.Gears> altModeComponent;
   private final AnimationComponent animationComponent;
   private final ExplosionComponent explosionComponent;
   private float projectileSpeed;
   private int projectileSpread;
   private float cooldown;
   private int triggers;
   private int interval;

   public GomuGomuNoGatlingAbility(AbilityCore<GomuGomuNoGatlingAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<GomuHelper.Gears>(this, GomuHelper.Gears.class, GomuHelper.Gears.NO_GEAR, true)).addChangeModeEvent(this::altModeChangeEvent);
      this.animationComponent = new AnimationComponent(this);
      this.explosionComponent = new ExplosionComponent(this);
      this.projectileSpeed = 3.0F;
      this.projectileSpread = 2;
      this.cooldown = 140.0F;
      this.triggers = 20;
      this.interval = 3;
      this.addComponents(new AbilityComponent[]{this.altModeComponent, this.animationComponent, this.explosionComponent});
      this.setCustomShootLogic((living) -> {
         for(int i = 0; i < 5; ++i) {
            this.projectileComponent.shootWithSpread(living, this.projectileSpeed, 3.0F, this.projectileSpread);
         }

      });
      this.repeaterComponent.addTriggerEvent(100, this::triggerRepeaterEvent);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.PUNCH_RUSH);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (((GomuHelper.Gears)this.altModeComponent.getCurrentMode()).equals(GomuHelper.Gears.GEAR_4) && entity instanceof Player player) {
         if (player.m_150110_().f_35935_) {
            return;
         }
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1, false, false));
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GOMU_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.6F + this.random.nextFloat() / 2.0F);
   }

   private boolean altModeChangeEvent(LivingEntity entity, IAbility ability, GomuHelper.Gears mode) {
      switch (mode) {
         case GEAR_2:
            this.setDisplayName(GOMU_GOMU_NO_JET_GATLING_NAME);
            this.cooldown = 100.0F;
            this.triggers = 35;
            this.interval = 2;
            break;
         case GEAR_3:
            this.setDisplayName(GOMU_GOMU_NO_ELEPHANT_GATLING_NAME);
            this.cooldown = 250.0F;
            this.triggers = 10;
            this.interval = 5;
            break;
         case GEAR_4:
            this.setDisplayName(GOMU_GOMU_NO_KONG_GATLING_NAME);
            this.cooldown = 200.0F;
            this.triggers = 8;
            this.interval = 5;
         case GEAR_5:
            break;
         case NO_GEAR:
         default:
            this.setDisplayIcon(GOMU_GOMU_NO_GATLING_ICON);
            this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
            this.cooldown = 140.0F;
            this.triggers = 20;
            this.interval = 3;
      }

      return true;
   }

   public void switchNoGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.NO_GEAR);
   }

   public void switchSecondGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.GEAR_2);
   }

   public void switchThirdGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.GEAR_3);
   }

   public void switchFourthGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.GEAR_4);
   }

   public void switchFifthGear(LivingEntity entity) {
      this.altModeComponent.setMode(entity, GomuHelper.Gears.GEAR_5);
   }

   public int getMaxTriggers() {
      return this.triggers;
   }

   public int getTriggerInterval() {
      return this.interval;
   }

   public float getRepeaterCooldown() {
      return this.cooldown;
   }

   public NuProjectileEntity getProjectileFactory(LivingEntity entity) {
      NuProjectileEntity projectile = null;
      this.projectileSpeed = 3.0F;
      float projDmageReduction = 0.8F;
      this.projectileSpread = 2;
      switch ((GomuHelper.Gears)this.altModeComponent.getCurrentMode()) {
         case GEAR_2:
            projectile = new GomuGomuNoJetPistolProjectile(entity.m_9236_(), entity, this);
            this.projectileSpeed = 3.6F;
            break;
         case GEAR_3:
            projectile = new GomuGomuNoElephantGunProjectile(entity.m_9236_(), entity, this);
            projectile.setEntityCollisionSize((double)2.5F);
            this.projectileSpeed = 2.4F;
            this.projectileSpread = 9;
            projDmageReduction = 0.6F;
            break;
         case GEAR_4:
            projectile = new GomuGomuNoKingKongGunProjectile(entity.m_9236_(), entity, this);
            projectile.setEntityCollisionSize((double)2.5F);
            this.projectileSpeed = 2.2F;
            this.projectileSpread = 6;
            projDmageReduction = 0.6F;
            break;
         default:
            projectile = new GomuGomuNoPistolProjectile(entity.m_9236_(), entity, this);
      }

      projectile.setDamage(projectile.getDamage() * (1.0F - projDmageReduction));
      return projectile;
   }

   static {
      GOMU_GOMU_NO_JET_GATLING_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_jet_gatling", "Gomu Gomu no Jet Gatling"));
      GOMU_GOMU_NO_ELEPHANT_GATLING_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_elephant_gatling", "Gomu Gomu no Elephant Gatling"));
      GOMU_GOMU_NO_KONG_GATLING_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_kong_gatling", "Gomu Gomu no Kong Gatling"));
      GOMU_GOMU_NO_GATLING_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_gatling.png");
      GOMU_GOMU_NO_JET_GATLING_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_jet_gatling.png");
      GOMU_GOMU_NO_ELEPHANT_GATLING_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_elephant_gatling.png");
      GOMU_GOMU_NO_KONG_GATLING_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_kong_gatling.png");
      SECOND_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_JET_GATLING_NAME));
      THIRD_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_ELEPHANT_GATLING_NAME));
      FOURTH_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_KONG_GATLING_NAME));
      INSTANCE = ModRegistry.registerAbility("gomu_gomu_no_gatling", "Gomu Gomu no Gatling", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Rapidly punches enemies in front of the user.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GomuGomuNoGatlingAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityDescriptionLine.IDescriptionLine.of(Component.m_237113_(name)), AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, SECOND_GEAR_NAME_DESC, GomuHelper.SECOND_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, THIRD_GEAR_NAME_DESC, GomuHelper.THIRD_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(250.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, FOURTH_GEAR_NAME_DESC, GomuHelper.FOURTH_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.RUBBER).build("mineminenomi");
      });
   }
}
