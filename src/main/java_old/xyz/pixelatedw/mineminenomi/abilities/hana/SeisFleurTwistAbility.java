package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SeisFleurTwistAbility extends Ability {
   private static final int MIL_DISTANCE = 30;
   private static final float DAMAGE_BONUS = 1.25F;
   private static final Component MIL_NAME = Component.m_237115_(ModRegistry.registerAbilityName("mil_fleur_twist", "Mil Fleur: Twist"));
   private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/seis_fleur_twist.png");
   private static final ResourceLocation MIL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/mil_fleur_twist.png");
   private static final int COOLDOWN = 100;
   private static final int NORMAL_DAMAGE = 6;
   public static final RegistryObject<AbilityCore<SeisFleurTwistAbility>> INSTANCE = ModRegistry.registerAbility("seis_fleur_twist", "Seis Fleur: Twist", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("The six arms sprout from around a target's body and then twists it around.", (Object)null), null};
      Object[] var10006 = new Object[]{MilFleurAbility.INSTANCE, "§a30§r", null};
      float var10009 = Math.abs(-0.25F);
      var10006[2] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While %s is active it will sprout on every enemy in a %s blocks radius and increase its damage by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SeisFleurTwistAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), DealDamageComponent.getTooltip(6.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final AltModeComponent<MilFleurAbility.Mode> altModeComponent;
   private final RangeComponent rangeComponent;
   private final DealDamageComponent dealDamageComponent;

   public SeisFleurTwistAbility(AbilityCore<SeisFleurTwistAbility> ability) {
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
         this.dealDamageComponent.getBonusManager().addBonus(HanaHelper.MIL_DAMAGE_BONUS, "Mil Fleur Damage Bonus", BonusOperation.MUL, 1.25F);

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 30.0F)) {
            this.twist(entity, target);
         }
      } else {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 30.0F, 2.0F);
         if (targets.size() > 0) {
            LivingEntity target = (LivingEntity)targets.get(0);
            if (target != null && target.m_6084_()) {
               this.twist(entity, target);
            }
         }
      }

      HanaHelper.spawnBlossomEffect(entity);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private void twist(LivingEntity entity, @Nullable Entity targetEntity) {
      if (targetEntity != null && targetEntity instanceof LivingEntity target) {
         target.m_146922_(target.m_146908_() + 180.0F);
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 1));
         HanaHandsEntity twist = new HanaHandsEntity(entity.m_9236_(), entity, this);
         twist.setWarmup(5);
         twist.setClutch();
         twist.setTarget(target);
         twist.setDamage(6.0F);
         entity.m_9236_().m_7967_(twist);
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
