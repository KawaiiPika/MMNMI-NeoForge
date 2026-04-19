package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
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

public class SeisFleurSlapAbility extends Ability {
   private static final int MIL_DISTANCE = 20;
   private static final float DAMAGE_BONUS = 1.25F;
   private static final Component MIL_NAME = Component.m_237115_(ModRegistry.registerAbilityName("mil_fleur_slap", "Mil Fleur: Slap"));
   private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/seis_fleur_slap.png");
   private static final ResourceLocation MIL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/abilities/mil_fleur_slap.png");
   private static final int COOLDOWN = 100;
   private static final int NORMAL_DAMAGE = 6;
   public static final RegistryObject<AbilityCore<SeisFleurSlapAbility>> INSTANCE = ModRegistry.registerAbility("seis_fleur_slap", "Seis Fleur: Slap", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Slaps the enemy pushing them few blocks back and dealing some damage.", (Object)null), null};
      Object[] var10006 = new Object[]{MilFleurAbility.INSTANCE, "§a20§r", null};
      float var10009 = Math.abs(-0.25F);
      var10006[2] = "§a" + Math.round(var10009 * 100.0F) + "%§r";
      var10002[1] = ImmutablePair.of("While %s is active it will sprout on every enemy in a %s blocks radius and increase its damage by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SeisFleurSlapAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), DealDamageComponent.getTooltip(6.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final AltModeComponent<MilFleurAbility.Mode> altModeComponent;
   private final RangeComponent rangeComponent;
   private final DealDamageComponent dealDamageComponent;

   public SeisFleurSlapAbility(AbilityCore<SeisFleurSlapAbility> ability) {
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

         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 20.0F)) {
            this.slap(entity, target);
         }
      } else {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 20.0F, 2.0F);
         if (targets.size() > 0) {
            LivingEntity target = (LivingEntity)targets.get(0);
            if (target != null && target.m_6084_()) {
               this.slap(entity, target);
            }
         }
      }

      HanaHelper.spawnBlossomEffect(entity);
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }

   private void slap(LivingEntity entity, @Nullable Entity targetEntity) {
      if (targetEntity != null && targetEntity instanceof LivingEntity target) {
         HanaHandsEntity slap = new HanaHandsEntity(entity.m_9236_(), entity, this);
         slap.setWarmup(0);
         slap.setTarget(target);
         slap.setDamage(6.0F);
         slap.setSlap();
         entity.m_9236_().m_7967_(slap);
         Vec3 dirVec = entity.m_20182_().m_82546_(target.m_20182_()).m_82541_();
         AbilityHelper.setDeltaMovement(target, -dirVec.f_82479_ * (double)3.0F, entity.m_20184_().m_7098_() + 0.1, -dirVec.f_82481_ * (double)3.0F);
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
