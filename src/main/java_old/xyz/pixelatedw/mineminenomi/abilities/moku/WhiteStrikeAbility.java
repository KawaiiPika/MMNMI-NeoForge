package xyz.pixelatedw.mineminenomi.abilities.moku;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WhiteStrikeAbility extends Ability {
   private static final int HOLD_TIME = 40;
   private static final int COOLDOWN = 400;
   private static final float RANGE = 10.0F;
   public static final RegistryObject<AbilityCore<WhiteStrikeAbility>> INSTANCE = ModRegistry.registerAbility("white_strike", "White Strike", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Surrounds the nearby area with smoke, slowing down nearby entities", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, WhiteStrikeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ContinuousComponent.getTooltip(40.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public WhiteStrikeAbility(AbilityCore<WhiteStrikeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.rangeComponent, this.animationComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 40.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() % 5.0F == 0.0F) {
         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 10.0F)) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SMOKE.get(), 800, 0, false, false));
         }

         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.WHITE_STRIKE.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 400.0F);
   }
}
