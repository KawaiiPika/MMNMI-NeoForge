package xyz.pixelatedw.mineminenomi.abilities.supa;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SparClawAbility extends PunchAbility {
   private static final float HOLD_TIME = 1000.0F;
   private static final float MIN_COOLDOWN = 20.0F;
   private static final float MAX_COOLDOWN = 1020.0F;
   public static final RegistryObject<AbilityCore<SparClawAbility>> INSTANCE = ModRegistry.registerAbility("spar_claw", "Spar Claw", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turns the undersides of the user's fingers into blades to slash opponents with", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SparClawAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 1020.0F), ContinuousComponent.getTooltip(1000.0F), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final MorphComponent morphComponent = new MorphComponent(this);

   public SparClawAbility(AbilityCore<SparClawAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.morphComponent});
      this.continuousComponent.addStartEvent(100, this::continuousStartEvent);
      this.continuousComponent.addEndEvent(100, this::continuousStopEvent);
   }

   private void continuousStartEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.SPAR_CLAW.get());
   }

   private void continuousStopEvent(LivingEntity entity, IAbility ability) {
      this.morphComponent.stopMorph(entity);
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      return true;
   }

   public int getUseLimit() {
      return 0;
   }

   public boolean isParallel() {
      return true;
   }

   public float getPunchHoldTime() {
      return 1000.0F;
   }

   public float getPunchDamage() {
      return 15.0F;
   }

   public float getPunchCooldown() {
      return 20.0F + this.continuousComponent.getContinueTime();
   }
}
