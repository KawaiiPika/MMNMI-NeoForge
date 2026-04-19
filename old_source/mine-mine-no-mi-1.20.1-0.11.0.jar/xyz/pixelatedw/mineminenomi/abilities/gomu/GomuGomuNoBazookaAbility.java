package xyz.pixelatedw.mineminenomi.abilities.gomu;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoGrizzlyMagnumProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoJetBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoLeoBazookaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class GomuGomuNoBazookaAbility extends Ability {
   private static final Component GOMU_GOMU_NO_JET_BAZOOKA_NAME;
   private static final Component GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME;
   private static final Component GOMU_GOMU_NO_LEO_BAZOOKA_NAME;
   private static final ResourceLocation GOMU_GOMU_NO_BAZOOKA_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_JET_BAZOOKA_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_GRIZZLY_MAGNUM_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_LEO_BAZOOKA_ICON;
   private static final int NO_GEAR_COOLDOWN = 200;
   private static final int NO_GEAR_CHARGE_TIME = 40;
   private static final int SECOND_GEAR_COOLDOWN = 140;
   private static final int SECOND_GEAR_CHARGE_TIME = 30;
   private static final int THIRD_GEAR_COOLDOWN = 300;
   private static final int THIRD_GEAR_CHARGE_TIME = 60;
   private static final int FOURTH_GEAR_COOLDOWN = 240;
   private static final int FOURTH_GEAR_CHARGE_TIME = 40;
   private static final AbilityDescriptionLine.IDescriptionLine SECOND_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine THIRD_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine FOURTH_GEAR_NAME_DESC;
   public static final RegistryObject<AbilityCore<GomuGomuNoBazookaAbility>> INSTANCE;
   private final AltModeComponent<GomuHelper.Gears> altModeComponent;
   private final ChargeComponent chargeComponent;
   private final ProjectileComponent projectileComponent;
   private final AnimationComponent animationComponent;
   private float cooldown;
   private int chargeTime;
   private float projectileSpeed;
   private float projectileSpread;

   public GomuGomuNoBazookaAbility(AbilityCore<GomuGomuNoBazookaAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<GomuHelper.Gears>(this, GomuHelper.Gears.class, GomuHelper.Gears.NO_GEAR, true)).addChangeModeEvent(this::altModeChangeEvent);
      this.chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addEndEvent(this::endChargeEvent);
      this.projectileComponent = new ProjectileComponent(this, this::createProjectile);
      this.animationComponent = new AnimationComponent(this);
      this.cooldown = 200.0F;
      this.chargeTime = 40;
      this.projectileSpeed = 2.0F;
      this.projectileSpread = 1.0F;
      this.addComponents(new AbilityComponent[]{this.altModeComponent, this.chargeComponent, this.projectileComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, (float)this.chargeTime);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.GOMU_BAZOOKA);
      if (((GomuHelper.Gears)this.altModeComponent.getCurrentMode()).equals(GomuHelper.Gears.GEAR_4) && entity instanceof Player player) {
         if (player.m_150110_().f_35935_) {
            return;
         }
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), this.chargeTime, 0));
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      NuProjectileEntity projectile1 = this.projectileComponent.getNewProjectile(entity);
      NuProjectileEntity projectile2 = this.projectileComponent.getNewProjectile(entity);
      Vec3 dirVec = Vec3.f_82478_;
      Direction dir = Direction.m_122364_((double)entity.m_146908_());
      dirVec = dirVec.m_82520_((double)Math.abs(dir.m_122436_().m_123341_()), (double)Math.abs(dir.m_122436_().m_123342_()), (double)Math.abs(dir.m_122436_().m_123343_()));
      dirVec = dirVec.m_82542_((double)this.projectileSpread, (double)1.0F, (double)this.projectileSpread);
      projectile1.m_7678_(entity.m_20185_() + dirVec.f_82481_, entity.m_20188_(), entity.m_20189_() + dirVec.f_82479_, entity.m_146908_(), entity.m_146909_());
      projectile2.m_7678_(entity.m_20185_() - dirVec.f_82481_, entity.m_20188_(), entity.m_20189_() - dirVec.f_82479_, entity.m_146908_(), entity.m_146909_());
      this.projectileComponent.shoot(projectile1, entity, entity.m_146909_(), entity.m_146908_(), this.projectileSpeed, 0.0F);
      this.projectileComponent.shoot(projectile2, entity, entity.m_146909_(), entity.m_146908_(), this.projectileSpeed, 0.0F);
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GOMU_SFX.get(), SoundSource.PLAYERS, 2.0F, 0.75F);
      this.cooldownComponent.startCooldown(entity, this.cooldown);
   }

   private boolean altModeChangeEvent(LivingEntity entity, IAbility ability, GomuHelper.Gears mode) {
      switch (mode) {
         case GEAR_2:
            this.setDisplayName(GOMU_GOMU_NO_JET_BAZOOKA_NAME);
            this.cooldown = 140.0F;
            this.chargeTime = 30;
            break;
         case GEAR_3:
            this.setDisplayName(GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME);
            this.cooldown = 300.0F;
            this.chargeTime = 60;
            break;
         case GEAR_4:
            this.setDisplayName(GOMU_GOMU_NO_LEO_BAZOOKA_NAME);
            this.cooldown = 240.0F;
            this.chargeTime = 40;
         case GEAR_5:
            break;
         case NO_GEAR:
         default:
            this.setDisplayIcon(GOMU_GOMU_NO_BAZOOKA_ICON);
            this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
            this.cooldown = 200.0F;
            this.chargeTime = 40;
      }

      return true;
   }

   private NuProjectileEntity createProjectile(LivingEntity entity) {
      NuProjectileEntity projectile = null;
      this.projectileSpeed = 2.0F;
      this.projectileSpread = 1.0F;
      switch ((GomuHelper.Gears)this.altModeComponent.getCurrentMode()) {
         case GEAR_2:
            projectile = new GomuGomuNoJetBazookaProjectile(entity.m_9236_(), entity, this);
            this.projectileSpeed = 3.0F;
            break;
         case GEAR_3:
            projectile = new GomuGomuNoGrizzlyMagnumProjectile(entity.m_9236_(), entity, this);
            this.projectileSpeed = 1.8F;
            this.projectileSpread = 2.5F;
            break;
         case GEAR_4:
            projectile = new GomuGomuNoLeoBazookaProjectile(entity.m_9236_(), entity, this);
            this.projectileSpeed = 3.0F;
            this.projectileSpread = 2.5F;
            break;
         default:
            projectile = new GomuGomuNoBazookaProjectile(entity.m_9236_(), entity, this);
      }

      return projectile;
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

   static {
      GOMU_GOMU_NO_JET_BAZOOKA_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_jet_bazooka", "Gomu Gomu no Jet Bazooka"));
      GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_grizzly_magnum", "Gomu Gomu no Grizzly Magnum"));
      GOMU_GOMU_NO_LEO_BAZOOKA_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_leo_bazooka", "Gomu Gomu no Leo Bazooka"));
      GOMU_GOMU_NO_BAZOOKA_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_bazooka.png");
      GOMU_GOMU_NO_JET_BAZOOKA_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_jet_bazooka.png");
      GOMU_GOMU_NO_GRIZZLY_MAGNUM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_grizzly_magnum.png");
      GOMU_GOMU_NO_LEO_BAZOOKA_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_leo_bazooka.png");
      SECOND_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_JET_BAZOOKA_NAME));
      THIRD_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_GRIZZLY_MAGNUM_NAME));
      FOURTH_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_LEO_BAZOOKA_NAME));
      INSTANCE = ModRegistry.registerAbility("gomu_gomu_no_bazooka", "Gomu Gomu no Bazooka", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Hits the enemy with both hands to launch them away.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GomuGomuNoBazookaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityDescriptionLine.IDescriptionLine.of(Component.m_237113_(name)), AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(40.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, SECOND_GEAR_NAME_DESC, GomuHelper.SECOND_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ChargeComponent.getTooltip(30.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, THIRD_GEAR_NAME_DESC, GomuHelper.THIRD_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ChargeComponent.getTooltip(60.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, FOURTH_GEAR_NAME_DESC, GomuHelper.FOURTH_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(40.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.RUBBER).build("mineminenomi");
      });
   }
}
