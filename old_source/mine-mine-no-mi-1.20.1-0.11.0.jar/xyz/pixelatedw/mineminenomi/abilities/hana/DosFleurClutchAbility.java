package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DosFleurClutchAbility extends Ability {
   private static final int MIL_DISTANCE = 30;
   private static final float DAMAGE_BONUS = 1.5F;
   private static final Component MIL_NAME = Component.m_237115_(ModRegistry.registerAbilityName("mil_fleur_clutch", "Mil Fleur: Clutch"));
   private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/dos_fleur_clutch.png");
   private static final ResourceLocation MIL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/mil_fleur_clutch.png");
   private static final int COOLDOWN = 120;
   private static final int NORMAL_DAMAGE = 10;
   public static final RegistryObject<AbilityCore<DosFleurClutchAbility>> INSTANCE = ModRegistry.registerAbility("dos_fleur_clutch", "Dos Fleur: Clutch", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Sprouts two hands to cover the opponent, and then bends them backward with bone-breaking results.", (Object)null), null};
      Object[] var10006 = new Object[]{MilFleurAbility.INSTANCE, "§a30§r", null};
      float var10009 = Math.abs(-0.5F);
      var10006[2] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While %s is active it will sprout on every enemy in a %s blocks radius and increase its damage by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DosFleurClutchAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(120.0F), DealDamageComponent.getTooltip(10.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final AltModeComponent<MilFleurAbility.Mode> altModeComponent;
   private final RangeComponent rangeComponent;
   private final DealDamageComponent dealDamageComponent;

   public DosFleurClutchAbility(AbilityCore<DosFleurClutchAbility> ability) {
      super(ability);
      this.altModeComponent = (new AltModeComponent<MilFleurAbility.Mode>(this, MilFleurAbility.Mode.class, MilFleurAbility.Mode.NORMAL, true)).addChangeModeEvent(this::onAltModeChange);
      this.rangeComponent = new RangeComponent(this);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.altModeComponent, this.rangeComponent, this.dealDamageComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS, 7);
      boolean hasMilFleur = (Boolean)AbilityCapability.get(entity).map((props) -> (MilFleurAbility)props.getEquippedAbility((AbilityCore)MilFleurAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      this.dealDamageComponent.getBonusManager().removeBonus(HanaHelper.MIL_DAMAGE_BONUS);
      if (hasMilFleur) {
         this.dealDamageComponent.getBonusManager().addBonus(HanaHelper.MIL_DAMAGE_BONUS, "Mil Fleur Damage Bonus", BonusOperation.MUL, 1.5F);

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 30.0F)) {
            this.clutch(entity, target);
         }
      } else {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 30.0F, 2.0F);
         if (targets.size() > 0) {
            LivingEntity target = (LivingEntity)targets.get(0);
            if (target != null && target.m_6084_()) {
               this.clutch(entity, target);
            }
         }
      }

      HanaHelper.spawnBlossomEffect(entity);
      this.cooldownComponent.startCooldown(entity, 120.0F);
   }

   private void clutch(LivingEntity entity, @Nullable Entity targetEntity) {
      if (targetEntity != null && targetEntity instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 20, 1));
         HanaHandsEntity clutch = new HanaHandsEntity(entity.m_9236_(), entity, this);
         clutch.setClutch();
         clutch.setWarmup(5);
         clutch.setTarget(target);
         clutch.setDamage(10.0F);
         entity.m_9236_().m_7967_(clutch);
         HanaHelper.spawnBlossomEffect(target);
      }

   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, MilFleurAbility.Mode mode) {
      if (mode == MilFleurAbility.Mode.NORMAL) {
         this.setDisplayName(((AbilityCore)INSTANCE.get()).getLocalizedName());
         this.setDisplayIcon(NORMAL_ICON);
      } else if (mode == MilFleurAbility.Mode.MIL_FLEUR) {
         this.setDisplayName(MIL_NAME);
         this.setDisplayIcon(MIL_ICON);
      }

      return true;
   }

   public void switchNormalMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, MilFleurAbility.Mode.NORMAL);
   }

   public void switchMilMode(LivingEntity entity) {
      this.altModeComponent.setMode(entity, MilFleurAbility.Mode.MIL_FLEUR);
   }
}
