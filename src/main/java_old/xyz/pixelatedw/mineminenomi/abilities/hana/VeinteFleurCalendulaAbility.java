package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class VeinteFleurCalendulaAbility extends GuardAbility {
   private static final int HOLD_TIME = 200;
   private static final int MIN_COOLDOWN = 60;
   private static final int MAX_COOLDOWN = 200;
   private static final GuardAbility.GuardValue GUARD;
   public static final RegistryObject<AbilityCore<VeinteFleurCalendulaAbility>> INSTANCE;
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final MorphComponent morphComponent = new MorphComponent(this);

   public VeinteFleurCalendulaAbility(AbilityCore<VeinteFleurCalendulaAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.morphComponent});
      this.continuousComponent.addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.HANA_CALENDULA.get());
      HanaHelper.spawnBlossomEffect(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.morphComponent.stopMorph(entity);
      float cooldown = Math.max(60.0F, this.continuousComponent.getContinueTime());
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public float getHoldTime() {
      return 200.0F;
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD;
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
   }

   static {
      GUARD = GuardAbility.GuardValue.percentage(0.1F, GuardAbility.GuardBreakKind.PER_HIT, 50.0F);
      INSTANCE = ModRegistry.registerAbility("veinte_fleur_calendula", "Veinte Fleur: Calendula", (id, name) -> {
         Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Using newly sprouted arms in the form of a shield the user can partially block attacks.", (Object)null));
         return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, VeinteFleurCalendulaAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(60.0F, 200.0F), ContinuousComponent.getTooltip(200.0F)).build("mineminenomi");
      });
   }
}
