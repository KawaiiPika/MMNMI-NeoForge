package xyz.pixelatedw.mineminenomi.abilities.kobu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShoureiAbility extends Ability {
   private static final TargetPredicate TARGETS_CHECK = (new TargetPredicate()).testFriendlyFaction();
   private static final int COOLDOWN = 1200;
   private static final int RANGE = 20;
   public static final RegistryObject<AbilityCore<ShoureiAbility>> INSTANCE = ModRegistry.registerAbility("shourei", "Shourei", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Increases other people's fighting spirit and physical strength by simply encouraging them with words", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, ShoureiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(1200.0F), RangeComponent.getTooltip(20.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public ShoureiAbility(AbilityCore<ShoureiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 20.0F, TARGETS_CHECK)) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 600, 1, true, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 600, 1, true, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 600, 1, true, false, true));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 600, 0, true, false, true));
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SHOUREI.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      }

      super.cooldownComponent.startCooldown(entity, 1200.0F);
   }
}
