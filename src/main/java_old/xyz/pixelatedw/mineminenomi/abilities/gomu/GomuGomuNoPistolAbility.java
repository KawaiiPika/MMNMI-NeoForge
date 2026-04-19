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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoBajrangGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoElephantGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoJetPistolProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoKingKongGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoPistolProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class GomuGomuNoPistolAbility extends Ability {
   private static final Component GOMU_GOMU_NO_JET_PISTOL_NAME;
   private static final Component GOMU_GOMU_NO_ELEPHANT_GUN_NAME;
   private static final Component GOMU_GOMU_NO_KING_KONG_GUN_NAME;
   private static final Component GOMU_GOMU_NO_BAJRANG_GUN_NAME;
   private static final ResourceLocation GOMU_GOMU_NO_PISTOL_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_JET_PISTOL_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_ELEPHANT_GUN_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_KING_KONG_GUN_ICON;
   private static final ResourceLocation GOMU_GOMU_NO_BAJRANG_GUN_ICON;
   private static final int NO_GEAR_COOLDOWN = 30;
   private static final int SECOND_GEAR_COOLDOWN = 20;
   private static final int THIRD_GEAR_COOLDOWN = 120;
   private static final int FOURTH_GEAR_COOLDOWN = 80;
   private static final int FIFTH_GEAR_COOLDOWN = 100;
   private static final AbilityDescriptionLine.IDescriptionLine SECOND_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine THIRD_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine FOURTH_GEAR_NAME_DESC;
   private static final AbilityDescriptionLine.IDescriptionLine FIFTH_GEAR_NAME_DESC;
   public static final RegistryObject<AbilityCore<GomuGomuNoPistolAbility>> INSTANCE;
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final AltModeComponent<GomuHelper.Gears> altModeComponent;
   private final ExplosionComponent explosionComponent;
   private float speed;
   private float cooldown;

   public GomuGomuNoPistolAbility(AbilityCore<GomuGomuNoPistolAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<GomuHelper.Gears>(this, GomuHelper.Gears.class, GomuHelper.Gears.NO_GEAR, true)).addChangeModeEvent(this::altModeChangeEvent);
      this.explosionComponent = new ExplosionComponent(this);
      this.speed = 2.0F;
      this.cooldown = 30.0F;
      this.addComponents(new AbilityComponent[]{this.projectileComponent, this.altModeComponent, this.explosionComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, this.speed, 0.0F);
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GOMU_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.cooldownComponent.startCooldown(entity, this.cooldown);
   }

   private boolean altModeChangeEvent(LivingEntity entity, IAbility ability, GomuHelper.Gears mode) {
      switch (mode) {
         case GEAR_2:
            this.setDisplayName(GOMU_GOMU_NO_JET_PISTOL_NAME);
            this.cooldown = 20.0F;
            break;
         case GEAR_3:
            this.setDisplayName(GOMU_GOMU_NO_ELEPHANT_GUN_NAME);
            this.cooldown = 120.0F;
            break;
         case GEAR_4:
            this.setDisplayIcon(GOMU_GOMU_NO_KING_KONG_GUN_ICON);
            this.setDisplayName(GOMU_GOMU_NO_KING_KONG_GUN_NAME);
            this.cooldown = 80.0F;
            break;
         case GEAR_5:
            this.setDisplayName(GOMU_GOMU_NO_BAJRANG_GUN_NAME);
            this.cooldown = 100.0F;
            break;
         case NO_GEAR:
         default:
            this.setDisplayIcon(GOMU_GOMU_NO_PISTOL_ICON);
            this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
            this.cooldown = 30.0F;
      }

      return true;
   }

   private NuHorizontalLightningEntity createProjectile(LivingEntity entity) {
      NuHorizontalLightningEntity projectile = null;
      this.speed = 2.0F;
      switch ((GomuHelper.Gears)this.altModeComponent.getCurrentMode()) {
         case GEAR_2:
            projectile = new GomuGomuNoJetPistolProjectile(entity.m_9236_(), entity, this);
            this.speed = 2.5F;
            break;
         case GEAR_3:
            projectile = new GomuGomuNoElephantGunProjectile(entity.m_9236_(), entity, this);
            this.speed = 1.8F;
            break;
         case GEAR_4:
            projectile = new GomuGomuNoKingKongGunProjectile(entity.m_9236_(), entity, this);
            this.speed = 1.8F;
            break;
         case GEAR_5:
            projectile = new GomuGomuNoBajrangGunProjectile(entity.m_9236_(), entity, this);
            this.speed = 1.9F;
            break;
         default:
            projectile = new GomuGomuNoPistolProjectile(entity.m_9236_(), entity, this);
      }

      projectile.offsetToHand(entity);
      projectile.setFollowOwner();
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
      GOMU_GOMU_NO_JET_PISTOL_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_jet_pistol", "Gomu Gomu no Jet Pistol"));
      GOMU_GOMU_NO_ELEPHANT_GUN_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_elephant_gun", "Gomu Gomu no Elephant Gun"));
      GOMU_GOMU_NO_KING_KONG_GUN_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_king_kong_gun", "Gomu Gomu no King Kong Gun"));
      GOMU_GOMU_NO_BAJRANG_GUN_NAME = Component.m_237115_(ModRegistry.registerName(ModRegistry.I18nCategory.ABILITY, "gomu_gomu_no_bajrang_gun", "Gomu Gomu no Bajrang Gun"));
      GOMU_GOMU_NO_PISTOL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_pistol.png");
      GOMU_GOMU_NO_JET_PISTOL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_jet_pistol.png");
      GOMU_GOMU_NO_ELEPHANT_GUN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_elephant_gun.png");
      GOMU_GOMU_NO_KING_KONG_GUN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_king_kong_gun.png");
      GOMU_GOMU_NO_BAJRANG_GUN_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/gomu_gomu_no_bajrang_gun.png");
      SECOND_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_JET_PISTOL_NAME));
      THIRD_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_ELEPHANT_GUN_NAME));
      FOURTH_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_KING_KONG_GUN_NAME));
      FIFTH_GEAR_NAME_DESC = AbilityDescriptionLine.IDescriptionLine.of(MentionHelper.mentionText(GOMU_GOMU_NO_BAJRANG_GUN_NAME));
      INSTANCE = ModRegistry.registerAbility("gomu_gomu_no_pistol", "Gomu Gomu no Pistol", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user stretches their arm to punch the opponent.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GomuGomuNoPistolAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityDescriptionLine.IDescriptionLine.of(Component.m_237113_(name)), AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(30.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, SECOND_GEAR_NAME_DESC, GomuHelper.SECOND_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, THIRD_GEAR_NAME_DESC, GomuHelper.THIRD_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, FOURTH_GEAR_NAME_DESC, GomuHelper.FOURTH_GEAR_REQ, AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(80.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.RUBBER).build("mineminenomi");
      });
   }
}
