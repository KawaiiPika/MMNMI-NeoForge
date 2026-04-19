package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GraviPullAbility extends Ability {
   private static final int COOLDOWN = 340;
   private static final int CHARGE_TIME = 60;
   private static final int RANGE = 16;
   public static final RegistryObject<AbilityCore<GraviPullAbility>> INSTANCE = ModRegistry.registerAbility("gravi_pull", "Gravi Pull", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Pulls all enemies in a radius towards the user.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, GraviPullAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(340.0F), ChargeComponent.getTooltip(60.0F), RangeComponent.getTooltip(16.0F, RangeComponent.RangeType.AOE)).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addEndEvent(this::endChargeEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public GraviPullAbility(AbilityCore<GraviPullAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 60.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GRAVI_PULL_1.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GRAVI_PULL_2.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 16.0F)) {
         double offsetX = entity.m_20185_() - target.m_20185_();
         double offsetZ = entity.m_20189_() - target.m_20189_();
         AbilityHelper.setDeltaMovement(target, offsetX / (double)2.0F, (entity.m_20186_() - target.m_20186_()) / (double)4.0F, offsetZ / (double)2.0F);
      }

      this.cooldownComponent.startCooldown(entity, 340.0F);
   }
}
