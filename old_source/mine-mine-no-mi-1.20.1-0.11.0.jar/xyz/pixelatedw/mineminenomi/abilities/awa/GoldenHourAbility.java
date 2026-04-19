package xyz.pixelatedw.mineminenomi.abilities.awa;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GoldenHourAbility extends Ability {
   private static final float HOLD_TIME = 200.0F;
   private static final float MIN_COOLDOWN = 200.0F;
   private static final float MAX_COOLDOWN = 1200.0F;
   public static final float RANGE = 10.0F;
   public static final RegistryObject<AbilityCore<GoldenHourAbility>> INSTANCE = ModRegistry.registerAbility("golden_hour", "Golden Hour", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Spreads bubbles on enemies around, leaving them weakened and immobile", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GoldenHourAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 1200.0F), ContinuousComponent.getTooltip(200.0F), RangeComponent.getTooltip(10.0F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public GoldenHourAbility(AbilityCore<GoldenHourAbility> core) {
      super(core);
      super.addComponents(this.continuousComponent, this.rangeComponent);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 200.0F);
   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 10.0F);
      targets.removeIf(Entity::m_20069_);

      for(LivingEntity target : targets) {
         if (!target.m_21023_((MobEffect)ModEffects.WASHED.get())) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.WASHED.get(), 100, 1));
         }

         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GOLDEN_HOUR.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      super.cooldownComponent.startCooldown(entity, 200.0F + this.continuousComponent.getContinueTime() * 5.0F);
   }
}
